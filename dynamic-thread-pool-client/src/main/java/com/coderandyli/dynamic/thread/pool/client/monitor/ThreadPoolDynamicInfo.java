package com.coderandyli.dynamic.thread.pool.client.monitor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;

/**
 * 线程池动态信息
 *
 * @Date 2021/8/12 5:27 下午
 * @Created by lizhenzhen
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ThreadPoolDynamicInfo {
    /**
     * 1: 业务触发；2: 定时收集
     */
    private Integer type;
    /**
     * 应用名称
     */
    private String applicationName;
    /**
     * 线程池名称
     */
    private String poolName;
    /**
     * 线程池唯一识别号
     */
    private String uniqueId;
    /**
     * 活跃线程数（workers中工作线程数）
     */
    private int activeCount;
    /**
     * 完成任务数
     */
    private long completedTaskCount;
    /**
     * 核心线程数
     */
    private int corePoolSize;
    /**
     * 存活时间
     */
    private long keepAliveTime;
    /**
     * 线程池中工作线程数增加达到的最大值
     */
    private int largestPoolSize;
    /**
     * 最大线程数
     */
    private int maximumPoolSize;
    /**
     * 当前线程池中的线程数
     *  - 当线程状态大于等于TIDYING时，等于0；否则等于workers.size()
     */
    private int poolSize;
    /**
     * 等待队列
     */
    private BlockingQueue<Runnable> queue;
    /**
     * 拒绝策略
     */
    private RejectedExecutionHandler rejectedExecutionHandler;
    /**
     * 任务数（已完成的任务 + 正在进行中的任务）
     */
    private int taskCount;
    /**
     * 任务拒绝次数
     */
    private int rejectCount;
    /**
     * 生成时间
     */
    private long createTime;

}
