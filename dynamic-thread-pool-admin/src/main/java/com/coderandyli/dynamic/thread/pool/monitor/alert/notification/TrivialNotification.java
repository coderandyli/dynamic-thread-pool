package com.coderandyli.dynamic.thread.pool.monitor.alert.notification;

import com.coderandyli.dynamic.thread.pool.monitor.alert.sender.MsgSender;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 【无关紧要】通知
 *  - send console
 *
 * @Date 2021/8/26 3:34 下午
 * @Created by lizhenzhen
 */
@Component("trivialNotification")
public class TrivialNotification extends Notification{

    public TrivialNotification(@Qualifier("consoleMsgSender") MsgSender msgSender) {
        super(msgSender);
    }

    @Override
    public void notify(String msg) {
        this.msgSender.send("【Trivial alert】" + msg);
    }
}
