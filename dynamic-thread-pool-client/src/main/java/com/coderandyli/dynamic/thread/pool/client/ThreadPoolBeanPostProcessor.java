package com.coderandyli.dynamic.thread.pool.client;

import com.coderandyli.dynamic.thread.pool.client.monitor.metrics.MetricsCollector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * call this class before or after creating the bean of DynamicThreadPoolExecutor.
 *
 * @Date 2021/8/27 1:38 下午
 * @Created by lizhenzhen
 */
@Slf4j
@Component
public class ThreadPoolBeanPostProcessor implements BeanPostProcessor {
    @Autowired
    private MetricsCollector metricsCollector;

    public ThreadPoolBeanPostProcessor() {
    }

    /**
     * Spring Bean 在实例化之前会调用
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * Spring Bean 在实例化之后调用
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof DynamicThreadPoolExecutor) {
            // regitster thread pool
            log.debug("register the thread pool");
            metricsCollector.registerExecutorService((DynamicThreadPoolExecutor) bean);
        }
        return bean;
    }
}
