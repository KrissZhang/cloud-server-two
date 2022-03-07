package com.self.cloudserver.thread;

import com.self.cloudserver.utils.SpringUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.annotation.PreDestroy;
import java.util.concurrent.*;

/**
 * 线程任务池
 * @author zp
 */
@Configuration
@EnableAsync
public class ThreadPool {

    private static final Logger logger = LoggerFactory.getLogger(ThreadPool.class);

    @Bean
    public TaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor threadTaskPool = new ThreadPoolTaskExecutor();

        //设置核心线程数
        threadTaskPool.setCorePoolSize(10);

        //设置最大线程数
        threadTaskPool.setMaxPoolSize(15);

        //设置队列容量
        threadTaskPool.setQueueCapacity(10);

        //设置线程活跃时间为300秒
        threadTaskPool.setKeepAliveSeconds(300);

        //设置默认线程名称
        threadTaskPool.setThreadNamePrefix("taskExecutor-");

        //设置拒绝策略rejection-policy：当任务池已经达到max-size时，如何处理新任务，CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        threadTaskPool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        //等待所有任务执行结束后再关闭线程池
        threadTaskPool.setWaitForTasksToCompleteOnShutdown(true);

        return threadTaskPool;
    }

    @Bean
    public TaskScheduler taskScheduler(){
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();

        //设置线程数
        threadPoolTaskScheduler.setPoolSize(15);

        //设置默认线程名称
        threadPoolTaskScheduler.setThreadNamePrefix("taskScheduler-");

        //等待所有任务执行结束后再关闭线程池
        threadPoolTaskScheduler.setWaitForTasksToCompleteOnShutdown(true);

        //设置线程池中任务的等待时间
        threadPoolTaskScheduler.setAwaitTerminationSeconds(60);

        return threadPoolTaskScheduler;
    }

    /**
     * 执行周期性或定时任务
     */
    @Bean(name = "scheduledExecutorService")
    protected ScheduledExecutorService scheduledExecutorService() {
        return new ScheduledThreadPoolExecutor(15,
                new BasicThreadFactory.Builder().namingPattern("schedule-pool-%d").daemon(true).build(),
                new ThreadPoolExecutor.CallerRunsPolicy()) {
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
                printException(r, t);
            }
        };
    }

    @PreDestroy
    public void destroy() {
        logger.info("=== 关闭线程池 scheduledExecutorService ===");
        ScheduledExecutorService scheduledExecutorService = SpringUtils.getBean("scheduledExecutorService");
        shutdownAndAwaitTermination(scheduledExecutorService);
    }

    /**
     * 停止线程池
     * 先使用shutdown, 停止接收新任务并尝试完成所有已存在任务.
     * 如果超时, 则调用shutdownNow, 取消在workQueue中Pending的任务,并中断所有阻塞函数.
     * 如果仍然超時，則強制退出.
     * 另对在shutdown时线程本身被调用中断做了处理.
     */
    private static void shutdownAndAwaitTermination(ExecutorService pool) {
        if (pool != null && !pool.isShutdown()) {
            pool.shutdown();
            try {
                if (!pool.awaitTermination(120, TimeUnit.SECONDS)) {
                    pool.shutdownNow();
                    if (!pool.awaitTermination(120, TimeUnit.SECONDS)) {
                        logger.info("Pool did not terminate");
                    }
                }
            } catch (InterruptedException ie) {
                pool.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * 打印线程异常信息
     */
    private static void printException(Runnable r, Throwable t) {
        if (t == null && r instanceof Future<?>) {
            try {
                Future<?> future = (Future<?>) r;
                if (future.isDone()) {
                    future.get();
                }
            } catch (CancellationException ce) {
                t = ce;
            } catch (ExecutionException ee) {
                t = ee.getCause();
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }

        if (t != null) {
            logger.error(t.getMessage(), t);
        }
    }

}
