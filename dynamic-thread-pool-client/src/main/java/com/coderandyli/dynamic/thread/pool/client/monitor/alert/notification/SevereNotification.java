package com.coderandyli.dynamic.thread.pool.client.monitor.alert.notification;

import com.coderandyli.dynamic.thread.pool.client.monitor.alert.sender.MsgSender;

/**
 * 【严重】通知
 *
 * @Date 2021/8/26 3:36 下午
 * @Created by lizhenzhen
 */
public class SevereNotification extends Notification{
    public SevereNotification(MsgSender msgSender) {
        super(msgSender);
    }

    @Override
    public void notify(String msg) {
        this.msgSender.send("【Severe alert】" + msg);
    }
}
