package com.coderandyli.dynamic.thread.pool.client;

import com.alibaba.fastjson.JSONObject;
import com.coderandyli.dynamic.thread.pool.core.ModifyThreadPool;
import com.coderandyli.dynamic.thread.pool.core.metrics.ThreadPoolManager;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
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
public class ThreadPoolHandler {
    private final ScheduledExecutorService executor;

    private static final String ADMIN_BASE_URL = "http://192.168.30.12:7701";
    /**
     * 是否暂定
     */
    private boolean isPause = false;

    public ThreadPoolHandler() {
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
        executor.scheduleAtFixedRate(() -> {
            if (!isPause) {
                if (log.isDebugEnabled()) {
                    log.debug("start pull pool config...");
                }
                startPullPoolConfig();
            }
        }, 0l, 10, TimeUnit.SECONDS);
    }

    /**
     * 开始拉取线程池配置并更新配置
     *
     * @return
     */
    private String startPullPoolConfig() {
        String application = "test-app";
        String url = ADMIN_BASE_URL + "/admin/" + application + "/thread-pool/query";

        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            ResponseBody body = response.body();
            if (body == null) return null;

            List<ModifyThreadPool> configs = JSONObject.parseArray(body.string(), ModifyThreadPool.class);
            for (ModifyThreadPool modifyThreadPool : configs) {
                ThreadPoolManager.getInstance().changed(modifyThreadPool);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
