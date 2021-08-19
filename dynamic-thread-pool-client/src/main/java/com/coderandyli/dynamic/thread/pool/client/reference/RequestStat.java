package com.coderandyli.dynamic.thread.pool.client.reference;

import lombok.Data;
import lombok.ToString;

/**
 * @author lizhen
 * @version 1.0
 * @date 2020/1/3 上午10:45
 */
@Data
@ToString
public class RequestStat {
    /**
     * 最大响应时间
     */
    private Double maxResponseTime;
    /**
     * 最小响应时间
     */
    private Double minResponseTime;
    /**
     * 平均响应时间
     */
    private Double avgResponseTime;
    /**
     * 99.9%响应时间
     */
    private Double p999ResponseTime;
    /**
     * 99%响应时间
     */
    private Double p99ResponseTime;
    /**
     * 调用次数
     */
    private long count;
    /**
     * tps
     */
    private int tps;

    public RequestStat() {
    }

    public RequestStat(Double maxResponseTime, Double minResponseTime, Double avgResponseTime, Double p999ResponseTime, Double p99ResponseTime, long count, int tps) {
        this.maxResponseTime = maxResponseTime;
        this.minResponseTime = minResponseTime;
        this.avgResponseTime = avgResponseTime;
        this.p999ResponseTime = p999ResponseTime;
        this.p99ResponseTime = p99ResponseTime;
        this.count = count;
        this.tps = tps;
    }
}
