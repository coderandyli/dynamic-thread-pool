package com.coderandyli.dynamic.thread.pool.monitor.reporter;

import com.coderandyli.dynamic.thread.pool.client.ThreadPoolDynamicInfo;
import com.coderandyli.dynamic.thread.pool.client.ThreadTaskInfo;
import com.coderandyli.dynamic.thread.pool.monitor.Aggregator;
import com.coderandyli.dynamic.thread.pool.monitor.TaskStat;
import com.coderandyli.dynamic.thread.pool.monitor.alert.Alert;
import com.coderandyli.dynamic.thread.pool.monitor.alert.AlertRule;
import com.coderandyli.dynamic.thread.pool.monitor.alert.StatInfo;
import com.coderandyli.dynamic.thread.pool.monitor.alert.handler.ActiveAlertHandler;
import com.coderandyli.dynamic.thread.pool.monitor.alert.handler.AlertHandler;
import com.coderandyli.dynamic.thread.pool.monitor.alert.handler.TaskRejectAlertHandler;
import com.coderandyli.dynamic.thread.pool.monitor.alert.notification.NormalNotification;
import com.coderandyli.dynamic.thread.pool.monitor.alert.sender.ConsoleMsgSender;
import com.coderandyli.dynamic.thread.pool.monitor.alert.sender.MsgSender;
import com.coderandyli.dynamic.thread.pool.monitor.metrics.storage.MetricsStorage;
import com.coderandyli.dynamic.thread.pool.monitor.view.StatViewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * (what) the class is abstract class,
 * <p>
 * (why) the common code is extracted in this class
 * <p>
 * (how)
 */
@Component
public abstract class ScheduleReporter {
    private static final long MAX_STAT_DURATION_IN_MILLIS = 10 * 60 * 1000; // 10minutes
    protected Aggregator aggregator;

    private final Alert alert;

    @Autowired
    @Qualifier("mysqlMetricsStorage")
    protected MetricsStorage metricsStorage;

    @Autowired
    protected StatViewer viewer;

    public ScheduleReporter() {
        this.aggregator = new Aggregator();

        // TODO: 2021/9/2 预警相关配置暂时先放这里，暂未想好预警消息触发逻辑。
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
        triggerAlert(stats);
    }

    /**
     * 触发预警逻辑
     */
    private void triggerAlert(Map<String, TaskStat> stats) {
        if (CollectionUtils.isEmpty(stats)) return;

        for (Map.Entry<String, TaskStat> entry : stats.entrySet()) {
            String tpId = entry.getKey();
            TaskStat value = entry.getValue();
            ThreadPoolDynamicInfo lastThreadPoolInfo = metricsStorage.queryLastThreadPoolInfoByTpId(tpId);

            StatInfo statInfo = StatInfo.builder()
                    .threadPoolId(lastThreadPoolInfo.getTpId())
                    .activeCount(lastThreadPoolInfo.getActiveCount())
                    .maximumPoolSize(lastThreadPoolInfo.getMaximumPoolSize())
                    .taskCount(lastThreadPoolInfo.getTaskCount())
                    .completedTaskCount(lastThreadPoolInfo.getCompletedTaskCount())
                    .rejectCount(lastThreadPoolInfo.getRejectCount())
                    // .queueCapacity()
                    // .queueCurrentSize()
                    // .queueRemainingCapacity()
                    .maxExecuteTime(value.getMaxExecuteTime())
                    .avgExecuteTime(value.getMaxExecuteTime())
                    .p99ExecuteTime(value.getP99ExecuteTime())
                    .p999ExecuteTime(value.getP999ExecuteTime())
                    .tps(value.getTps())
                    // .durationInSeconds()
                    .build();
            alert.check(statInfo);
        }
    }
    

    /**
     * todo 防止一次性采集的数据过大，导致内存溢出 分批次采集数据
     *
     * @param startTimeInMillis
     * @param endTimeInMillis
     * @return
     */
    private Map<String, TaskStat> doStat(long startTimeInMillis, long endTimeInMillis) {
        Map<String, List<ThreadTaskInfo>> requestInfos = metricsStorage.queryAllTaskInfosByDuration(startTimeInMillis, endTimeInMillis);
        if (CollectionUtils.isEmpty(requestInfos)) return Collections.emptyMap();

        return aggregator.aggregate(
                requestInfos, endTimeInMillis - startTimeInMillis);
    }
}