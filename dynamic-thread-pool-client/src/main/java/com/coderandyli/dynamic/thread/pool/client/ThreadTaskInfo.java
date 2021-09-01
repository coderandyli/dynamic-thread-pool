package com.coderandyli.dynamic.thread.pool.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Date 2021/8/18 9:53 上午
 * @Created by lizhenzhen
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ThreadTaskInfo {
    /**
     * 线程池唯一识别号
     */
    private String threadPoolUniqueId;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 响应时间
     */
    private double responseTime;
    /**
     * 开始请求时间
     */
    private long timestamp;
}
