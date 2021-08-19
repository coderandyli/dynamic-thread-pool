package com.coderandyli.dynamic.thread.pool.client.monitor.metrics;

import com.coderandyli.dynamic.thread.pool.client.monitor.ThreadPoolDynamicInfo;
import com.coderandyli.dynamic.thread.pool.client.monitor.ThreadTaskInfo;

import java.util.List;
import java.util.Map;

/**
 * 原始数据存储
 *
 * @Date 2021/8/18 10:00 上午
 * @Created by lizhenzhen
 */
public interface MetricsStorage {
    /**
     * 保存任务执行信息
     * @param taskInfo
     */
    void saveTaskInfo(ThreadTaskInfo taskInfo);

    /**
     * 保存线程池数据
     * @param threadPoolInfo
     */
    void saveThreadPoolInfo(ThreadPoolDynamicInfo threadPoolInfo);
    /**
     * 获取指定时间区间内的【taskName】信息列表
     * 『提示』：时间区间过大，可能造成OOM或者频繁Full GC
     * @param taskName
     * @param startTime
     * @param endTime
     * @return
     */
    List<ThreadTaskInfo> queryTaskInfosByDuration(String taskName, long startTime, long endTime);
    /**
     * 获取指定时间区间内的所有的所有请求
     * 『提示』：时间区间过大，可能造成OOM或者频繁Full GC
     * @param startTime
     * @param endTime
     * @return
     */
    Map<String, List<ThreadTaskInfo>> queryAllTaskInfosByDuration(long startTime, long endTime);

    /**
     * 获取线程池最新信息
     */
    ThreadPoolDynamicInfo queryLastThreadPoolInfo();

    /**
     * 获取所有线程池最新信息
     */
    List<ThreadPoolDynamicInfo> queryAllThreadPoolInfo();
}
