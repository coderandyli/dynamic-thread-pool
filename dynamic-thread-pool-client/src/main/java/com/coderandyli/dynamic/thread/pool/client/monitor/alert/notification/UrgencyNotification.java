package com.coderandyli.dynamic.thread.pool.client.monitor.alert.notification;

import com.coderandyli.dynamic.thread.pool.client.monitor.alert.sender.MsgSender;

/**
 * 【紧急】通知
 *
 * @Date 2021/8/26 3:25 下午
 * @Created by lizhenzhen
 */
public class UrgencyNotification extends Notification{
    public UrgencyNotification(MsgSender msgSender) {
        super(msgSender);
    }

    @Override
    public void notify(String msg) {
        this.msgSender.send("【Urgency alert】" + msg);
    }
}
