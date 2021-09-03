package com.coderandyli.dynamic.thread.pool.monitor.metrics.storage;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.coderandyli.dynamic.thread.pool.admin.entity.ThreadPoolDynamicInfoRecord;
import com.coderandyli.dynamic.thread.pool.admin.entity.ThreadTaskExecRecord;
import com.coderandyli.dynamic.thread.pool.admin.service.ThreadPoolDynamicInfoRecordService;
import com.coderandyli.dynamic.thread.pool.admin.service.ThreadTaskExecRecordService;
import com.coderandyli.dynamic.thread.pool.client.ThreadPoolDynamicInfo;
import com.coderandyli.dynamic.thread.pool.client.ThreadTaskInfo;
import com.coderandyli.dynamic.thread.pool.monitor.convert.ThreadPoolDynamicInfoCovert;
import com.coderandyli.dynamic.thread.pool.monitor.convert.ThreadTaskInfoCovert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Date 2021/9/1 5:10 下午
 * @Created by lizhenzhen
 */
@Primary
@Repository("mysqlMetricsStorage")
public class MYSQLMetricsStorage implements MetricsStorage {

    @Autowired
    private ThreadTaskExecRecordService threadTaskExecRecordService;

    @Autowired
    private ThreadPoolDynamicInfoRecordService threadPoolDynamicInfoRecordService;

    @Override
    public void saveTaskInfo(ThreadTaskInfo taskInfo) {
        ThreadTaskExecRecord threadTaskExecRecord = ThreadTaskInfoCovert.threadTaskInfoToThreadTaskExecRecord(taskInfo);
        threadTaskExecRecordService.insert(threadTaskExecRecord);
    }

    @Override
    public void saveThreadPoolInfo(ThreadPoolDynamicInfo threadPoolInfo) {
        ThreadPoolDynamicInfoRecord poolDynamicInfoRecord = ThreadPoolDynamicInfoCovert.threadPoolDynamicInfoToThreadPoolDynamicInfoRecord(threadPoolInfo);
        threadPoolDynamicInfoRecordService.insert(poolDynamicInfoRecord);
    }

    @Override
    public List<ThreadTaskInfo> queryTaskInfosByDuration(String tpId, long startTime, long endTime) {
        List<ThreadTaskExecRecord> threadTaskExecRecords = null;
        if (tpId == null) {
            threadTaskExecRecords = threadTaskExecRecordService.selectList(new EntityWrapper<ThreadTaskExecRecord>()
                    .between("start_time", startTime, endTime));
        } else {
            threadTaskExecRecords = threadTaskExecRecordService.selectList(new EntityWrapper<ThreadTaskExecRecord>()
                    .eq("tp_id", tpId)
                    .between("start_time", startTime, endTime));
        }

        if (CollectionUtils.isEmpty(threadTaskExecRecords)) return Collections.emptyList();

        return threadTaskExecRecords.stream().map(ThreadTaskInfoCovert::threadTaskExecRecordToThreadTaskInfo).collect(Collectors.toList());
    }

    @Override
    public ThreadPoolDynamicInfo queryLastThreadPoolInfoByTpId(String tpId) {
        ThreadPoolDynamicInfoRecord record = threadPoolDynamicInfoRecordService.queryLastOneByTpId(tpId);
        return ThreadPoolDynamicInfoCovert.threadPoolDynamicInfoRecordToThreadPoolDynamicInfo(record);
    }

    @Deprecated
    @Override
    public List<ThreadPoolDynamicInfo> queryAllThreadPoolInfo() {
        return null;
    }

    @Override
    public Map<String, List<ThreadTaskInfo>> queryAllTaskInfosByDuration(long startTime, long endTime) {
        List<ThreadTaskInfo> taskInfos = this.queryTaskInfosByDuration(null, startTime, endTime);
        if (CollectionUtils.isEmpty(taskInfos)) return Collections.emptyMap();

        Map<String, List<ThreadTaskInfo>> resultMap = new HashMap<>();
        for (ThreadTaskInfo taskInfo : taskInfos) {
            List<ThreadTaskInfo> taskInfoSubList = resultMap.computeIfAbsent(taskInfo.getTpId(), k -> new ArrayList<>());
            taskInfoSubList.add(taskInfo);
        }
        return resultMap;
    }
}
