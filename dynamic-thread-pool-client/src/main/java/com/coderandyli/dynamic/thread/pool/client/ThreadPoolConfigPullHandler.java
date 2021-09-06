package com.coderandyli.dynamic.thread.pool.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.coderandyli.dynamic.thread.pool.client.config.ThreadPoolConfigProperties;
import com.coderandyli.dynamic.thread.pool.client.utils.OkHttpUtils;
import com.coderandyli.dynamic.thread.pool.core.ModifyThreadPool;
import com.coderandyli.dynamic.thread.pool.core.metrics.ThreadPoolManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Date 2021/9/3 3:17 下午
 * @Created by lizhenzhen
 */
@Slf4j
@Component
public class ThreadPoolConfigPullHandler {

    private final ScheduledExecutorService executor;

    @Autowired
    private ThreadPoolConfigProperties configProperties;

    /**
     * 是否暂定
     */
    private boolean isPause = false;

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
    private String startPullPoolConfig() {
        String applicationName = configProperties.getApplicationName();
        String adminBaseUrl = configProperties.getAdmin().getBaseUrl();

        String url = adminBaseUrl + "/admin/" + applicationName + "/thread-pool/config/query";
        if (log.isDebugEnabled()) {
            log.debug("start pull pool configuration, the url is【{}】", url);
        }
        try {
            String result = OkHttpUtils.get(url);
            if (result == null) return null;

            List<ModifyThreadPool> configs = JSONObject.parseArray(result, ModifyThreadPool.class);
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
        return null;
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
            }
            log.debug("The request result is 【{}】", reuslt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
