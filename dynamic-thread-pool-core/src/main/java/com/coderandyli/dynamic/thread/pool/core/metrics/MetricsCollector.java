package com.coderandyli.dynamic.thread.pool.core.metrics;

import com.coderandyli.dynamic.thread.pool.core.DynamicThreadPoolExecutor;
import com.coderandyli.dynamic.thread.pool.core.ThreadPoolDynamicInfo;
import com.coderandyli.dynamic.thread.pool.core.ThreadTaskInfo;
import com.coderandyli.dynamic.thread.pool.core.config.RabbitConfig;
import com.coderandyli.dynamic.thread.pool.core.utils.JsonUtil;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;

/**
 * 提供API, 采集接口请求的原始数据
 * <p>
 * 为防止一次性加载太多数据到内存，导致内存吃紧，甚至内存溢出
 * 采用 Google Guava EventBus 生产者-消费者模型，将采集的数据先放入内存共享队列中，另一个线程读取共享队列中的数据，写入到外部存储中，
 *
 * @author lizhen
 * @version 1.0
 * @date 2020/1/3 上午9:48
 * <p>
 */
@Component("metricsCollector")
public class MetricsCollector {
    private static final int DEFAULT_STORAGE_THREAD_POOL_SIZE = 20;

    private EventBus eventBus;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public MetricsCollector() {
        this.eventBus = new AsyncEventBus(Executors.newFixedThreadPool(DEFAULT_STORAGE_THREAD_POOL_SIZE));
        this.eventBus.register(new EventListener());
    }

    /**
     * 注册线程池
     *
     * @param threadPoolExecutor
     */
    public void registerExecutorService(DynamicThreadPoolExecutor threadPoolExecutor) {
        ThreadPoolManager threadPoolManager = ThreadPoolManager.getInstance();
        threadPoolManager.registerExecutorService(threadPoolExecutor.getUniqueId(), threadPoolExecutor);
    }

    /**
     * 记录线程任务信息
     *
     * @param taskInfo
     */
    public void recordTask(ThreadTaskInfo taskInfo) {
        if (taskInfo == null || StringUtils.isBlank(taskInfo.getTaskName())) {
            return;
        }
        eventBus.post(taskInfo);
    }

    /**
     * 记录线程池信息
     *
     * @param threadPoolInfo
     */
    public void recordThreadPoolInfo(ThreadPoolDynamicInfo threadPoolInfo) {
        if (threadPoolInfo == null || StringUtils.isBlank(threadPoolInfo.getPoolName())) {
            return;
        }
        eventBus.post(threadPoolInfo);
    }

    public class EventListener {
        @Subscribe
        public void saveRequestInfo(ThreadTaskInfo taskInfo) {
            // send msg to MQ
            rabbitTemplate.convertAndSend(RabbitConfig.METRICS_EXCHANGE, RabbitConfig.DTP_METRCS_STORAGE_TASK_ROUTING_KEY, JsonUtil.toJson(taskInfo));
        }

        @Subscribe
        public void saveThreadPoolInfo(ThreadPoolDynamicInfo threadPoolInfo) {
            // send msg to MQ
            rabbitTemplate.convertAndSend(RabbitConfig.METRICS_EXCHANGE, RabbitConfig.DTP_METRCS_STORAGE_THREADPOOL_ROUTING_KEY, JsonUtil.toJson(threadPoolInfo));
        }
    }
}
