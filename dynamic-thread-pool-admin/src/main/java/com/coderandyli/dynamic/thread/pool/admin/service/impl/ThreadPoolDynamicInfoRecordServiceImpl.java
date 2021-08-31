package com.coderandyli.dynamic.thread.pool.admin.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.coderandyli.dynamic.thread.pool.admin.entity.ThreadPoolDynamicInfoRecord;
import com.coderandyli.dynamic.thread.pool.admin.mapper.ThreadPoolDynamicInfoRecordMapper;
import com.coderandyli.dynamic.thread.pool.admin.service.ThreadPoolDynamicInfoRecordService;
import org.springframework.stereotype.Service;


@Service("threadPoolDynamicInfoRecordService")
public class ThreadPoolDynamicInfoRecordServiceImpl extends ServiceImpl<ThreadPoolDynamicInfoRecordMapper, ThreadPoolDynamicInfoRecord> implements ThreadPoolDynamicInfoRecordService {

}
