package com.haifeng.demo.lib;


public interface ObservableOnSubscribe<T> {
    void subscribe(ObservableCreate.Emitter<T> emitter) throws Exception;
}
