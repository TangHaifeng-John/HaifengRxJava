package com.haifeng.demo.lib;

public class ObservableMap<T, R> extends Observable<R> {


    Function<? super T, ? extends R> function;

    ObservableSource<T> observableSource;


    public ObservableMap(ObservableSource<T> observableSource, Function<? super T, ? extends R> function) {
        this.function = function;
        this.observableSource = observableSource;
    }

    @Override
    protected void subscribeActual(Observer<? super R> observer) {

        MapObserver<T, R> mapObserver = new MapObserver<>(observer, function);
        observableSource.subscribe(mapObserver);
    }


    class MapObserver<T, R> implements Observer<T> {

        Observer<? super R> observer;

        Function<? super T, ? extends R> function;

        public MapObserver(Observer<? super R> observer, Function<? super T, ? extends R> function) {
            this.observer = observer;
            this.function = function;
        }

        @Override
        public void onSubscribe() {

        }

        @Override
        public void onNext(T value) {
            R t = function.apply(value);
            observer.onNext(t);
        }


        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }
}
