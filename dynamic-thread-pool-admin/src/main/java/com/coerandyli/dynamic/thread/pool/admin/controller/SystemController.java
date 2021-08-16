package com.coerandyli.dynamic.thread.pool.admin.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author lizhenzhen
 * @version 1.0
 * @date 2021/1/19 上午11:11
 */
@RestController
@RequestMapping("/sys")
public class SystemController {

    @Resource
    private ApplicationContext applicationContext;

    /**
     * 系统健康
     *
     * @return
     */
    @GetMapping("/health")
    public String systemHealth() {
        long startupDate = applicationContext.getStartupDate();
        long dataTime = System.currentTimeMillis() - startupDate;
        return "system safety check, the request time is " + dataTime;
    }
}
