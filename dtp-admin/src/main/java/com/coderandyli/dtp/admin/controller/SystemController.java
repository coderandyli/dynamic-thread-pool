package com.coderandyli.dtp.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author lizhenzhen
 * @version 1.0
 * @date 2021/1/19 上午11:11
 */
@Slf4j
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
