package com.coderandyli.dynamic.thread.pool.client.monitor;


import com.google.common.base.Joiner;

import java.util.ArrayList;
import java.util.List;

public class PerfCounterTest {
    public static void main(String[] args) {

        // 定时触发统计并将结果输出到邮件
        List<String> emailToAddresses = new ArrayList<>();
        emailToAddresses.add("coderandyli@163.com");
        EmailReporter emailReporter = new EmailReporter(emailToAddresses);
        emailReporter.startDailyReport();

        // 收集接口访问数据
        MetricsCollector collector = new MetricsCollector();
        collector.recordRequest(new RequestInfo("register", 10d, System.currentTimeMillis()));
        collector.recordRequest(new RequestInfo("register", 11d, System.currentTimeMillis()));
        collector.recordRequest(new RequestInfo("register", 12d, System.currentTimeMillis()));
        collector.recordRequest(new RequestInfo("login", 1d, System.currentTimeMillis()));
        collector.recordRequest(new RequestInfo("login", 0.1d, System.currentTimeMillis()));


        // 定时触发统计并将结果显示到终端
        ConsoleReporter consoleReporter = new ConsoleReporter();
        consoleReporter.startRepeatedReport(10, 10);

        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}