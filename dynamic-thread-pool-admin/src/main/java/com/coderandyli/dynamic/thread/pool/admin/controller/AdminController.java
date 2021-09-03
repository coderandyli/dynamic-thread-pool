package com.coderandyli.dynamic.thread.pool.admin.controller;

import com.coderandyli.dynamic.thread.pool.core.ModifyThreadPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    /**
     * 更新线程池配置信息
     *
     * @param modifyThreadPool
     * @return
     */
    @PostMapping("/thread-pool/update")
    public Boolean updateThreadPoolConfig(@RequestBody ModifyThreadPool modifyThreadPool) {
        log.debug("modify the thread pool config, the arg is 【{}】", modifyThreadPool);

        // TODO: 2021/9/3 存储配置信息
        return Boolean.TRUE;
    }

    /**
     * 查询指定应用的线程池的最新配置信息
     * @return
     */
    @GetMapping("/{application}/thread-pool/query")
    public List<ModifyThreadPool> queryThreadPoolConfig(@PathVariable("application") String application) {
        log.debug("Query the configuration of a thread pool, the arg is 【{}】", application);

        // TODO: 2021/9/3 完善
        ArrayList<ModifyThreadPool> results = new ArrayList<>();
        ModifyThreadPool config1 = new ModifyThreadPool();
        config1.setTpId("test-app:biz-order");
        config1.setCorePoolSize(100);
        config1.setMaximumPoolSize(1000);
        config1.setKeepAliveTime(10);
        results.add(config1);

        ModifyThreadPool config2 = new ModifyThreadPool();
        config2.setTpId("test-app:biz-order1");
        config2.setCorePoolSize(10);
        config2.setMaximumPoolSize(20);
        config2.setKeepAliveTime(10);
        results.add(config2);
        return results;
    }



}
