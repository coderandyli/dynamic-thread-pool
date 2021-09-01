package com.coderandyli.dynamic.thread.pool.monitor.storage;

import com.coderandyli.dynamic.thread.pool.admin.entity.ThreadPoolDynamicInfoRecord;
import com.coderandyli.dynamic.thread.pool.admin.entity.ThreadTaskExecRecord;
import com.coderandyli.dynamic.thread.pool.admin.service.ThreadPoolDynamicInfoRecordService;
import com.coderandyli.dynamic.thread.pool.admin.service.ThreadTaskExecRecordService;
import com.coderandyli.dynamic.thread.pool.client.ThreadPoolDynamicInfo;
import com.coderandyli.dynamic.thread.pool.client.ThreadTaskInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
        ThreadTaskExecRecord threadTaskExecRecord = new ThreadTaskExecRecord();
        BeanUtils.copyProperties(taskInfo, threadTaskExecRecord);
        threadTaskExecRecord.setTpId(taskInfo.getThreadPoolUniqueId());
        threadTaskExecRecord.setStartTime(taskInfo.getTimestamp());
        threadTaskExecRecord.setRecordTime(new Date());
        threadTaskExecRecordService.insert(threadTaskExecRecord);
    }

    @Override
    public void saveThreadPoolInfo(ThreadPoolDynamicInfo threadPoolInfo) {
        ThreadPoolDynamicInfoRecord poolDynamicInfoRecord = new ThreadPoolDynamicInfoRecord();
        BeanUtils.copyProperties(threadPoolInfo, poolDynamicInfoRecord);
        poolDynamicInfoRecord.setTpId(threadPoolInfo.getUniqueId());
        poolDynamicInfoRecord.setRecordTime(new Date());
        threadPoolDynamicInfoRecordService.insert(poolDynamicInfoRecord);
    }

    @Override
    public List<ThreadTaskInfo> queryTaskInfosByDuration(String taskName, long startTime, long endTime) {
        return null;
    }

    @Override
    public Map<String, List<ThreadTaskInfo>> queryAllTaskInfosByDuration(long startTime, long endTime) {
        return null;
    }

    @Override
    public ThreadPoolDynamicInfo queryLastThreadPoolInfo() {
        return null;
    }

    @Override
    public List<ThreadPoolDynamicInfo> queryAllThreadPoolInfo() {
        return null;
    }
}
