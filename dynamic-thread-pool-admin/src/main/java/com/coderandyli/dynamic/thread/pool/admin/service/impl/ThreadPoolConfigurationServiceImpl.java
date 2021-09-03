package com.coderandyli.dynamic.thread.pool.admin.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.coderandyli.dynamic.thread.pool.admin.entity.ThreadPoolConfiguration;
import com.coderandyli.dynamic.thread.pool.admin.mapper.ThreadPoolConfigurationMapper;
import com.coderandyli.dynamic.thread.pool.admin.service.ThreadPoolConfigurationService;
import org.springframework.stereotype.Service;



@Service("threadPoolConfigurationService")
public class ThreadPoolConfigurationServiceImpl extends ServiceImpl<ThreadPoolConfigurationMapper, ThreadPoolConfiguration> implements ThreadPoolConfigurationService {

}
