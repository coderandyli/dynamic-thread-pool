package com.coderandyli.dynamic.thread.pool.monitor.alert.handler;

import com.coderandyli.dynamic.thread.pool.monitor.alert.AlertRule;
import com.coderandyli.dynamic.thread.pool.monitor.alert.notification.Notification;
import com.coderandyli.dynamic.thread.pool.monitor.alert.StatInfo;

/**
 * 警告处理器
 *
 * @Date 2021/8/26 1:45 下午
 * @Created by lizhenzhen
 */
public abstract class AlertHandler {
    /**
     * 消息通知
     */
    protected Notification notification;
    /**
     * 警告规则
     */
    protected AlertRule alertRule;

    public AlertHandler(Notification notification, AlertRule alertRule) {
        this.notification = notification;
        this.alertRule = alertRule;
    }

    public abstract void check(StatInfo statInfo);
}
