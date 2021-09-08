package com.coderandyli.dtp.monitor.alert.notification;

import com.coderandyli.dtp.monitor.alert.sender.MsgSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 【紧急】通知
 *  - send email
 *
 * @Date 2021/8/26 3:25 下午
 * @Created by lizhenzhen
 */
@Component("urgencyNotification")
public class UrgencyNotification extends Notification{

    @Autowired
    public UrgencyNotification(@Qualifier("emailMsgSender") MsgSender msgSender) {
        super(msgSender);
    }

    @Override
    public void notify(String msg) {
        this.msgSender.send("【Urgency alert】" + msg);
    }
}
