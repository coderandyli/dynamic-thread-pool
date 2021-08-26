package com.coderandyli.dynamic.thread.pool.client.monitor.alert.notification;

import com.coderandyli.dynamic.thread.pool.client.monitor.alert.sender.MsgSender;

/**
 * 【无关紧要】通知
 *
 * @Date 2021/8/26 3:34 下午
 * @Created by lizhenzhen
 */
public class TrivialNotification extends Notification{

    public TrivialNotification(MsgSender msgSender) {
        super(msgSender);
    }

    @Override
    public void notify(String msg) {
        this.msgSender.send("【Trivial alert】" + msg);
    }
}
