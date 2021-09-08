package com.coderandyli.dynamic.thread.pool.monitor.alert.notification;

import com.coderandyli.dynamic.thread.pool.monitor.alert.sender.MsgSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 通知类
 *
 * @Date 2021/8/26 2:26 下午
 * @Created by lizhenzhen
 */
@Component
public abstract class Notification {

    protected MsgSender msgSender;

    @Autowired
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
