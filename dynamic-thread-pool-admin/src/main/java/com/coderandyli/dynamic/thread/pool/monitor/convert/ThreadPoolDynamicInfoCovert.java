package com.coderandyli.dynamic.thread.pool.monitor.convert;

import com.coderandyli.dynamic.thread.pool.admin.entity.ThreadPoolDynamicInfoRecord;
import com.coderandyli.dynamic.thread.pool.client.ThreadPoolDynamicInfo;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * ThreadPoolDynamicInfo 相关转换
 *
 * @Date 2021/9/2 10:07 上午
 * @Created by lizhenzhen
 */
public class ThreadPoolDynamicInfoCovert {

    /**
     * ThreadPoolDynamicInfo 转 ThreadPoolDynamicInfoRecord
     * @param threadPoolInfo
     * @return
     */
    public static ThreadPoolDynamicInfoRecord threadPoolDynamicInfoToThreadPoolDynamicInfoRecord(ThreadPoolDynamicInfo threadPoolInfo) {
        if (threadPoolInfo == null) return null;

        ThreadPoolDynamicInfoRecord poolDynamicInfoRecord = new ThreadPoolDynamicInfoRecord();
        BeanUtils.copyProperties(threadPoolInfo, poolDynamicInfoRecord);
        poolDynamicInfoRecord.setRecordTime(new Date());
        return poolDynamicInfoRecord;
    }

    /**
     * ThreadPoolDynamicInfoRecord 转 ThreadPoolDynamicInfo
     * @param threadPoolDynamicInfoRecord
     * @return
     */
    public static ThreadPoolDynamicInfo threadPoolDynamicInfoRecordToThreadPoolDynamicInfo(ThreadPoolDynamicInfoRecord threadPoolDynamicInfoRecord) {
        if (threadPoolDynamicInfoRecord == null) return null;

        ThreadPoolDynamicInfo threadPoolInfo = new ThreadPoolDynamicInfo();
        BeanUtils.copyProperties(threadPoolDynamicInfoRecord, threadPoolInfo);
        return threadPoolInfo;
    }


}
