package com.coderandyli.dtp.monitor.alert.sender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Date 2021/8/26 3:27 δΈε
 * @Created by lizhenzhen
 */
@Slf4j
@Component("emailMsgSender")
public class EmailMsgSender implements MsgSender {
    private List<String> emails;

    public EmailMsgSender(List<String> emails) {
        this.emails = emails;
    }

    public EmailMsgSender() {
    }

    @Override
    public void send(String msg) {
        log.debug("send msg to specified email, the msg is γ{}γ", msg);
    }
}
