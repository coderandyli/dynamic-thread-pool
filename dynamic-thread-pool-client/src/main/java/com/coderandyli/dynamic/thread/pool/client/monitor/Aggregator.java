package com.coderandyli.dynamic.thread.pool.client.monitor;

import com.coderandyli.dynamic.thread.pool.client.reference.RequestInfo;
import com.coderandyli.dynamic.thread.pool.client.reference.RequestStat;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.DoubleStream;

/**
 * 根据原始数据进行聚合计算
 *
 * @author lizhen
 * @version 1.0
 * @date 2020/1/3 上午9:52
 */
public class Aggregator {

    public Map<String, RequestStat> aggregate(Map<String, List<ThreadTaskInfo>> requestInfos, long durationInMillis) {
        Map<String, RequestStat> requestStats = new HashMap<>();
        for (Map.Entry<String, List<ThreadTaskInfo>> entry : requestInfos.entrySet()) {
            String apiName = entry.getKey();
            List<ThreadTaskInfo> requestInfosPerApi = entry.getValue();
            RequestStat requestStat = doAggregate(requestInfosPerApi, durationInMillis);
            requestStats.put(apiName, requestStat);
        }
        return requestStats;
    }

    @VisibleForTesting
    protected RequestStat doAggregate(List<ThreadTaskInfo> taskInfos, long durationInMillis) {
        List<Double> respTimes = new ArrayList<>();
        for (ThreadTaskInfo requestInfo : taskInfos) {
            double respTime = requestInfo.getResponseTime();
            respTimes.add(respTime);
        }

        RequestStat requestStat = new RequestStat();
        requestStat.setMaxResponseTime(max(respTimes));
        requestStat.setMinResponseTime(min(respTimes));
        requestStat.setAvgResponseTime(avg(respTimes));
        requestStat.setP999ResponseTime(percentile999(respTimes));
        requestStat.setP99ResponseTime(percentile99(respTimes));
        requestStat.setCount(respTimes.size());
        requestStat.setTps((int) tps(respTimes.size(), durationInMillis / 1000));
        return requestStat;
    }

    private double max(List<Double> dataset) {
        if (CollectionUtils.isEmpty(dataset)) return 0d;
        return Collections.max(dataset);
    }

    private double min(List<Double> dataset) {
        if (CollectionUtils.isEmpty(dataset)) return 0d;
        return Collections.min(dataset);
    }

    private double avg(List<Double> dataset) {
        if (CollectionUtils.isEmpty(dataset)) return 0d;
        return dataset.stream().mapToDouble(i -> i).average().orElse(Double.NaN);
    }

    private double tps(int count, double duration) {
        if (count == 0 || duration == 0) return 0;
        return count / duration;
    }

    private double percentile999(List<Double> dataset) {
        return percentile(dataset, 0.999);
    }

    private double percentile99(List<Double> dataset) {
        return percentile(dataset, 0.99);
    }

    private double percentile(List<Double> dataset, double ratio) {
        if (CollectionUtils.isEmpty(dataset)) return 0d;

        // 从小到排序
        Collections.sort(dataset);
        return dataset.get((int) Math.ceil(dataset.size() * ratio) - 1);
    }

}