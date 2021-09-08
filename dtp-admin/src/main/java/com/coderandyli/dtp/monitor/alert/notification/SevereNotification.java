package com.coderandyli.dtp.monitor.alert.notification;

import com.coderandyli.dtp.monitor.alert.sender.MsgSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 【严重】通知
 *  - send dingTalk
 *
 * @Date 2021/8/26 3:36 下午
 * @Created by lizhenzhen
 */
@Component("severeNotification")
public class SevereNotification extends Notification{

    @Autowired
    public SevereNotification(@Qualifier("dingTalkMsgSender") MsgSender msgSender) {
        super(msgSender);
    }

    @Override
    public void notify(String msg) {
        this.msgSender.send("【Severe alert】" + msg);
    }
}
