package com.coderandyli.dynamic.thread.pool.monitor;

import lombok.Data;
import lombok.ToString;

/**
 * 任务线程聚合统计信息
 *
 * @Date 2021/8/12 5:27 下午
 * @Created by lizhenzhen
 */
@Data
@ToString
public class TaskStat {
    /**
     * 最大执行时间
     */
    private Double maxExecuteTime;
    /**
     * 最小响应时间
     */
    private Double minExecuteTime;
    /**
     * 平均响应时间
     */
    private Double avgExecuteTime;
    /**
     * 99.9%响应时间
     */
    private Double p999ExecuteTime;
    /**
     * 99%响应时间
     */
    private Double p99ExecuteTime;
    /**
     * 调用次数
     */
    private long count;
    /**
     * tps
     */
    private int tps;

    public TaskStat() {
    }

    public TaskStat(Double maxExecuteTime, Double minExecuteTime, Double avgExecuteTime, Double p999ExecuteTime, Double p99ExecuteTime, long count, int tps) {
        this.maxExecuteTime = maxExecuteTime;
        this.minExecuteTime = minExecuteTime;
        this.avgExecuteTime = avgExecuteTime;
        this.p999ExecuteTime = p999ExecuteTime;
        this.p99ExecuteTime = p99ExecuteTime;
        this.count = count;
        this.tps = tps;
    }
}
