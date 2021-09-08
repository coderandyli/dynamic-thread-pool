package com.coderandyli.dtp.client.properties;

import lombok.Data;

/**
 * @Date 2021/9/6 3:45 下午
 * @Created by lizhenzhen
 */
@Data
public class AdminProperties {
    /**
     * admin 基础路径
     */
    private String baseUrl;
    /**
     * 默认端口为7701
     */
    private int port = 7701;
    /**
     * 是否暂停拉取配置，默认为false
     */
    private boolean pausePull = false;
    /**
     * 线程池配置拉取周期（即：线程池配置更新频率）
     * 默认为10s
     */
    private long period = 10;
}
