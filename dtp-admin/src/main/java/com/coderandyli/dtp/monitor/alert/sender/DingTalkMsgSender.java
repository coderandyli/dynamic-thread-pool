package com.coderandyli.dtp.monitor.alert.sender;

import com.coderandyli.dtp.core.utils.OkHttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * send msg to DingTalk
 *
 * <h3> Reference <h3/>
 * - https://developers.dingtalk.com/document/robots/custom-robot-access?spm=ding_open_doc.document.0.0.62846573Urnl0G#topic-2026027
 *
 * @Date 2021/8/26 3:27 下午
 * @Created by lizhenzhen
 */
@Slf4j
@Component("dingTalkMsgSender")
public class DingTalkMsgSender implements MsgSender {

    @Override
    public void send(String msg) {
        if (log.isDebugEnabled()) {
            log.debug("send msg to DingTalk, the msg is 【{}】", msg);
        }
        String url = "https://oapi.dingtalk.com/robot/send?access_token=f27e5352655badd67c9b19544d9292860d6a4ef3e419b04803f6bb8b0cf6faa7";
        String body = "{\"msgtype\": \"text\",\"text\": {\"content\": \n" +
                "\"" + "【报警】" + msg + "\"" +
                "}}";
        try {
            OkHttpUtils.post(url, body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
