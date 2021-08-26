package com.coderandyli.dynamic.thread.pool.client.monitor.alert.handler;

import com.coderandyli.dynamic.thread.pool.client.monitor.alert.AlertRule;
import com.coderandyli.dynamic.thread.pool.client.monitor.alert.StatInfo;
import com.coderandyli.dynamic.thread.pool.client.monitor.alert.notification.NormalNotification;
import com.coderandyli.dynamic.thread.pool.client.monitor.alert.notification.Notification;
import com.coderandyli.dynamic.thread.pool.client.monitor.alert.sender.ConsoleMsgSender;
import com.coderandyli.dynamic.thread.pool.client.monitor.alert.sender.MsgSender;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Date 2021/8/26 4:00 下午
 * @Created by lizhenzhen
 */
public class ActiveAlertHandlerTest {

    private ActiveAlertHandler activeAlertHandler;

    @Before
    public void before() {
        MsgSender msgSender = new ConsoleMsgSender();
        NormalNotification normalNotification = new NormalNotification(msgSender);
        AlertRule alertRule = new AlertRule();

        activeAlertHandler = new ActiveAlertHandler(normalNotification, alertRule);
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