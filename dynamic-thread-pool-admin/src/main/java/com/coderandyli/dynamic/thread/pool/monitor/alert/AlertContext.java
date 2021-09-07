package com.coderandyli.dynamic.thread.pool.monitor.alert;

import com.coderandyli.dynamic.thread.pool.core.ThreadPoolDynamicInfo;
import com.coderandyli.dynamic.thread.pool.monitor.TaskStat;
import com.coderandyli.dynamic.thread.pool.monitor.alert.handler.ActiveAlertHandler;
import com.coderandyli.dynamic.thread.pool.monitor.alert.handler.AlertHandler;
import com.coderandyli.dynamic.thread.pool.monitor.alert.handler.TaskRejectAlertHandler;
import com.coderandyli.dynamic.thread.pool.monitor.alert.notification.NormalNotification;
import com.coderandyli.dynamic.thread.pool.monitor.alert.sender.ConsoleMsgSender;
import com.coderandyli.dynamic.thread.pool.monitor.alert.sender.MsgSender;
import com.coderandyli.dynamic.thread.pool.monitor.metrics.storage.MetricsStorage;
import com.coderandyli.dynamic.thread.pool.monitor.reporter.ScheduleReporter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Date 2021/9/2 2:44 下午
 * @Created by lizhenzhen
 */
@Component
public class AlertContext implements InitializingBean {
    private final ScheduledExecutorService executor;
    private final Alert alert;
    private final ScheduleReporter scheduleReporter;
    protected final MetricsStorage metricsStorage;

    @Autowired
    public AlertContext(ScheduleReporter scheduleReporter, @Qualifier("mysqlMetricsStorage") MetricsStorage metricsStorage) {
        this.executor = Executors.newSingleThreadScheduledExecutor();
        this.scheduleReporter = scheduleReporter;
        this.metricsStorage = metricsStorage;

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


    @Override
    public void afterPropertiesSet() throws Exception {
        // 每10秒查询60秒内的任务执行记录
        startRepeatedAlertCheck(10, 60);
    }

    public void startRepeatedAlertCheck(long periodInSeconds, long durationInSeconds) {
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                long durationInMillis = durationInSeconds * 1000;
                long endTimeInMillis = System.currentTimeMillis();
                long startTimeInMillis = endTimeInMillis - durationInMillis;

                Map<String, TaskStat> stats = scheduleReporter.doStat(startTimeInMillis, endTimeInMillis);
                triggerAlert(stats);
            }
        }, 0l, periodInSeconds, TimeUnit.SECONDS);
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
                    .corePoolSize(lastThreadPoolInfo.getCorePoolSize())
                    .maximumPoolSize(lastThreadPoolInfo.getMaximumPoolSize())
                    .activeCount(lastThreadPoolInfo.getActiveCount())
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

}
