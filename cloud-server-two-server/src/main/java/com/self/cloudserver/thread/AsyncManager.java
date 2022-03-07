package com.self.cloudserver.thread;

import com.self.cloudserver.utils.SpringUtils;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 异步任务管理器
 */
public class AsyncManager {

    /**
     * 操作延迟10毫秒
     */
    private final int OPERATE_DELAY_TIME = 10;

    /**
     * 异步操作任务调度线程池
     */
    private ScheduledExecutorService executor = SpringUtils.getBean("scheduledExecutorService");

    /**
     * 单例
     */
    private AsyncManager(){
    }

    private static AsyncManager asyncManager = new AsyncManager();

    public static AsyncManager threadPool() {
        return asyncManager;
    }

    /**
     * 执行任务
     * @param task 任务
     */
    public void execute(Runnable task) {
        executor.schedule(task, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
    }

}
