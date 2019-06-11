package com.haifeng.demo.lib;


import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class IoExecutor {
    private Executor executor = Executors.newFixedThreadPool(5, threadFactory());

    private IoExecutor() {
    }

    private static class Builder {
        static IoExecutor ioScheduler = new IoExecutor();
    }


    public static IoExecutor getInstance() {
        return Builder.ioScheduler;
    }


    public void executeRunable(Runnable runnable) {
        executor.execute(runnable);
    }


    private static ThreadFactory threadFactory() {
        final AtomicInteger mCount = new AtomicInteger(1);
        return new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                Thread result = new Thread(runnable, "IO Thread #" + mCount.getAndIncrement());
                return result;
            }
        };
    }


}
