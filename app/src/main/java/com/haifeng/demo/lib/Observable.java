package com.haifeng.demo.lib;



public abstract class Observable<T> implements ObservableSource<T> {
    @Override
    public void subscribe(Observer<? super T> observer) {
        subscribeActual(observer);
    }

    protected abstract void subscribeActual(Observer<? super T> observer);

    public static <T> Observable<T> create(ObservableOnSubscribe<T> observableCreate) {
        return new ObservableCreate(observableCreate);
    }


    public final Observable<T> subscribeOn(Scheduler scheduler) {
        return new ObservableSubscribeOn<T>(this, scheduler);
    }

    public final <R> Observable<R> map(Function<? super T, ? extends R> function) {
        return new ObservableMap<T, R>(this, function);
    }


    public final Observable<T> observerOn(Scheduler scheduler) {
        return new ObservableObserverOn<T>(this, scheduler);
    }




}
