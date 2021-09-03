package com.coderandyli.dynamic.thread.pool.admin.service;

import com.baomidou.mybatisplus.service.IService;
import com.coderandyli.dynamic.thread.pool.admin.entity.ThreadPoolDynamicInfoRecord;

/**
 * 线程池动态信息记录表
 *
 * @author lizhenzhen
 * 2021-08-31 15:36:22
 */
public interface ThreadPoolDynamicInfoRecordService extends IService<ThreadPoolDynamicInfoRecord> {
    ThreadPoolDynamicInfoRecord queryLastOneByTpId(String tpId);
}

