package com.coderandyli.dynamic.thread.pool.admin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.coderandyli.dynamic.thread.pool.admin.entity.ThreadPoolConfiguration;
import com.coderandyli.dynamic.thread.pool.admin.mapper.ThreadPoolConfigurationMapper;
import com.coderandyli.dynamic.thread.pool.admin.service.ThreadPoolConfigurationService;
import com.coderandyli.dynamic.thread.pool.core.ModifyThreadPool;
import com.coderandyli.dynamic.thread.pool.monitor.convert.ThreadPoolConfigurationCovert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;


@Slf4j
@Service("threadPoolConfigurationService")
public class ThreadPoolConfigurationServiceImpl extends ServiceImpl<ThreadPoolConfigurationMapper, ThreadPoolConfiguration> implements ThreadPoolConfigurationService {

    @Autowired
    private ThreadPoolConfigurationMapper threadPoolConfigurationMapper;

    @Override
    public List<ModifyThreadPool> selectLastListByApplication(String application) {
        if (log.isDebugEnabled()) {
            log.debug("Query the configuration of a thread pool, the arg is 【{}】", application);
        }

        List<String> unExecTpIds = threadPoolConfigurationMapper.queryAllUnExecutedTpIdssByApplication(application);
        if (CollectionUtils.isEmpty(unExecTpIds)) return Collections.emptyList();

        List<ModifyThreadPool> modifyThreadPools = new ArrayList<>();
        unExecTpIds.forEach(tpId -> {
            ThreadPoolConfiguration threadPoolConfiguration = threadPoolConfigurationMapper.queryLastUnexecutedOneByTpId(tpId);
            if (threadPoolConfiguration != null) {
                ModifyThreadPool modifyThreadPool = ThreadPoolConfigurationCovert.threadPoolConfigurationToModifyThreadPool(threadPoolConfiguration);
                modifyThreadPools.add(modifyThreadPool);
            }
        });
        return modifyThreadPools;
    }

    @Override
    public Boolean markExecuted(List<String> tpIds) {
        if (log.isDebugEnabled()) {
            log.debug("the thread pool config mark executed, the tpId is 【{}】", tpIds);
        }

        List<ThreadPoolConfiguration> threadPoolConfigurations = this.selectList(new EntityWrapper<ThreadPoolConfiguration>()
                .in("tp_id", tpIds)
                .eq("is_exec", false)
        );
        if (CollectionUtils.isEmpty(threadPoolConfigurations)) return false;

        threadPoolConfigurations.forEach(item -> {
            item.setExec(true);
            item.setExecTime(new Date());
        });
        boolean bool = this.updateBatchById(threadPoolConfigurations);
        if (bool) {
            log.info("the thread pool config mark executed. the result is【{}】", Arrays.toString(threadPoolConfigurations.toArray()));
        }
        return bool;
    }
}
