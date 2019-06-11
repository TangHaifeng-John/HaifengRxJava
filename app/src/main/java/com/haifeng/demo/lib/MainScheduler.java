package com.haifeng.demo.lib;


import android.os.Handler;
import android.os.Looper;

public class MainScheduler implements Scheduler {

    Handler handler =new Handler(Looper.getMainLooper());

    @Override
    public void execute(Runnable runnable) {
        handler.post(runnable);
    }



}
