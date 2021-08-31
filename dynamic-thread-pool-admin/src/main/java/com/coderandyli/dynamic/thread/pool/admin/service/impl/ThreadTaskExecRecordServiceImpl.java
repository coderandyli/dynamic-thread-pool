package com.coderandyli.dynamic.thread.pool.admin.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.coderandyli.dynamic.thread.pool.admin.entity.ThreadTaskExecRecord;
import com.coderandyli.dynamic.thread.pool.admin.mapper.ThreadTaskExecRecordMapper;
import com.coderandyli.dynamic.thread.pool.admin.service.ThreadTaskExecRecordService;
import org.springframework.stereotype.Service;


@Service("threadTaskExecRecordService")
public class ThreadTaskExecRecordServiceImpl extends ServiceImpl<ThreadTaskExecRecordMapper, ThreadTaskExecRecord> implements ThreadTaskExecRecordService {

}
