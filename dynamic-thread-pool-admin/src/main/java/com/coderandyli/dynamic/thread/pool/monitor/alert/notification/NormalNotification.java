package com.coderandyli.dynamic.thread.pool.monitor.alert.notification;

import com.coderandyli.dynamic.thread.pool.monitor.alert.sender.MsgSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * 普通通知
 *  - send console
 *
 * @Date 2021/8/26 3:25 下午
 * @Created by lizhenzhen
 */
@Primary
@Component("normalNotification")
public class NormalNotification extends Notification{

    @Autowired
    public NormalNotification(@Qualifier("consoleMsgSender") MsgSender msgSender) {
        super(msgSender);
    }

    @Override
    public void notify(String msg) {
        this.msgSender.send("【Normal alert】" + msg);
    }
}
