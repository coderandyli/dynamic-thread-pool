package com.coderandyli.dtp.monitor.alert;

import com.coderandyli.dtp.core.ThreadPoolDynamicInfo;
import com.coderandyli.dtp.monitor.TaskStat;
import com.coderandyli.dtp.monitor.alert.handler.AlertHandler;
import com.coderandyli.dtp.monitor.metrics.storage.MetricsStorage;
import com.coderandyli.dtp.monitor.reporter.ScheduleReporter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Alert 逻辑开始执行
 *
 * @Date 2021/9/2 2:44 下午
 * @Created by lizhenzhen
 */
@Slf4j
@Component
public class AlertContext implements InitializingBean {
    private final ScheduledExecutorService executor;
    private final Alert alert;
    private final ScheduleReporter scheduleReporter;
    private final MetricsStorage metricsStorage;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    public AlertContext(ScheduleReporter scheduleReporter,
                        @Qualifier("mysqlMetricsStorage") MetricsStorage metricsStorage,
                        Alert alert) {
        this.executor = Executors.newSingleThreadScheduledExecutor();
        this.scheduleReporter = scheduleReporter;
        this.metricsStorage = metricsStorage;
        this.alert = alert;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 每10秒查询60秒内的任务执行记录
        startRepeatedAlertCheck(10, 60);

        // add alertHandler
        Map<String, AlertHandler> alertHandlerMap = applicationContext.getBeansOfType(AlertHandler.class);
        List<AlertHandler> handlers = new ArrayList(alertHandlerMap.values());
        handlers.forEach(this.alert::addAlertHandler);
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
