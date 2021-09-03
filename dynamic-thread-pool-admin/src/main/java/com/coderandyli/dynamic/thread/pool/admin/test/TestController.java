package com.coderandyli.dynamic.thread.pool.admin.test;

import com.coderandyli.dynamic.thread.pool.admin.service.ThreadTaskExecRecordService;
import com.coderandyli.dynamic.thread.pool.monitor.metrics.storage.MetricsStorage;
import com.coderandyli.dynamic.thread.pool.monitor.reporter.ConsoleReporter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Autowired
    @Qualifier("mysqlMetricsStorage")
    private MetricsStorage metricsStorage;

    @Autowired
    private ConsoleReporter consoleReporter;

    @Autowired
    private ThreadTaskExecRecordService taskExecRecordService;

    @GetMapping("/exec-async-task")
    public void asyncTask() {
        log.debug("exec async task, the current is 【{}】", Thread.currentThread().getName());
        orderThreadPool.execute(new OrderBiz());
    }

    @GetMapping("/task-info/query")
    public void queryTaskInfo() {
        // 每10秒查询60秒内的任务执行记录
        consoleReporter.startRepeatedReport(10, 60);
    }
}
