package com.coderandyli.dtp.monitor.alert.handler;

import com.coderandyli.dtp.monitor.alert.StatInfo;
import com.coderandyli.dtp.monitor.alert.notification.NormalNotification;
import com.coderandyli.dtp.monitor.alert.sender.ConsoleMsgSender;
import com.coderandyli.dtp.monitor.alert.sender.MsgSender;
import org.junit.Before;
import org.junit.Test;

/**
 * @Date 2021/9/2 1:41 下午
 * @Created by lizhenzhen
 */
public class ActiveAlertHandlerTest {
    private ActiveAlertHandler activeAlertHandler;

    @Before
    public void before() {
        MsgSender msgSender = new ConsoleMsgSender();
        NormalNotification normalNotification = new NormalNotification(msgSender);

        activeAlertHandler = new ActiveAlertHandler(normalNotification);
    }

    @Test
    public void check() {
        // 未达阈值
        StatInfo statInfo1 = StatInfo.builder()
                .threadPoolId("test-application:thread-pool")
                .activeCount(10)
                .maximumPoolSize(15)
                .build();
        // 已达阈值
        StatInfo statInfo2 = StatInfo.builder()
                .threadPoolId("test-application:thread-pool")
                .activeCount(14)
                .maximumPoolSize(15)
                .build();
        activeAlertHandler.check(statInfo2);
    }

}