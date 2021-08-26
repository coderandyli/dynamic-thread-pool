package com.coderandyli.dynamic.thread.pool.client.monitor.alert.notification;

import com.coderandyli.dynamic.thread.pool.client.monitor.alert.sender.MsgSender;

/**
 * 普通通知
 *
 * @Date 2021/8/26 3:25 下午
 * @Created by lizhenzhen
 */
public class NormalNotification extends Notification{
    public NormalNotification(MsgSender msgSender) {
        super(msgSender);
    }

    @Override
    public void notify(String msg) {
        this.msgSender.send("【Normal alert】" + msg);
    }
}
