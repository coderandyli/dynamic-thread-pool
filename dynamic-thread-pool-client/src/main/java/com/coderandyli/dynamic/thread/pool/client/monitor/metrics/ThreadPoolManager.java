package com.coderandyli.dynamic.thread.pool.client.monitor.metrics;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池管理器
 *
 * @Date 2021/8/18 4:03 下午
 * @Created by lizhenzhen
 */
@Slf4j
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
     * @param threadPoolId 线程池Id
     * @param config
     */
    public void changed(String threadPoolId, String config) {
        if (config == null) {
            executorServiceMap.remove(threadPoolId);
            return;
        }

        ExecutorService executorService = executorServiceMap.get(threadPoolId);
        if (!(executorService instanceof ThreadPoolExecutor)) {
            return;
        }
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executorService;
        // TODO: 2021/8/18 修改线程池配置
        // threadPoolExecutor.setCorePoolSize();
        // threadPoolExecutor.setMaximumPoolSize();
        // threadPoolExecutor.setKeepAliveTime();
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
