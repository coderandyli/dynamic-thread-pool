package com.coderandyli.dynamic.thread.pool.monitor.reporter;
import org.springframework.stereotype.Component;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Date 2021/8/23 11:48 上午
 * @Created by lizhenzhen
 */
@Component
public class ConsoleReporter extends ScheduleReporter {
    private ScheduledExecutorService executor;

    public ConsoleReporter() {
        System.out.println("ConsoleReporter init...");
         this.executor = Executors.newSingleThreadScheduledExecutor();
    }

    /**
     * repeated report
     * @param periodInSeconds 周期(单位：秒)
     * @param durationInSeconds  间隔时间(单位: 秒)
     */
    public void startRepeatedReport(long periodInSeconds, long durationInSeconds) {
            executor.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    long durationInMillis = durationInSeconds * 1000;
                    long endTimeInMillis = System.currentTimeMillis();
                    long startTimeInMillis = endTimeInMillis - durationInMillis;
                    doStatAndReport(startTimeInMillis, endTimeInMillis);
                }
            }, 0l, periodInSeconds, TimeUnit.SECONDS);
    }
}
