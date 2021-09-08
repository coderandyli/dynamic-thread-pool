package com.coderandyli.dtp.monitor.reporter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Date 2021/8/23 11:48 上午
 * @Created by lizhenzhen
 */
@Slf4j
@Component
public class ConsoleReporter extends ScheduleReporter {
    private final ScheduledExecutorService executor;

    public ConsoleReporter() {
        if (log.isDebugEnabled()) {
            log.debug("ConsoleReporter init...");
        }
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
