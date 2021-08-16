package com.coderandyli.dynamic.thread.pool.client;

import java.util.List;
import java.util.concurrent.*;

/**
 * @Date 2021/8/16 7:28 下午
 * @Created by lizhenzhen
 */
public class DynamicThreadPoolExecutor extends ThreadPoolExecutor {
    public DynamicThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public DynamicThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public DynamicThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public DynamicThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
    }

    private static final RejectedExecutionHandler defaultRejectHandler = new ThreadPoolExecutor.AbortPolicy();
    public static final RejectedExecutionHandler countRejectHandler = new CountRejectedHandler();

    /**
     * reject handler
     */
    static class CountRejectedHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            if (executor instanceof DynamicThreadPoolExecutor) {
                DynamicThreadPoolExecutor namedThreadPoolExecutor = (DynamicThreadPoolExecutor) executor;
                // TODO: 2021/8/16 记录任务拒绝次数
                // ThreadPoolRejectMetricManager.increment(namedThreadPoolExecutor.getPoolName());
            }
            defaultRejectHandler.rejectedExecution(r, executor);
        }
    }
}
