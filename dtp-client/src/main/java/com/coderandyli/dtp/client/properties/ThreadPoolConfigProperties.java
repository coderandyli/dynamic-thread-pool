package com.coderandyli.dtp.client.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * configuration properties for Dynamic thread pool
 *
 * <h3> QA <h3/>
 * Q: Spring Boot Configuration Annotation Processor not configured.
 * A: 引入依赖
 * ```
 * <dependency>
 * <groupId>org.springframework.boot</groupId>
 * <artifactId>spring-boot-configuration-processor</artifactId>
 * <optional>true</optional>
 * </dependency>
 * ```
 * 引入后，配置文件对该类赋值时，会弹出提示信息
 *
 * @Date 2021/9/6 3:18 下午
 * @Created by lizhenzhen
 */
@Component
@Data
@ConfigurationProperties(prefix = "dtp")
public class ThreadPoolConfigProperties {
    /**
     * 应用名称
     */
    private String application;
    /**
     * admin
     */
    private AdminProperties admin;
}
