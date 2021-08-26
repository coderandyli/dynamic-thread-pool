package com.coderandyli.dynamic.thread.pool.client.monitor.alert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * 统计信息
 *
 * @Date 2021/8/26 1:46 下午
 * @Created by lizhenzhen
 */
@Data
@ToString
@Builder
public class StatInfo {
    /**
     * 线程池id (应用名称:线程池名称)
     */
    private String threadPoolId;
    /**
     * 活跃线程数（workers中工作线程数）
     */
    private int activeCount;
    /**
     * 最大线程数
     */
    private int maximumPoolSize;
    /**
     * 任务数（已完成的任务 + 正在进行中的任务）
     */
    private int taskCount;
    /**
     * 完成任务数
     */
    private long completedTaskCount;
    /**
     * 任务拒绝次数
     */
    private int rejectCount;
    /**
     * 队列容量
     */
    private int queueCapacity;
    /**
     * 当前队列大小
     */
    private int queueCurrentSize;
    /**
     * 当前队列剩余容量
     */
    private int queueRemainingCapacity;

    /**
     * 间隔时间(秒)
     */
    private long durationInSeconds;
}
