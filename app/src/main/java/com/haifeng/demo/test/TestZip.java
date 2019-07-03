package com.haifeng.demo.test;

import android.os.Handler;
import android.util.Log;

import com.haifeng.demo.lib.Observable;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

public class TestZip<T> {

    Handler handler = new Handler() {
    };
    Queue<String> queue1 = new LinkedBlockingDeque<>();
    Queue<Integer> queue2 = new LinkedBlockingDeque<>();


   public ZipObserver<T>[] zipObservers = new ZipObserver[2];


    public TestZip() {
        for (int i=0;i<zipObservers.length;i++){
            zipObservers[i] =new ZipObserver<T>();
        }
    }



    public void test() {




        T[] os = (T[]) new Object[2];


        a:
        for (; ; ) {
            int length = 2;
            for (int i = 0; i < length; i++) {
                if (zipObservers[i].queue.isEmpty()) {
                    break a;

                }
                if (i == 1) {
                    os[0] = zipObservers[0].queue.poll();
                    os[1] = zipObservers[1].queue.poll();
                    if (os[0] != null && os[1] != null) {
                        Log.i("test", os[0] + "<>" + os[1]);
                        zipObservers[0].queue.clear();
                        break a;
                    }
                }
            }
        }



    }

    public static <B, A, R> Observable<R> zip(TestZip<? extends A> b) {
        // return zipArray(toFunction(zipper), source1, source2);
        return zipArrayBiFunction(b);
    }


    public static <T, R> Observable<R> zipArrayBiFunction(TestZip<? extends T> b) {
        return null;
    }






}
