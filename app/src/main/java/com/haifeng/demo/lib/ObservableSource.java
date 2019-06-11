package com.haifeng.demo.lib;


public interface ObservableSource<T> {
    void subscribe(Observer<? super T> observer);
}
