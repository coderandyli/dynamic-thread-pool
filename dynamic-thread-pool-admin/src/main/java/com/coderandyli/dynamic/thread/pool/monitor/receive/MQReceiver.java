package com.coderandyli.dynamic.thread.pool.monitor.receive;

import com.coderandyli.dynamic.thread.pool.client.config.RabbitConfig;
import com.coderandyli.dynamic.thread.pool.client.ThreadPoolDynamicInfo;
import com.coderandyli.dynamic.thread.pool.client.ThreadTaskInfo;
import com.coderandyli.dynamic.thread.pool.monitor.metrics.storage.MetricsStorage;
import com.coderandyli.dynamic.thread.pool.client.utils.JsonUtil;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @Date 2021/8/27 4:24 下午
 * @Created by lizhenzhen
 */
@Slf4j
@Component
public class MQReceiver {

    @Autowired
    @Qualifier("mysqlMetricsStorage")
    private MetricsStorage metricsStorage;

    @RabbitListener(queues = RabbitConfig.DTP_METRCS_STORAGE_THREADPOOL_QUEUE)
    public void receiveTheadPoolMsg(String msg, Message message, Channel channel) {
        ThreadPoolDynamicInfo threadPoolInfo = (ThreadPoolDynamicInfo) JsonUtil.fromJson(msg, ThreadPoolDynamicInfo.class);
        if (log.isDebugEnabled()) {
            log.debug("receiver msg from queue 【{}】 the msg is 【{}】", RabbitConfig.DTP_METRCS_STORAGE_THREADPOOL_QUEUE, threadPoolInfo);
        }
        metricsStorage.saveThreadPoolInfo(threadPoolInfo);
    }

    @RabbitListener(queues = RabbitConfig.DTP_METRCS_STORAGE_TASK_QUEUE)
    public void receiveTaskMsg(String msg, Message message, Channel channel) {
        ThreadTaskInfo taskInfo = (ThreadTaskInfo) JsonUtil.fromJson(msg, ThreadTaskInfo.class);
        if (log.isDebugEnabled()) {
            log.debug("receiver msg from queue 【{}】 the msg is 【{}】", RabbitConfig.DTP_METRCS_STORAGE_TASK_QUEUE, taskInfo);
        }
        metricsStorage.saveTaskInfo(taskInfo);
    }
}
