package com.coderandyli.dtp.demo.config;

import com.coderandyli.dtp.core.DynamicThreadPoolExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
        DynamicThreadPoolExecutor dynamicThreadPoolExecutor = new DynamicThreadPoolExecutor(
                "dtp-demo",
                "biz-order",
                2 * NCPUS,
                25 * NCPUS,
                20,
                TimeUnit.MILLISECONDS,
                new SynchronousQueue<>());
        return dynamicThreadPoolExecutor;
    }
}
