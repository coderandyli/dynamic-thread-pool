package com.coderandyli.dtp.monitor.alert.sender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @Date 2021/8/26 3:27 下午
 * @Created by lizhenzhen
 */
@Slf4j
@Primary
@Component("consoleMsgSender")
public class ConsoleMsgSender implements MsgSender {
    @Override
    public void send(String msg) {
        log.warn(msg);
    }
}
