package com.coderandyli.dtp.admin.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.coderandyli.dtp.admin.entity.ThreadTaskExecRecord;
import com.coderandyli.dtp.admin.mapper.ThreadTaskExecRecordMapper;
import com.coderandyli.dtp.admin.service.ThreadTaskExecRecordService;
import org.springframework.stereotype.Service;


@Service("threadTaskExecRecordService")
public class ThreadTaskExecRecordServiceImpl extends ServiceImpl<ThreadTaskExecRecordMapper, ThreadTaskExecRecord> implements ThreadTaskExecRecordService {

}
