package com.haifeng.demo.lib;

public class ObservableObserverOn<T> extends Observable {

    ObservableSource<T> observableSource;

    Scheduler scheduler;


    public ObservableObserverOn(ObservableSource<T> observableSource, Scheduler scheduler) {
        this.observableSource = observableSource;
        this.scheduler = scheduler;
    }

    @Override
    protected void subscribeActual(Observer observer) {
        ObservableOnObserver<T> observableOnObserver = new ObservableOnObserver<>(scheduler, observer);
        observableSource.subscribe(observableOnObserver);
    }

    class ObservableOnObserver<T> implements Observer<T> {
        Scheduler scheduler;
        Observer<T> observer;


        public ObservableOnObserver(Scheduler scheduler, Observer<T> observer) {
            this.scheduler = scheduler;
            this.observer = observer;
        }


        @Override
        public void onSubscribe() {
            scheduler.execute(new Runnable() {
                @Override
                public void run() {
                    observer.onSubscribe();
                }
            });
        }

        @Override
        public void onNext(final T value) {
            scheduler.execute(new Runnable() {
                @Override
                public void run() {
                    observer.onNext(value);
                }
            });
        }

        @Override
        public void onError(final Throwable e) {
            scheduler.execute(new Runnable() {
                @Override
                public void run() {
                    observer.onError(e);
                }
            });
        }

        @Override
        public void onComplete() {
            scheduler.execute(new Runnable() {
                @Override
                public void run() {
                    observer.onComplete();
                }
            });
        }
    }
}
