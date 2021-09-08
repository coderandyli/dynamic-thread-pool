package com.coderandyli.dynamic.thread.pool.monitor.alert.sender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * send msg to DingTalk
 *
 * @Date 2021/8/26 3:27 下午
 * @Created by lizhenzhen
 */
@Slf4j
@Component("dingTalkMsgSender")
public class DingTalkMsgSender implements MsgSender {

    @Override
    public void send(String msg) {
        log.debug("send msg to DingTalk, the msg is 【{}】", msg);
    }
}
