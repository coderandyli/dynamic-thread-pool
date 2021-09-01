package com.coderandyli.dynamic.thread.pool.monitor.alert.notification;

import com.coderandyli.dynamic.thread.pool.monitor.alert.sender.MsgSender;

/**
 * 通知类
 *
 * @Date 2021/8/26 2:26 下午
 * @Created by lizhenzhen
 */
public abstract class Notification {

    protected MsgSender msgSender;

    public Notification(MsgSender msgSender) {
        this.msgSender = msgSender;
    }

    /**
     * 通知
     *
     * @param msg
     */
    public abstract void notify(String msg);
}
