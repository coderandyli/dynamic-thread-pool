package com.coderandyli.dtp.admin.controller;

import com.coderandyli.dtp.admin.convert.ThreadPoolConfigurationCovert;
import com.coderandyli.dtp.admin.entity.ThreadPoolConfiguration;
import com.coderandyli.dtp.admin.service.ThreadPoolConfigurationService;
import com.coderandyli.dtp.core.ModifyThreadPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Admin Controller
 *
 * @Date 2021/9/3 10:53 上午
 * @Created by lizhenzhen
 */
@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ThreadPoolConfigurationService threadPoolConfigurationService;

    /**
     * 更新线程池配置信息
     *
     * @param modifyThreadPool
     * @return
     */
    @PostMapping("/thread-pool/config/update")
    public Boolean updateThreadPoolConfig(@RequestBody ModifyThreadPool modifyThreadPool) {
        log.debug("modify the thread pool config, the arg is 【{}】", modifyThreadPool);
        ThreadPoolConfiguration threadPoolConfiguration = ThreadPoolConfigurationCovert.modifyThreadPoolToThreadPoolConfiguration(modifyThreadPool);
        return threadPoolConfigurationService.insert(threadPoolConfiguration);
    }

    /**
     * 查询指定应用的线程池的最新配置信息
     * @return
     */
    @GetMapping("/{application}/thread-pool/config/query")
    public List<ModifyThreadPool> queryThreadPoolConfig(@PathVariable("application") String application) {
        return threadPoolConfigurationService.selectLastListByApplication(application);
    }

    /**
     * 线程池配置信息标记已执行
     *
     * @param tpIds
     * @return
     */
    @PostMapping("/thread-pool/config/executed")
    public Boolean markExecuted(@RequestBody List<String> tpIds) {
        return threadPoolConfigurationService.markExecuted(tpIds);
    }


}
