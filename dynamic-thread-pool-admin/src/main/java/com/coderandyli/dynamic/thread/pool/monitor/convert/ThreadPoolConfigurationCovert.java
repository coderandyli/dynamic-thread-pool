package com.coderandyli.dynamic.thread.pool.monitor.convert;

import com.coderandyli.dynamic.thread.pool.admin.entity.ThreadPoolConfiguration;
import com.coderandyli.dynamic.thread.pool.core.ModifyThreadPool;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @Date 2021/9/3 5:43 下午
 * @Created by lizhenzhen
 */
public class ThreadPoolConfigurationCovert {
    /**
     * ModifyThreadPool covert to ThreadPoolConfiguration
     *
     * @param modifyThreadPool
     * @return
     */
    public static ThreadPoolConfiguration modifyThreadPoolToThreadPoolConfiguration(ModifyThreadPool modifyThreadPool) {
        ThreadPoolConfiguration threadPoolConfiguration = new ThreadPoolConfiguration();
        BeanUtils.copyProperties(modifyThreadPool, threadPoolConfiguration);
        threadPoolConfiguration.setCreateTime(new Date());
        return threadPoolConfiguration;
    }

    /**
     * ThreadPoolConfiguration covert to ModifyThreadPool
     *
     * @param threadPoolConfiguration
     * @return
     */
    public static ModifyThreadPool threadPoolConfigurationToModifyThreadPool(ThreadPoolConfiguration threadPoolConfiguration) {
        ModifyThreadPool modifyThreadPool = new ModifyThreadPool();
        BeanUtils.copyProperties(threadPoolConfiguration, modifyThreadPool);
        return modifyThreadPool;
    }
}
