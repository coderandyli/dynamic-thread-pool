package com.coderandyli.dynamic.thread.pool.client.config;

import com.coderandyli.dynamic.thread.pool.client.properties.AdminProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * configuration properties for Dynamic thread pool
 *
 * @Date 2021/9/6 3:18 下午
 * @Created by lizhenzhen
 */
@Component
@Data
@ConfigurationProperties(prefix = "dynamic-thread-pool")
public class ThreadPoolConfigProperties {
    /**
     * 应用名称
     */
    private String applicationName;
    /**
     * admin
     */
    private AdminProperties admin;
}
