package com.coerandyli.dynamic.thread.pool.admin.receive;

import com.coderandyli.dynamic.thread.pool.client.config.RabbitConfig;
import com.coderandyli.dynamic.thread.pool.client.monitor.ThreadTaskInfo;
import com.coderandyli.dynamic.thread.pool.client.utils.JsonUtil;
import com.coderandyli.dynamic.thread.pool.client.monitor.ThreadPoolDynamicInfo;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Date 2021/8/27 4:24 下午
 * @Created by lizhenzhen
 */
@Slf4j
@Component
public class MQReceiver {

    @RabbitListener(queues = RabbitConfig.DTP_METRCS_STORAGE_THREADPOOL_QUEUE)
    public void receiveTheadPoolMsg(String msg, Message message, Channel channel) {

        ThreadPoolDynamicInfo dynamicInfo = (ThreadPoolDynamicInfo) JsonUtil.fromJson(msg, ThreadPoolDynamicInfo.class);
        log.info("receiver msg from queue 【{}】 the msg is 【{}】", RabbitConfig.DTP_METRCS_STORAGE_THREADPOOL_QUEUE, dynamicInfo);
    }

    @RabbitListener(queues = RabbitConfig.DTP_METRCS_STORAGE_TASK_QUEUE)
    public void receiveTaskMsg(String msg, Message message, Channel channel) {

        ThreadTaskInfo taskInfo = (ThreadTaskInfo) JsonUtil.fromJson(msg, ThreadPoolDynamicInfo.class);
        log.info("receiver msg from queue 【{}】 the msg is 【{}】", RabbitConfig.DTP_METRCS_STORAGE_TASK_QUEUE, taskInfo);
    }
}
