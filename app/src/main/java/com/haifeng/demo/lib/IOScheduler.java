package com.haifeng.demo.lib;

public class IOScheduler implements Scheduler {


    @Override
    public void execute(Runnable runnable) {
        IoExecutor.getInstance().executeRunable(runnable);
    }



}
