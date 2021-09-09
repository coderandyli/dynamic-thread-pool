package com.coderandyli.dtp.monitor.alert.handler;

import com.coderandyli.dtp.monitor.alert.StatInfo;
import com.coderandyli.dtp.monitor.alert.notification.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 线程池活跃度警告处理器
 * <p>
 * 线程池活跃度 = activeCount / maximumPoolSize
 *
 * <h3> Notification level<h3/>
 * URGENCY
 *
 * @Date 2021/8/26 2:23 下午
 * @Created by lizhenzhen
 */
@Slf4j
@Component("activeAlertHandler")
public class ActiveAlertHandler extends AlertHandler {

    @Autowired
    public ActiveAlertHandler(@Qualifier("urgencyNotification") Notification notification) {
        super(notification);
    }

    @Override
    public void check(StatInfo statInfo) {
        if (log.isDebugEnabled()) {
            log.debug("【线程池活跃度警告处理器】即将开始执行...");
        }

        int activeCount = statInfo.getActiveCount();
        int maximumPoolSize = statInfo.getMaximumPoolSize();

        double active = (activeCount * 1.0) / maximumPoolSize;
        active = new BigDecimal(active).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        double maxActive = this.alertRule.getMaxActive();
        if (maxActive <= active) { // 达到阈值
            if (log.isDebugEnabled()) {
                log.debug("【线程池活跃度警告处理器】达到阈值，发出警告. 参数为：【{}】", statInfo);
            }
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
        } else {
            if (log.isDebugEnabled()) {
                log.debug("【线程池活跃度警告处理器】未达到阈值，参数为：【{}】", statInfo);
            }
        }
    }
}
