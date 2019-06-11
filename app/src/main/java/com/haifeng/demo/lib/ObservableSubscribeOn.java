package com.haifeng.demo.lib;

public class ObservableSubscribeOn<T> extends Observable<T> {

    ObservableSource<T> observableSource;

    Scheduler scheduler;

    public ObservableSubscribeOn(ObservableSource<T> observableSource, Scheduler scheduler) {
        this.observableSource = observableSource;
        this.scheduler = scheduler;
    }


    @Override
    protected void subscribeActual(final Observer<? super T> observer) {
            scheduler.execute(new Runnable() {
                @Override
                public void run() {
                    observableSource.subscribe(observer);
                }
            });
    }
}
