package com.coderandyli.dynamic.thread.pool.client.monitor;

import java.util.List;
import java.util.Map;

/**
 * @author lizhen
 * @version 1.0
 * @date 2020/1/3 上午9:50
 *
 * 原始数据存储
 */
public interface MetricsStorage {
    /**
     * 保存请求信息
     * @param requestInfo
     */
    void saveRequestInfo(RequestInfo requestInfo);
    /**
     * 获取指定时间区间内的【apiName】请求信息列表
     * 『提示』：时间区间过大，可能造成OOM或者频繁Full GC
     * @param apiName
     * @param startTime
     * @param endTime
     * @return
     */
    List<RequestInfo> getRequestInfosByDuration(String apiName, long startTime, long endTime);
    /**
     * 获取指定时间区间内的所有的所有请求
     * 『提示』：时间区间过大，可能造成OOM或者频繁Full GC
     * @param startTime
     * @param endTime
     * @return
     */
    Map<String, List<RequestInfo>> getAllRequestInfosByDuration(long startTime, long endTime);

}
