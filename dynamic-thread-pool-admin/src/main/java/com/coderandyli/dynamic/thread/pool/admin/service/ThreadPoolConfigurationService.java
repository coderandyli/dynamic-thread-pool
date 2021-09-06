package com.coderandyli.dynamic.thread.pool.admin.service;


import com.baomidou.mybatisplus.service.IService;
import com.coderandyli.dynamic.thread.pool.admin.entity.ThreadPoolConfiguration;
import com.coderandyli.dynamic.thread.pool.core.ModifyThreadPool;

import java.util.List;

/**
 * 线程池配置信息
 *
 * @author lizhenzhen
 * 2021-09-03 17:32:07
 */
public interface ThreadPoolConfigurationService extends IService<ThreadPoolConfiguration> {
    /**
     * 获取指定应用下所有线程池的配置信息
     *
     * @param application
     * @return
     */
    List<ModifyThreadPool> selectLastListByApplication(String application);

    /**
     * 线程池配置标记执行
     * @param tpId
     * @return
     */
    Boolean markExecuted(List<String> tpIds);
}

