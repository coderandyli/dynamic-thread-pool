package com.coderandyli.dtp.monitor.alert.handler;

import com.coderandyli.dtp.monitor.alert.AlertRule;
import com.coderandyli.dtp.monitor.alert.StatInfo;
import com.coderandyli.dtp.monitor.alert.notification.Notification;

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

    public AlertHandler(Notification notification) {
        this.notification = notification;
        this.alertRule = new AlertRule();
    }

    public abstract void check(StatInfo statInfo);
}
