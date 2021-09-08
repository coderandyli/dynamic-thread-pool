package com.coderandyli.dtp.admin.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.coderandyli.dtp.admin.mapper.ThreadPoolDynamicInfoRecordMapper;
import com.coderandyli.dtp.admin.service.ThreadPoolDynamicInfoRecordService;
import com.coderandyli.dtp.admin.entity.ThreadPoolDynamicInfoRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("threadPoolDynamicInfoRecordService")
public class ThreadPoolDynamicInfoRecordServiceImpl extends ServiceImpl<ThreadPoolDynamicInfoRecordMapper, ThreadPoolDynamicInfoRecord> implements ThreadPoolDynamicInfoRecordService {
    @Autowired
    private ThreadPoolDynamicInfoRecordMapper threadPoolDynamicInfoRecordMapper;

    @Override
    public ThreadPoolDynamicInfoRecord queryLastOneByTpId(String tpId) {


        return threadPoolDynamicInfoRecordMapper.queryLastOneByTpId(tpId);
    }
}
