package com.coderandyli.dynamic.thread.pool.client.properties;

import lombok.Data;

/**
 * @Date 2021/9/6 3:45 下午
 * @Created by lizhenzhen
 */
@Data
public class AdminProperties {
    /**
     * 默认端口为7701
     */
    private String port;
    private String baseUrl;
    /**
     * 线程池配置拉取周期（即：线程池配置更新频率）
     * 默认为10s
     */
    private long period = 10;
}
