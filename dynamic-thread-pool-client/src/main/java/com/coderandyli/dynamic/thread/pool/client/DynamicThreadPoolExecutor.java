package com.coderandyli.dynamic.thread.pool.client;

import com.coderandyli.dynamic.thread.pool.client.monitor.*;
import com.coderandyli.dynamic.thread.pool.client.monitor.metrics.MetricsCollector;
import com.coderandyli.dynamic.thread.pool.client.monitor.metrics.ThreadPoolRejectMetricManager;
import org.springframework.beans.BeanUtils;

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
     * 应用名称
     */
    private String applicationName;
    /**
     * 线程池名称
     */
    private String poolName;
    /**
     * 线程池唯一识别号
     */
    private String uniqueId;
    /**
     * 线程任务执行信息
     */
    private ThreadTaskInfo taskInfo;
    /**
     * 数据采集
     */
    private MetricsCollector metricsCollector;

    public DynamicThreadPoolExecutor(String applicationName, String poolName, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        this(applicationName, poolName, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, newNamedThreadFactory(poolName));
    }

    public DynamicThreadPoolExecutor(String applicationName, String poolName, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        this(applicationName, poolName, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, countRejectHandler);
    }

    public DynamicThreadPoolExecutor(String applicationName, String poolName, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        this(applicationName, poolName, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, newNamedThreadFactory(poolName), handler);
    }

    public DynamicThreadPoolExecutor(String applicationName, String poolName, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        this.poolName = poolName;
        this.metricsCollector = (MetricsCollector) SpringContextUtils.getBean("metricsCollector");
        this.taskInfo = new ThreadTaskInfo();
        this.applicationName = applicationName;

        // 注册线程池
        this.metricsCollector.registerExecutorService(this);
    }

    @Override
    public void shutdown() {
        // ThreadPool will be shutdown:
        saveThreadPoolInfo();
        super.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        saveThreadPoolInfo();
        // ThreadPool going to immediately be shutdown
        // 记录被丢弃的任务, 暂时只记录日志, 后续可根据业务场景做进一步处理
        List<Runnable> dropTasks = null;
        dropTasks = super.shutdownNow();
        return dropTasks;
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        saveThreadPoolInfo();

        // 记录线程任务数据
        taskInfo.setThreadPoolUniqueId(this.getUniqueId());
        taskInfo.setTaskName(t.getName());
        taskInfo.setTimestamp(System.currentTimeMillis());
        super.beforeExecute(t, r);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        // 记录并存储任务数据
        taskInfo.setResponseTime((double) System.currentTimeMillis() - taskInfo.getTimestamp());
        saveTaskInfo();
        saveThreadPoolInfo();
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
                ThreadPoolRejectMetricManager.increment(dynamicThreadPoolExecutor.getUniqueId());
            }
            defaultRejectHandler.rejectedExecution(r, executor);
        }
    }

    public String getApplicationName() {
        return applicationName;
    }

    public String getPoolName() {
        return poolName;
    }

    /**
     * 线程池唯一识别号（即threaPoolId）
     */
    public String getUniqueId() {
        return this.applicationName + ":" +
                this.poolName;
    }

    /**
     * 存储任务数据
     */
    private void saveTaskInfo() {
        this.metricsCollector.recordTask(taskInfo);
    }

    /**
     * 存储线程池信息
     */
    public void saveThreadPoolInfo() {
        ThreadPoolDynamicInfo threadPoolInfo = new ThreadPoolDynamicInfo();
        BeanUtils.copyProperties(this, threadPoolInfo);
        threadPoolInfo.setType(1);
        threadPoolInfo.setRejectCount(
                ThreadPoolRejectMetricManager.get(this.getUniqueId())
        );
        this.metricsCollector.recordThreadPoolInfo(threadPoolInfo);
    }
}
