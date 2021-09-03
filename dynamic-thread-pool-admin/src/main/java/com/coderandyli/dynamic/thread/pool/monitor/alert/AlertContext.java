package com.coderandyli.dynamic.thread.pool.monitor.alert;

import com.coderandyli.dynamic.thread.pool.monitor.reporter.ConsoleReporter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Date 2021/9/2 2:44 下午
 * @Created by lizhenzhen
 */
@Component
public class AlertContext implements InitializingBean {
    @Autowired
    private ConsoleReporter consoleReporter;

    @Override
    public void afterPropertiesSet() throws Exception {
        // 每10秒查询60秒内的任务执行记录
        consoleReporter.startRepeatedReport(10, 60);
    }
}
