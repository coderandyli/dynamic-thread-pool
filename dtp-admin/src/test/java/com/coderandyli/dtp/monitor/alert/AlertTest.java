package com.coderandyli.dtp.monitor.alert;

import com.coderandyli.dtp.monitor.alert.handler.ActiveAlertHandler;
import com.coderandyli.dtp.monitor.alert.handler.AlertHandler;
import com.coderandyli.dtp.monitor.alert.handler.TaskRejectAlertHandler;
import com.coderandyli.dtp.monitor.alert.notification.NormalNotification;
import com.coderandyli.dtp.monitor.alert.sender.ConsoleMsgSender;
import com.coderandyli.dtp.monitor.alert.sender.MsgSender;
import org.junit.Before;
import org.junit.Test;

/**
 * @Date 2021/9/2 1:42 下午
 * @Created by lizhenzhen
 */
public class AlertTest {

    private Alert alert;

    @Before
    public void before() {
        MsgSender msgSender = new ConsoleMsgSender();
        NormalNotification normalNotification = new NormalNotification(msgSender);
        AlertRule alertRule = new AlertRule();
        AlertHandler activeAlertHandler = new ActiveAlertHandler(normalNotification);
        AlertHandler taskRejectAlertHandler = new TaskRejectAlertHandler(normalNotification);

        alert = new Alert();
        alert.addAlertHandler(activeAlertHandler);
        alert.addAlertHandler(taskRejectAlertHandler);
    }

    @Test
    public void check() {
        // 未达阈值
        StatInfo statInfo1 = StatInfo.builder()
                .threadPoolId("test-application:thread-pool")
                .activeCount(10)
                .maximumPoolSize(15)
                .rejectCount(2)
                .build();
        // 已达阈值
        StatInfo statInfo2 = StatInfo.builder()
                .threadPoolId("test-application:thread-pool")
                .activeCount(14)
                .maximumPoolSize(15)
                .rejectCount(20)
                .build();
        alert.check(statInfo2);
    }

}