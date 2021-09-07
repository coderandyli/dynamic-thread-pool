package com.coderandyli.dynamic.thread.pool.monitor.reporter;

import com.coderandyli.dynamic.thread.pool.core.ThreadTaskInfo;
import com.coderandyli.dynamic.thread.pool.monitor.Aggregator;
import com.coderandyli.dynamic.thread.pool.monitor.TaskStat;
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

    @Autowired
    @Qualifier("mysqlMetricsStorage")
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
     * todo 防止一次性采集的数据过大，导致内存溢出 可考虑分批次采集数据
     *
     * @param startTimeInMillis
     * @param endTimeInMillis
     * @return
     */
    public Map<String, TaskStat> doStat(long startTimeInMillis, long endTimeInMillis) {
        Map<String, List<ThreadTaskInfo>> requestInfos = metricsStorage.queryAllTaskInfosByDuration(startTimeInMillis, endTimeInMillis);
        if (CollectionUtils.isEmpty(requestInfos)) return Collections.emptyMap();

        return aggregator.aggregate(
                requestInfos, endTimeInMillis - startTimeInMillis);
    }
}