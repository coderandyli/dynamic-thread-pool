package com.coderandyli.dynamic.thread.pool.client.monitor.alert.sender;

import lombok.extern.slf4j.Slf4j;

/**
 * @Date 2021/8/26 3:27 下午
 * @Created by lizhenzhen
 */
@Slf4j
public class ConsoleMsgSender implements MsgSender {
    @Override
    public void send(String msg) {
        log.warn(msg);
    }
}
