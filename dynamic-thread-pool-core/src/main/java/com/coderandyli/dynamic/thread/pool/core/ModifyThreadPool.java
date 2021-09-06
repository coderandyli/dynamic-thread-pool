package com.coderandyli.dynamic.thread.pool.core;

import lombok.Data;
import lombok.ToString;

/**
 * @Date 2021/9/3 10:01 上午
 * @Created by lizhenzhen
 */
@Data
@ToString
public class ModifyThreadPool {

    private Long id;
    /**
     * 应用名称
     */
    private String application;
    /**
     * 线程池唯一识别号
     */
    private String tpId;
    /**
     * 核心线程数
     */
    private int corePoolSize;
    /**
     * 最大线程数
     */
    private int maximumPoolSize;
    /**
     * 存活时间(单位为秒)
     */
    private long keepAliveTime;

    // TODO: 2021/9/3 后续可补充修改队列长度
}
