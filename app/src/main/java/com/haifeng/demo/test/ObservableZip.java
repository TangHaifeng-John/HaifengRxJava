package com.haifeng.demo.test;


import com.haifeng.demo.lib.BiFunction;
import com.haifeng.demo.lib.Observable;
import com.haifeng.demo.lib.ObservableSource;
import com.haifeng.demo.lib.Observer;

import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class ObservableZip<T, R> extends Observable<R> {

    BiFunction<? super Object, ? super Object, R> biFunction;

    final ObservableSource<? extends T>[] sources;

    public ObservableZip(BiFunction<? super Object, ? super Object, R> biFunction, ObservableSource<? extends T>[] sources) {
        this.biFunction = biFunction;
        this.sources = sources;
    }

    @Override
    protected void subscribeActual(Observer<? super R> observer) {
        ObservableSource<? extends T>[] sources = this.sources;

    }


    static final class ZipCoordinator<T, R> {
        final Observer<? super R> actual;
        final ObservableSource<? extends T>[] sources;
        final BiFunction<? super Object, ? super Object, R> biFunction;
        final ZipObserver<T, R>[] observers;
        final T[] row;
        ZipCoordinator(Observer<? super R> actual, ObservableSource<? extends T>[] sources,
                       BiFunction<? super Object, ? super Object, R> biFunction) {
            this.actual = actual;
            this.sources = sources;
            this.biFunction = biFunction;
            this.observers = new ZipObserver[sources.length];
            this.row = (T[]) new Object[sources.length];
        }


        public void drain() {
            final T[] os = row;
            outer:
            for (; ; ) {
                int length = observers.length;
                for (int i = 0; i < length; i++) {
                    ZipObserver<T, R> zipObserver = observers[i];
                    Queue<T> queue = zipObserver.queue;
                    if (queue.isEmpty()) {
                        if (observers[i].done) {
                            actual.onComplete();
                        }
                        break outer;
                    }
                    if (i == 1) {
                        os[0] = observers[0].queue.poll();
                        os[1] = observers[1].queue.poll();
                        if (null != os[0] && null != os[1]) {
                            try {
                                R result = biFunction.apply(os[0],os[1]);
                                actual.onNext(result);
                                Arrays.fill(os, null);
                            } catch (Exception e) {
                                e.printStackTrace();
                                actual.onError(e);
                            }
                        }
                    }
                }
            }
        }

    }

    static final class ZipObserver<T,R> implements  Observer<T>{
         ZipCoordinator<T, R> parent;
        final Queue<T> queue = new LinkedBlockingQueue<>();
        volatile boolean done;

        ZipObserver(ZipCoordinator<T, R> parent) {
            this.parent = parent;
        }
        @Override
        public void onSubscribe() {

        }

        @Override
        public void onNext(T value) {
            queue.offer(value);
            parent.drain();
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {
            done = true;
            parent.drain();
        }
    }
}
