package com.coderandyli.dynamic.thread.pool.client;

import org.springframework.beans.BeanUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池管理器
 *
 * @Date 2021/8/18 4:03 下午
 * @Created by lizhenzhen
 */
public class ExecutorManager {
    private volatile static ExecutorManager instance;

    private final Map<String, ExecutorService> executorServiceMap = new ConcurrentHashMap<>();

    public ExecutorManager() {

    }

    /**
     * register thread pool
     *
     * @param name
     * @param executorService
     */
    public void registerExecutorService(String name, ExecutorService executorService) {
        executorServiceMap.put(name, executorService);
    }

    /**
     * get thead pool
     *
     * @param name 线程池名称
     * @return
     */
    public ExecutorService getExecutorService(String name) {
        if (!executorServiceMap.containsKey(name)) {
            return null;
        }
        return executorServiceMap.get(name);
    }

    public ThreadPoolDynamicInfo getThreadPoolInfo(String name) {
        ExecutorService executorService = getExecutorService(name);
        if (!(executorService instanceof DynamicThreadPoolExecutor)) {
            return null;
        }
        DynamicThreadPoolExecutor threadPoolExecutor = (DynamicThreadPoolExecutor) executorService;
        return covert2ThreadPoolDynamicInfo(threadPoolExecutor);
    }

    public ThreadPoolDynamicInfo covert2ThreadPoolDynamicInfo(DynamicThreadPoolExecutor threadPoolExecutor) {
        ThreadPoolDynamicInfo threadPoolInfo = new ThreadPoolDynamicInfo();
        BeanUtils.copyProperties(threadPoolExecutor, threadPoolInfo);


//        infoDto.setKeepAliveTime(
//                bizOrderExecutor.getKeepAliveTime()
//        );

        // RejectedExecutionHandler rejectedExecutionHandler = threadPoolExecutor.getRejectedExecutionHandler();
        return threadPoolInfo;
    }


    /**
     * modify the thead pool configuration
     *
     * @param poolName
     * @param config
     */
    public void changed(String poolName, String config) {
        if (config == null) {
            executorServiceMap.remove(poolName);
            return;
        }

        ExecutorService executorService = executorServiceMap.get(poolName);
        if (!(executorService instanceof ThreadPoolExecutor)) {
            return;
        }
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executorService;
        // TODO: 2021/8/18 修改线程池配置
        // threadPoolExecutor.setCorePoolSize();
        // threadPoolExecutor.setMaximumPoolSize();
        // threadPoolExecutor.setKeepAliveTime();
    }

    public static ExecutorManager getInstance() {
        if (instance == null) {
            synchronized (ExecutorManager.class) {
                if (instance == null) {
                    instance = new ExecutorManager();
                }
            }
        }
        return instance;
    }

    public Map<String, ExecutorService> getExecutorServiceMap() {
        return executorServiceMap;
    }
}
