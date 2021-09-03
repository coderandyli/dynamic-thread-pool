package com.coderandyli.dynamic.thread.pool.core.metrics;

import com.coderandyli.dynamic.thread.pool.core.ModifyThreadPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池管理器
 *
 * @Date 2021/8/18 4:03 下午
 * @Created by lizhenzhen
 */
@Slf4j
@Component
public class ThreadPoolManager {
    private volatile static ThreadPoolManager instance;
    private final Map<String, ExecutorService> executorServiceMap = new ConcurrentHashMap<>();

    public ThreadPoolManager() {
    }

    /**
     * get thead pool
     *
     * @param threadPoolId 线程池Id
     * @return
     */
    public ExecutorService getExecutorService(String threadPoolId) {
        if (!executorServiceMap.containsKey(threadPoolId)) {
            return null;
        }
        return executorServiceMap.get(threadPoolId);
    }

    /**
     * register thread pool
     *
     * @param threadPoolId    线程池Id
     * @param executorService
     */
    protected void registerExecutorService(String threadPoolId, ExecutorService executorService) {
        executorServiceMap.put(threadPoolId, executorService);
        log.debug("The thread pool is successfully registered，the threadPoolId name is 【{}】", threadPoolId);
    }


    /**
     * modify the thead pool configuration
     *
     * @param config 线程池配置信息
     */
    public void changed(ModifyThreadPool config) {
        String tpId = config.getTpId();
        ExecutorService executorService = executorServiceMap.get(tpId);
        if (!(executorService instanceof ThreadPoolExecutor)) {
            return;
        }

        if (log.isDebugEnabled()) {
            log.debug("The thread pool configuration will be modified. the arg is 【{}】", config);
        }
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executorService;
        threadPoolExecutor.setCorePoolSize(config.getCorePoolSize());
        threadPoolExecutor.setMaximumPoolSize(config.getMaximumPoolSize());
        threadPoolExecutor.setKeepAliveTime(config.getKeepAliveTime(), TimeUnit.SECONDS);

        log.info("The thread pool configuration is modified successfull， the arg is【{}】", config);
    }


    public static ThreadPoolManager getInstance() {
        if (instance == null) {
            synchronized (ThreadPoolManager.class) {
                if (instance == null) {
                    instance = new ThreadPoolManager();
                }
            }
        }
        return instance;
    }

    public Map<String, ExecutorService> getExecutorServiceMap() {
        return executorServiceMap;
    }
}
