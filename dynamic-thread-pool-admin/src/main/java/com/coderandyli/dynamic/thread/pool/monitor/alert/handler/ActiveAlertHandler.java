package com.coderandyli.dynamic.thread.pool.monitor.alert.handler;

import com.coderandyli.dynamic.thread.pool.monitor.alert.AlertRule;
import com.coderandyli.dynamic.thread.pool.monitor.alert.StatInfo;
import com.coderandyli.dynamic.thread.pool.monitor.alert.notification.Notification;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * 线程池活跃度警告处理器
 *
 * 线程池活跃度 = activeCount / maximumPoolSize
 *
 * @Date 2021/8/26 2:23 下午
 * @Created by lizhenzhen
 */
@Slf4j
public class ActiveAlertHandler extends AlertHandler {


    public ActiveAlertHandler(Notification notification, AlertRule alertRule) {
        super(notification, alertRule);
    }

    @Override
    public void check(StatInfo statInfo) {
        log.debug("【线程池活跃度警告处理器】即将开始执行...");

        int activeCount = statInfo.getActiveCount();
        int maximumPoolSize = statInfo.getMaximumPoolSize();

        double active = (activeCount * 1.0) / maximumPoolSize;
        active = new BigDecimal(active).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        double maxActive = this.alertRule.getMaxActive();
        if (maxActive <= active) {
            // 达到阈值
            log.debug("【线程池活跃度警告处理器】达到阈值，发出警告. 参数为：【{}】", statInfo);
            String msg = "线程池【" +
                    statInfo.getThreadPoolId() +
                    "】" +
                    "线程活跃度达到阈值, 当期阈值为: 【" +
                    alertRule.getMaxActive() +
                    "】, 实际活跃度为【" +
                    active +
                    "】" +
                    "详细信息为：" +
                    statInfo;
            this.notification.notify(msg);
        }else {
            log.debug("【线程池活跃度警告处理器】未达到阈值，参数为：【{}】", statInfo);
        }
    }
}
