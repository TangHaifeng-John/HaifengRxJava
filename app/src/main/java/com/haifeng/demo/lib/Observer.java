package com.haifeng.demo.lib;

public interface Observer<T> {

    void onSubscribe();

    void onNext(T value);

    void onError(Throwable e);

    void onComplete();
}
