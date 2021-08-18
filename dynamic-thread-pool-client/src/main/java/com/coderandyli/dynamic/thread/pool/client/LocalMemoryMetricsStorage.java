package com.coderandyli.dynamic.thread.pool.client;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Store raw data in local memory
 *
 * @Date 2021/8/18 10:06 上午
 * @Created by lizhenzhen
 */
@Repository("localMemoryMetricsStorage")
public class LocalMemoryMetricsStorage implements MetricsStorage{
    /**
     * 原始任务数据集合
     */
    private static final List<ThreadTaskInfo> taskInfos = new ArrayList<>();

    @Override
    public void saveTaskInfo(ThreadTaskInfo taskInfo) {
            taskInfos.add(taskInfo);
    }

    @Override
    public List<ThreadTaskInfo> queryTaskInfosByDuration(String taskName, long startTime, long endTime) {

        Map<String, List<ThreadTaskInfo>> resultMap = queryAllTaskInfosByDuration(startTime, endTime);
        if (resultMap == null) return Collections.emptyList();

        List<ThreadTaskInfo> taskInfos = resultMap.get(taskName);
        if (CollectionUtils.isEmpty(taskInfos)) return Collections.emptyList();
        return taskInfos;
    }

    @Override
    public Map<String, List<ThreadTaskInfo>> queryAllTaskInfosByDuration(long startTime, long endTime) {
        if (CollectionUtils.isEmpty(taskInfos)) return Collections.emptyMap();
        Map<String, List<ThreadTaskInfo>> resultMap = new HashMap<>();
        for (ThreadTaskInfo taskInfo : taskInfos) {
            long timestamp = taskInfo.getTimestamp();
            if ((startTime <= timestamp && timestamp <= endTime) || (startTime == 0 && endTime == 0)){
                List<ThreadTaskInfo> taskInfoSubList = resultMap.computeIfAbsent(taskInfo.getTaskName(), k -> new ArrayList<>());
                taskInfoSubList.add(taskInfo);
            }
        }
        return resultMap;
    }
}
