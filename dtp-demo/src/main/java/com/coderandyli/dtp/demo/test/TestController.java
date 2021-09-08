package com.coderandyli.dtp.demo.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;

/**
 * @Date 2021/8/18 11:25 上午
 * @Created by lizhenzhen
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {
    @Resource(name = "bizOrderThreadPool")
    private ExecutorService orderThreadPool;

    @GetMapping("/exec-async-task")
    public void asyncTask() {
        log.debug("exec async task, the current is 【{}】", Thread.currentThread().getName());
        orderThreadPool.execute(new OrderBiz());
    }
}
