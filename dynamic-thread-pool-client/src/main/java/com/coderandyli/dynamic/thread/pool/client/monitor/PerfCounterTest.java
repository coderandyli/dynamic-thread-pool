package com.coderandyli.dynamic.thread.pool.client.monitor;


import com.google.common.base.Joiner;

import java.util.ArrayList;
import java.util.List;

public class PerfCounterTest {
    public static void main(String[] args) {

        Joiner joiner = Joiner.on("; ").skipNulls();
        System.out.println(joiner.join("Harry", null, "Ron", "Hermione"));



        // 定时触发统计并将结果显示到终端
        ConsoleReporter consoleReporter = new ConsoleReporter();
        consoleReporter.startRepeatedReport(60, 60);

        // 定时触发统计并将结果输出到邮件
        List<String> emailToAddresses = new ArrayList<>();
        emailToAddresses.add("coderandyli@163.com");
        EmailReporter emailReporter = new EmailReporter(emailToAddresses);
        emailReporter.startDailyReport();

        // 收集接口访问数据
        MetricsCollector collector = new MetricsCollector();
        collector.recordRequest(new RequestInfo("register", 123d, 10234l));
        collector.recordRequest(new RequestInfo("register", 223, 11234));
        collector.recordRequest(new RequestInfo("register", 323, 12334));
        collector.recordRequest(new RequestInfo("login", 23, 12434));
        collector.recordRequest(new RequestInfo("login", 1223, 14234));

        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}