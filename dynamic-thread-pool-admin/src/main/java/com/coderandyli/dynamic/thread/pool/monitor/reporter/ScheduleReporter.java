package com.coderandyli.dynamic.thread.pool.monitor.reporter;

import com.coderandyli.dynamic.thread.pool.monitor.Aggregator;
import com.coderandyli.dynamic.thread.pool.monitor.TaskStat;
import com.coderandyli.dynamic.thread.pool.client.ThreadTaskInfo;
import com.coderandyli.dynamic.thread.pool.monitor.alert.Alert;
import com.coderandyli.dynamic.thread.pool.monitor.alert.AlertRule;
import com.coderandyli.dynamic.thread.pool.monitor.alert.StatInfo;
import com.coderandyli.dynamic.thread.pool.monitor.alert.handler.ActiveAlertHandler;
import com.coderandyli.dynamic.thread.pool.monitor.alert.handler.AlertHandler;
import com.coderandyli.dynamic.thread.pool.monitor.alert.handler.TaskRejectAlertHandler;
import com.coderandyli.dynamic.thread.pool.monitor.alert.notification.NormalNotification;
import com.coderandyli.dynamic.thread.pool.monitor.alert.sender.ConsoleMsgSender;
import com.coderandyli.dynamic.thread.pool.monitor.alert.sender.MsgSender;
import com.coderandyli.dynamic.thread.pool.monitor.storage.MetricsStorage;
import com.coderandyli.dynamic.thread.pool.monitor.view.StatViewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * (what) the class is abstract class,
 *
 * (why) the common code is extracted in this class
 *
 * (how)
 */
@Component
public abstract class ScheduleReporter {
    private static final long MAX_STAT_DURATION_IN_MILLIS = 10 * 60 * 1000; // 10minutes
    protected Aggregator aggregator;

    private Alert alert;

    @Autowired
    protected MetricsStorage metricsStorage;

    @Autowired
    protected StatViewer viewer;

    public ScheduleReporter() {
        this.aggregator = new Aggregator();

        // 预警相关配置
        MsgSender msgSender = new ConsoleMsgSender();
        NormalNotification normalNotification = new NormalNotification(msgSender);
        AlertRule alertRule = new AlertRule();
        AlertHandler activeAlertHandler = new ActiveAlertHandler(normalNotification, alertRule);
        AlertHandler taskRejectAlertHandler = new TaskRejectAlertHandler(normalNotification, alertRule);

        alert = new Alert();
        alert.addAlertHandler(activeAlertHandler);
        alert.addAlertHandler(taskRejectAlertHandler);
    }

    protected void doStatAndReport(long startTimeInMillis, long endTimeInMillis) {
        Map<String, TaskStat> stats = doStat(startTimeInMillis, endTimeInMillis);
        viewer.output(stats, startTimeInMillis, endTimeInMillis);

        // 触发预警逻辑
        // TODO: 2021/8/26 完善
        StatInfo statInfo = StatInfo.builder()
                .threadPoolId("test-application:thread-pool")
                .activeCount(10)
                .maximumPoolSize(15)
                .rejectCount(2)
                .build();
        alert.check(statInfo);
    }

    /**
     * todo 防止一次性采集的数据过大，导致内存溢出 分批次采集数据
     * @param startTimeInMillis
     * @param endTimeInMillis
     * @return
     */
    private Map<String, TaskStat> doStat(long startTimeInMillis, long endTimeInMillis) {
        Map<String, List<ThreadTaskInfo>> requestInfos =
                metricsStorage.queryAllTaskInfosByDuration(startTimeInMillis, endTimeInMillis);
        if (CollectionUtils.isEmpty(requestInfos)) return Collections.emptyMap();

        return aggregator.aggregate(
                requestInfos, endTimeInMillis - startTimeInMillis);
    }
}