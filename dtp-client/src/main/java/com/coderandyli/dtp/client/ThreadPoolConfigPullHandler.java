package com.coderandyli.dtp.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.coderandyli.dtp.client.properties.ThreadPoolConfigProperties;
import com.coderandyli.dtp.client.utils.OkHttpUtils;
import com.coderandyli.dtp.core.metrics.ThreadPoolManager;
import com.coderandyli.dtp.core.ModifyThreadPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 定时拉取线程池配置信息
 *
 * @Date 2021/9/3 3:17 下午
 * @Created by lizhenzhen
 */
@Slf4j
@Component
public class ThreadPoolConfigPullHandler {

    private final ScheduledExecutorService executor;

    @Autowired
    private ThreadPoolConfigProperties configProperties;

    public ThreadPoolConfigPullHandler() {
        this.executor = Executors.newSingleThreadScheduledExecutor();
    }


    @PostConstruct
    public void init() {
        execSingleThreadScheduledExecutor();
    }

    /**
     * 执行定时任务
     */
    public void execSingleThreadScheduledExecutor() {
        long period = configProperties.getAdmin().getPeriod();
        boolean isPause = configProperties.getAdmin().isPausePull();
        if (log.isDebugEnabled()) {
            log.debug("Reading configuration information from yml, the dtp.admin.period is 【{}】, dtp.admin.pausePull is 【{}】", period, isPause);
        }
        executor.scheduleAtFixedRate(() -> {
            if (!isPause) {
                if (log.isDebugEnabled()) {
                    log.debug("start pull pool config...");
                }
                startPullPoolConfig();
            }
        }, 0l, period, TimeUnit.SECONDS);
    }

    /**
     * 开始拉取线程池配置并更新配置
     *
     * @return
     */
    private void startPullPoolConfig() {
        String applicationName = configProperties.getApplication();
        String adminBaseUrl = configProperties.getAdmin().getBaseUrl();

        String url = adminBaseUrl + "/admin/" + applicationName + "/thread-pool/config/query";
        if (log.isDebugEnabled()) {
            log.debug("start pull pool configuration, the url is【{}】", url);
        }
        try {
            String result = OkHttpUtils.get(url);
            if (result == null) return;

            List<ModifyThreadPool> configs = JSONObject.parseArray(result, ModifyThreadPool.class);
            if (CollectionUtils.isEmpty(configs)) {
                if (log.isDebugEnabled()) {
                    log.debug("the pool configuration was not pulled. the applicaiton is 【{}】", applicationName);
                }
                return;
            }
            if (log.isDebugEnabled()) {
                log.debug("the pool configuration pull successful. the result is 【{}】", Arrays.toString(configs.toArray()));
            }

            List<String> executedtpIds = new ArrayList<>();
            for (ModifyThreadPool modifyThreadPool : configs) {
                ThreadPoolManager.getInstance().changed(modifyThreadPool);
                executedtpIds.add(modifyThreadPool.getTpId());
            }
            // 线程池配置标记已执行
            markExecuted(executedtpIds, adminBaseUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    /**
     * 标记已执行
     *
     * @param executedtpIds
     */
    private void markExecuted(List<String> executedtpIds, String adminBaseUrl) {
        if (CollectionUtils.isEmpty(executedtpIds)) return;

        String url = adminBaseUrl + "/admin/thread-pool/config/executed";
        try {
            String reuslt = OkHttpUtils.post(url, JSON.toJSONString(executedtpIds));
            if (log.isDebugEnabled()) {
                log.debug("The request result is 【{}】", reuslt);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
