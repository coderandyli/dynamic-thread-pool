package com.coderandyli.dynamic.thread.pool.monitor.alert.handler;

import com.coderandyli.dynamic.thread.pool.monitor.alert.AlertRule;
import com.coderandyli.dynamic.thread.pool.monitor.alert.StatInfo;
import com.coderandyli.dynamic.thread.pool.monitor.alert.notification.NormalNotification;
import com.coderandyli.dynamic.thread.pool.monitor.alert.sender.ConsoleMsgSender;
import com.coderandyli.dynamic.thread.pool.monitor.alert.sender.MsgSender;
import org.junit.Before;
import org.junit.Test;

/**
 * @Date 2021/9/2 1:41 下午
 * @Created by lizhenzhen
 */
public class TaskRejectAlertHandlerTest {
    private TaskRejectAlertHandler taskRejectAlertHandler;

    @Before
    public void before() {
        MsgSender msgSender = new ConsoleMsgSender();
        NormalNotification normalNotification = new NormalNotification(msgSender);
        AlertRule alertRule = new AlertRule();
        taskRejectAlertHandler = new TaskRejectAlertHandler(normalNotification);
    }

    @Test
    public void check() {
        // 未达阈值
        StatInfo statInfo1 = StatInfo.builder()
                .threadPoolId("test-application:thread-pool")
                .rejectCount(2)
                .build();
        // 已达阈值
        StatInfo statInfo2 = StatInfo.builder()
                .threadPoolId("test-application:thread-pool")
                .rejectCount(20)
                .build();
        taskRejectAlertHandler.check(statInfo1);
    }
}