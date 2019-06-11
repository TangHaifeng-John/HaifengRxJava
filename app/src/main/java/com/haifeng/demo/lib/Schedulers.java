package com.haifeng.demo.lib;

public class Schedulers {

    public static Scheduler IO = new IOScheduler();
    public static Scheduler MAIN = new MainScheduler();
}
