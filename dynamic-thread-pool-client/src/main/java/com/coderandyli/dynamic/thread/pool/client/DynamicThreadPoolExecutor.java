package com.coderandyli.dynamic.thread.pool.client;

import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义动态线程池
 *
 * @Date 2021/8/16 7:28 下午
 * @Created by lizhenzhen
 */
public class DynamicThreadPoolExecutor extends ThreadPoolExecutor {
    /**
     * 线程池名称
     */
    private String poolName;

    public DynamicThreadPoolExecutor(String poolName, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        this(poolName, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, newNamedThreadFactory(poolName));
    }

    public DynamicThreadPoolExecutor(String poolName, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        this(poolName, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, defaultRejectHandler);
    }

    public DynamicThreadPoolExecutor(String poolName, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        this(poolName, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, newNamedThreadFactory(poolName), handler);
    }

    public DynamicThreadPoolExecutor(String poolName, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        this.poolName = poolName;
    }

    @Override
    public void shutdown() {
        // TODO: 2021/8/16 记录线程池实时状态
        // ThreadPool will be shutdown:
        super.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        // TODO: 2021/8/16 记录线程池实时状态
        // ThreadPool going to immediately be shutdown
        // 记录被丢弃的任务, 暂时只记录日志, 后续可根据业务场景做进一步处理
        List<Runnable> dropTasks = null;
            dropTasks = super.shutdownNow();
        return dropTasks;
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        // TODO: 2021/8/16 记录线程池实时状态
        super.beforeExecute(t, r);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        // TODO: 2021/8/16 记录线程池实时状态
        super.afterExecute(r, t);
    }

    public static ThreadFactory newNamedThreadFactory(String name) {
        return new NamedThreadFactory(name);
    }


    /**
     * 线程工厂类，参考{@link Executors#DefaultThreadFactory}
     */
    private static class NamedThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        NamedThreadFactory(String biz) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = "dynamic-pool-" + biz + "-" +
                    poolNumber.getAndIncrement() +
                    "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }

    private static final RejectedExecutionHandler defaultRejectHandler = new ThreadPoolExecutor.AbortPolicy();
    public static final RejectedExecutionHandler countRejectHandler = new CountRejectedHandler();


    public static RejectedExecutionHandler getDefaultRejectHandler() {
        return defaultRejectHandler;
    }

    public static RejectedExecutionHandler getCountRejectHandler() {
        return countRejectHandler;
    }

    /**
     * reject handler
     */
    static class CountRejectedHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            if (executor instanceof DynamicThreadPoolExecutor) {
                DynamicThreadPoolExecutor dynamicThreadPoolExecutor = (DynamicThreadPoolExecutor) executor;
                // TODO: 2021/8/16 记录任务拒绝次数
                // ThreadPoolRejectMetricManager.increment(namedThreadPoolExecutor.getPoolName());
            }
            defaultRejectHandler.rejectedExecution(r, executor);
        }
    }


    public String getPoolName() {
        return poolName;
    }
}
