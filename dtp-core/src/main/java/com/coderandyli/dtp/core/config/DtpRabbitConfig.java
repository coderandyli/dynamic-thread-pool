package com.coderandyli.dtp.core.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Rabbit Configuration
 *
 * @author Hanping QIAO
 * 2018-09-06 下午3:41
 **/
@Configuration
public class DtpRabbitConfig {
    /**
     * EXCHANGE NAME of METRICS
     * 命名方式：{applicatioNname}.{业务名称}
     * dtp：dynamic thread pool
     */
    public static final String METRICS_EXCHANGE = "dtp.metrics";

    /**
     * QUEUE NAME
     * 命名方式：{applicatioNname_{业务名称}_{自定义}
     */
    public static final String DTP_METRCS_STORAGE_TASK_QUEUE = "dtp_metrcs_storage_task";
    public static final String DTP_METRCS_STORAGE_THREADPOOL_QUEUE = "dtp_metrcs_storage_threadpool";

    /**
     * TOPIC ROUTING KEY FOR QUEUE
     */
    public static final String DTP_METRCS_STORAGE_TASK_ROUTING_KEY = "dtp.metrcs.storage_task";
    public static final String DTP_METRCS_STORAGE_THREADPOOL_ROUTING_KEY = "dtp.metrcs.storage_threadpool";

    @Bean
    @Qualifier("metrcsStorageTaskQueue")
    public Queue metrcsStorageTaskQueue() {
        return new Queue(DTP_METRCS_STORAGE_TASK_QUEUE,true);
    }
    @Bean
    @Qualifier("metrcsStorageThreadPoolQueue")
    public Queue metrcsStorageThreadPoolQueue() {
        return new Queue(DTP_METRCS_STORAGE_THREADPOOL_QUEUE,true);
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(METRICS_EXCHANGE);
    }

    /**
     * Queue通过Binding与Exchange进行绑定
     *
     * @param queue
     * @param topicExchange
     * @return
     */
    @Bean
    public Binding bindingDtpMetrcsStorageTask(@Qualifier("metrcsStorageTaskQueue") Queue queue, TopicExchange topicExchange){
        return BindingBuilder.bind(queue).to(topicExchange).with(DTP_METRCS_STORAGE_TASK_ROUTING_KEY);
    }
    @Bean
    public Binding bindingDtpMetrcsStorageThreadPool(@Qualifier("metrcsStorageThreadPoolQueue") Queue queue, TopicExchange topicExchange){
        return BindingBuilder.bind(queue).to(topicExchange).with(DTP_METRCS_STORAGE_THREADPOOL_ROUTING_KEY);
    }
}
