package com.coderandyli.dynamic.thread.pool.monitor.convert;

import com.coderandyli.dynamic.thread.pool.admin.entity.ThreadTaskExecRecord;
import com.coderandyli.dynamic.thread.pool.core.ThreadTaskInfo;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * ThreadTaskInfo相关转换
 *
 * @Date 2021/9/2 10:08 上午
 * @Created by lizhenzhen
 */
public class ThreadTaskInfoCovert {

    /**
     * ThreadTaskInfo 转 ThreadTaskExecRecord
     * @param taskInfo
     * @return
     */
    public static ThreadTaskExecRecord threadTaskInfoToThreadTaskExecRecord(ThreadTaskInfo taskInfo) {
        if (taskInfo == null) return null;

        ThreadTaskExecRecord threadTaskExecRecord = new ThreadTaskExecRecord();
        BeanUtils.copyProperties(taskInfo, threadTaskExecRecord);
        threadTaskExecRecord.setStartTime(taskInfo.getTimestamp());
        threadTaskExecRecord.setRecordTime(new Date());
        return threadTaskExecRecord;
    }

    /**
     * ThreadTaskExecRecord 转 ThreadTaskInfo
     * @param threadTaskExecRecord
     * @return
     */
    public static ThreadTaskInfo threadTaskExecRecordToThreadTaskInfo(ThreadTaskExecRecord threadTaskExecRecord) {
        if (threadTaskExecRecord == null) return null;

        ThreadTaskInfo threadTaskInfo = new ThreadTaskInfo();
        BeanUtils.copyProperties(threadTaskExecRecord, threadTaskInfo);
        threadTaskInfo.setTimestamp(threadTaskExecRecord.getStartTime());
        return threadTaskInfo;
    }
}
