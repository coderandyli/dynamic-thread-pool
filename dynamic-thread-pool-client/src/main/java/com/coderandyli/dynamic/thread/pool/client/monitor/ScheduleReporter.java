package com.coderandyli.dynamic.thread.pool.client.monitor;


import com.coderandyli.dynamic.thread.pool.client.monitor.*;

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
 *
 */
public abstract class ScheduleReporter {
    private static final long MAX_STAT_DURATION_IN_MILLIS = 10 * 60 * 1000; // 10minutes

    protected MetricsStorage metricsStorage;
    protected Aggregator aggregator;
    protected StatViewer viewer;

    public ScheduleReporter(MetricsStorage metricsStorage, Aggregator aggregator, StatViewer viewer) {
        this.metricsStorage = metricsStorage;
        this.aggregator = aggregator;
        this.viewer = viewer;
    }

    protected void doStatAndReport(long startTimeInMillis, long endTimeInMillis) {
        Map<String, RequestStat> stats = doStat(startTimeInMillis, endTimeInMillis);
        viewer.output(stats, startTimeInMillis, endTimeInMillis);
    }

    /**
     * 防止一次性采集的数据过大，分批次采集数据
     * @param startTimeInMillis
     * @param endTimeInMillis
     * @return
     */
    private Map<String, RequestStat> doStat(long startTimeInMillis, long endTimeInMillis) {
        Map<String, List<RequestStat>> segmentStats = new HashMap<>();
        long segmentStartTimeMillis = startTimeInMillis;
        while (segmentStartTimeMillis < endTimeInMillis) {
            long segmentEndTimeMillis = segmentStartTimeMillis + MAX_STAT_DURATION_IN_MILLIS;
            if (segmentEndTimeMillis > endTimeInMillis) {
                segmentEndTimeMillis = endTimeInMillis;
            }
            Map<String, List<RequestInfo>> requestInfos =
                    metricsStorage.getAllRequestInfosByDuration(segmentStartTimeMillis, segmentEndTimeMillis);
            if (requestInfos == null || requestInfos.isEmpty()) {
                continue;
            }
            Map<String, RequestStat> segmentStat = aggregator.aggregate(
                    requestInfos, segmentEndTimeMillis - segmentStartTimeMillis);
            addStat(segmentStats, segmentStat);
            segmentStartTimeMillis += MAX_STAT_DURATION_IN_MILLIS;
        }

        long durationInMillis = endTimeInMillis - startTimeInMillis;
        Map<String, RequestStat> aggregatedStats = aggregateStats(segmentStats, durationInMillis);
        return aggregatedStats;
    }

    private void addStat(Map<String, List<RequestStat>> segmentStats,
                         Map<String, RequestStat> segmentStat) {
        for (Map.Entry<String, RequestStat> entry : segmentStat.entrySet()) {
            String apiName = entry.getKey();
            RequestStat stat = entry.getValue();
            List<RequestStat> statList = segmentStats.putIfAbsent(apiName, new ArrayList<>());
            statList.add(stat);
        }
    }

    private Map<String, RequestStat> aggregateStats(Map<String, List<RequestStat>> segmentStats,
                                                    long durationInMillis) {
        Map<String, RequestStat> aggregatedStats = new HashMap<>();
        for (Map.Entry<String, List<RequestStat>> entry : segmentStats.entrySet()) {
            String apiName = entry.getKey();
            List<RequestStat> apiStats = entry.getValue();
            double maxRespTime = Double.MIN_VALUE;
            double minRespTime = Double.MAX_VALUE;
            long count = 0;
            double sumRespTime = 0;
            for (RequestStat stat : apiStats) {
                if (stat.getMaxResponseTime() > maxRespTime) maxRespTime = stat.getMaxResponseTime();
                if (stat.getMinResponseTime() < minRespTime) minRespTime = stat.getMinResponseTime();
                count += stat.getCount();
                sumRespTime += (stat.getCount() * stat.getAvgResponseTime());
            }
            RequestStat aggregatedStat = new RequestStat();
            aggregatedStat.setMaxResponseTime(maxRespTime);
            aggregatedStat.setMinResponseTime(minRespTime);
            aggregatedStat.setAvgResponseTime(sumRespTime / count);
            aggregatedStat.setCount(count);
            aggregatedStat.setTps(count / durationInMillis * 1000);
            aggregatedStats.put(apiName, aggregatedStat);
        }
        return aggregatedStats;
    }
}