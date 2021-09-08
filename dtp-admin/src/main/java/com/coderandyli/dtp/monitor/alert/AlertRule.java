package com.coderandyli.dtp.monitor.alert;

/**
 * 警告规则
 *
 * @Date 2021/8/26 2:23 下午
 * @Created by lizhenzhen
 */
public class AlertRule {
    public int getMaxErrorCount() {
        return 5;
    }

    public int getMaxTps() {
        return 5;
    }

    public int getMaxRejectCount() {
        return 5;
    }

    public int getMaxQueueSize() {
        return 10;
    }

    /**
     * 最大线程池活跃度
     *
     * @return
     */
    public double getMaxActive() {
        return 0.8d;
    }


}
