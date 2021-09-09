package com.coderandyli.dtp.monitor.receive;

import com.coderandyli.dtp.core.ThreadPoolDynamicInfo;
import com.coderandyli.dtp.core.ThreadTaskInfo;
import com.coderandyli.dtp.core.config.DtpRabbitConfig;
import com.coderandyli.dtp.core.utils.JsonUtil;
import com.coderandyli.dtp.monitor.metrics.storage.MetricsStorage;
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

    @RabbitListener(queues = DtpRabbitConfig.DTP_METRCS_STORAGE_THREADPOOL_QUEUE)
    public void receiveTheadPoolMsg(String msg, Message message, Channel channel) {
        ThreadPoolDynamicInfo threadPoolInfo = (ThreadPoolDynamicInfo) JsonUtil.fromJson(msg, ThreadPoolDynamicInfo.class);
        if (log.isDebugEnabled()) {
            log.debug("receiver msg from queue 【{}】 the msg is 【{}】", DtpRabbitConfig.DTP_METRCS_STORAGE_THREADPOOL_QUEUE, threadPoolInfo);
        }
        metricsStorage.saveThreadPoolInfo(threadPoolInfo);
    }

    @RabbitListener(queues = DtpRabbitConfig.DTP_METRCS_STORAGE_TASK_QUEUE)
    public void receiveTaskMsg(String msg, Message message, Channel channel) {
        ThreadTaskInfo taskInfo = (ThreadTaskInfo) JsonUtil.fromJson(msg, ThreadTaskInfo.class);
        if (log.isDebugEnabled()) {
            log.debug("receiver msg from queue 【{}】 the msg is 【{}】", DtpRabbitConfig.DTP_METRCS_STORAGE_TASK_QUEUE, taskInfo);
        }
        metricsStorage.saveTaskInfo(taskInfo);
    }
}
