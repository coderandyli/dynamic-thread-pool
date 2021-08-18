package com.coerandyli.dynamic.thread.pool.admin.test;

import com.coderandyli.dynamic.thread.pool.client.ExecutorManager;
import com.coderandyli.dynamic.thread.pool.client.MetricsStorage;
import com.coderandyli.dynamic.thread.pool.client.ThreadPoolDynamicInfo;
import com.coderandyli.dynamic.thread.pool.client.ThreadTaskInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
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
    private MetricsStorage metricsStorage;

    @GetMapping("/exec-async-task")
    public void asyncTask() {
        log.info("exec async task, the current is 【{}】", Thread.currentThread().getName());
        orderThreadPool.execute(new OrderBiz());
    }

    @GetMapping("/task-info/query")
    public void queryTaskInfo() {
        // 获取线程任务原始数据
        Map<String, List<ThreadTaskInfo>> resultMap = metricsStorage.queryAllTaskInfosByDuration(0, 0);
        log.info("线程任务执行数据 is 【{}】", resultMap.toString());
        ThreadPoolDynamicInfo threadPoolInfo = ExecutorManager.getInstance().getThreadPoolInfo("biz-order");
        log.info("线程池基本信息: 【{}】", threadPoolInfo.toString());
    }
}
