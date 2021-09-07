package com.coderandyli.dynamic.thread.pool.monitor.alert.sender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Date 2021/8/26 3:27 下午
 * @Created by lizhenzhen
 */
@Slf4j
@Component
public class EmailMsgSender implements MsgSender {
    private final List<String> emails;

    public EmailMsgSender(List<String> emails) {
        this.emails = emails;
    }

    @Override
    public void send(String msg) {
        log.debug("send msg to specified email, the msg is 【{}】", msg);
    }
}
