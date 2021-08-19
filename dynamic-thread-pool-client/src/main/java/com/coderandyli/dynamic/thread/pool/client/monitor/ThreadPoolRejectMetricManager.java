package com.coderandyli.dynamic.thread.pool.client.monitor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by mjd on 2020/6/10 19:33
 */
public class ThreadPoolRejectMetricManager {
    private static final ConcurrentMap<String, AtomicInteger> rejectCountMap = new ConcurrentHashMap<>();

    public static void increment(String threadPoolId) {
        AtomicInteger rejectCount = rejectCountMap.get(threadPoolId);
        if (rejectCount == null) {
            rejectCountMap.putIfAbsent(threadPoolId, new AtomicInteger());
        }
        rejectCount = rejectCountMap.get(threadPoolId);
        rejectCount.incrementAndGet();
    }

    public static int get(String threadPoolId) {
        AtomicInteger rejectCount = rejectCountMap.get(threadPoolId);
        if (rejectCount == null) {
            return 0;
        }
        return rejectCount.get();
    }

    public static int getAndReset(String threadPoolId) {
        AtomicInteger rejectCount = rejectCountMap.get(threadPoolId);
        if (rejectCount == null) {
            return 0;
        }
        return rejectCount.getAndSet(0);
    }
}
