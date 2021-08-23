package com.coderandyli.dynamic.thread.pool.client.reference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lizhen
 * @version 1.0
 * @date 2020/1/3 上午9:52
 * <p>
 * 根据原始数据进行聚合计算
 */
public class Aggregator {

    public Map<String, RequestStat> aggregate(Map<String, List<RequestInfo>> requestInfos, long durationInMillis) {
        Map<String, RequestStat> requestStats = new HashMap<>();
        for (Map.Entry<String, List<RequestInfo>> entry : requestInfos.entrySet()) {
            String apiName = entry.getKey();
            List<RequestInfo> requestInfosPerApi = entry.getValue();
            RequestStat requestStat = doAggregate(requestInfosPerApi, durationInMillis);
            requestStats.put(apiName, requestStat);
        }
        return requestStats;
    }

    private RequestStat doAggregate(List<RequestInfo> requestInfos, long durationInMillis) {
        List<Double> respTimes = new ArrayList<>();
        for (RequestInfo requestInfo : requestInfos) {
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
        // requestStat.setTps((long) tps(respTimes.size(), durationInMillis/1000));
        return requestStat;
    }

    private double max(List<Double> dataset) {
        return 0d;
    }
    private double min(List<Double> dataset) {
        return 0d;
    }
    private double avg(List<Double> dataset) {
        return 0d;
    }
    private double tps(int count, double duration) {
        return 0d;
    }
    private double percentile999(List<Double> dataset) {
        return 0d;
    }
    private double percentile99(List<Double> dataset) {
        return 0d;
    }
    private double percentile(List<Double> dataset, double ratio) {
        return 0d;
    }
}