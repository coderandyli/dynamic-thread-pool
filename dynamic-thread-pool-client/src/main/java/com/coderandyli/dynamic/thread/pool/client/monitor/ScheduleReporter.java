package com.coderandyli.dynamic.thread.pool.client.monitor;

import com.coderandyli.dynamic.thread.pool.client.monitor.metrics.MetricsStorage;
import com.coderandyli.dynamic.thread.pool.client.monitor.view.StatViewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    protected MetricsStorage metricsStorage;

    @Autowired
    protected StatViewer viewer;

    public ScheduleReporter() {
        this.aggregator = new Aggregator();
    }

    protected void doStatAndReport(long startTimeInMillis, long endTimeInMillis) {
        Map<String, TaskStat> stats = doStat(startTimeInMillis, endTimeInMillis);
        viewer.output(stats, startTimeInMillis, endTimeInMillis);
    }

    /**
     * 防止一次性采集的数据过大，分批次采集数据
     * @param startTimeInMillis
     * @param endTimeInMillis
     * @return
     */
    private Map<String, TaskStat> doStat(long startTimeInMillis, long endTimeInMillis) {
        Map<String, List<TaskStat>> segmentStats = new HashMap<>();
        long segmentStartTimeMillis = startTimeInMillis;
        while (segmentStartTimeMillis < endTimeInMillis) {
            long segmentEndTimeMillis = segmentStartTimeMillis + MAX_STAT_DURATION_IN_MILLIS;
            if (segmentEndTimeMillis > endTimeInMillis) {
                segmentEndTimeMillis = endTimeInMillis;
            }
            Map<String, List<ThreadTaskInfo>> requestInfos =
                    metricsStorage.queryAllTaskInfosByDuration(segmentStartTimeMillis, segmentEndTimeMillis);
            if (requestInfos == null || requestInfos.isEmpty()) {
                continue;
            }
            Map<String, TaskStat> segmentStat = aggregator.aggregate(
                    requestInfos, segmentEndTimeMillis - segmentStartTimeMillis);
            addStat(segmentStats, segmentStat);
            segmentStartTimeMillis += MAX_STAT_DURATION_IN_MILLIS;
        }

        long durationInMillis = endTimeInMillis - startTimeInMillis;
        return aggregateStats(segmentStats, durationInMillis);
    }

    private void addStat(Map<String, List<TaskStat>> segmentStats,
                         Map<String, TaskStat> segmentStat) {
        for (Map.Entry<String, TaskStat> entry : segmentStat.entrySet()) {
            String apiName = entry.getKey();
            TaskStat stat = entry.getValue();

            List<TaskStat> statList = segmentStats.get(apiName);
            if (statList == null){
                statList = new ArrayList<>();
            }
            statList.add(stat);
            segmentStats.put(apiName, statList);
            // List<RequestStat> statList = segmentStats.putIfAbsent(apiName, new ArrayList<>());
        }
    }

    private Map<String, TaskStat> aggregateStats(Map<String, List<TaskStat>> segmentStats,
                                                    long durationInMillis) {
        Map<String, TaskStat> aggregatedStats = new HashMap<>();
        for (Map.Entry<String, List<TaskStat>> entry : segmentStats.entrySet()) {
            String apiName = entry.getKey();
            List<TaskStat> apiStats = entry.getValue();
            double maxRespTime = Double.MIN_VALUE;
            double minRespTime = Double.MAX_VALUE;
            long count = 0;
            double sumRespTime = 0;
            for (TaskStat stat : apiStats) {
                if (stat.getMaxExecuteTime() > maxRespTime) maxRespTime = stat.getMaxExecuteTime();
                if (stat.getMinExecuteTime() < minRespTime) minRespTime = stat.getMinExecuteTime();
                count += stat.getCount();
                sumRespTime += (stat.getCount() * stat.getAvgExecuteTime());
            }
            TaskStat aggregatedStat = new TaskStat();
            aggregatedStat.setMaxExecuteTime(maxRespTime);
            aggregatedStat.setMinExecuteTime(minRespTime);
            aggregatedStat.setAvgExecuteTime(sumRespTime / count);
            aggregatedStat.setCount(count);
            // aggregatedStat.setTps(count / durationInMillis * 1000);
            aggregatedStats.put(apiName, aggregatedStat);
        }
        return aggregatedStats;
    }
}