package com.coderandyli.dynamic.thread.pool.monitor.alert.handler;

import com.coderandyli.dynamic.thread.pool.monitor.alert.StatInfo;
import com.coderandyli.dynamic.thread.pool.monitor.alert.notification.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 任务拒绝警告处理器
 *
 * <h3> Notification Level<h3/>
 * SEVERE
 *
 * @Date 2021/8/26 2:23 下午
 * @Created by lizhenzhen
 */
@Slf4j
@Component("taskRejectAlertHandler")
public class TaskRejectAlertHandler extends AlertHandler {

    @Autowired
    public TaskRejectAlertHandler(@Qualifier("severeNotification") Notification notification) {
        super(notification);
    }

    @Override
    public void check(StatInfo statInfo) {
        if (log.isDebugEnabled()) {
            log.debug("【任务拒绝警告处理器】即将开始执行...");
        }

        int rejectCount = statInfo.getRejectCount();
        int maxRejectCount = this.alertRule.getMaxRejectCount();
        if (maxRejectCount <= rejectCount) {
            // 达到阈值
            if (log.isDebugEnabled()) {
                log.debug("【任务拒绝警告处理器】达到阈值，发出警告. 参数为：【{}】", statInfo);
            }
            String msg = "线程池【" +
                    statInfo.getThreadPoolId() +
                    "】" +
                    "任务拒绝次数达到阈值, 当期阈值为: 【" +
                    alertRule.getMaxRejectCount() +
                    "】, 实际任务拒绝次数为【" +
                    statInfo.getRejectCount() +
                    "】" +
                    "详细信息为：" +
                    statInfo;
            this.notification.notify(msg);
        } else {
            this.notification.notify("【线程池活跃度警告处理器】未达到阈值");
            if (log.isDebugEnabled()) {
                log.debug("【线程池活跃度警告处理器】未达到阈值，参数为：【{}】", statInfo);
            }
        }
    }
}
