package com.coerandyli.dynamic.thread.pool.admin.test.config;

import com.coderandyli.dynamic.thread.pool.client.DynamicThreadPoolExecutor;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * 业务线程池
 *
 * @Date 2021/7/16 2:37 下午
 * @Created by lizhenzhen
 */
@Configuration
public class BizThreadPoolConfig {
    /**
     * The number of CPUs
     */
    private static final int NCPUS = Runtime.getRuntime().availableProcessors(); // 8

    /**
     * 订单业务线程池
     */
    @Bean(value = "bizOrderThreadPool")
    public ThreadPoolExecutor buildBizOrderQueueThreadPool() {
//        DynamicThreadPoolExecutor dynamicThreadPoolExecutor = new DynamicThreadPoolExecutor(
//                "test-app",
//                "biz-order",
//                2 * NCPUS,
//                25 * NCPUS,
//                20,
//                TimeUnit.MILLISECONDS,
//                new SynchronousQueue<>());

        // 模拟任务拒绝
        DynamicThreadPoolExecutor dynamicThreadPoolExecutor = new DynamicThreadPoolExecutor(
                "test-app",
                "biz-order",
                2,
                4,
                20,
                TimeUnit.MILLISECONDS,
                new SynchronousQueue<>());

        return dynamicThreadPoolExecutor;
    }
}
