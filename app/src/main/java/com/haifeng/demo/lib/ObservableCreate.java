package com.haifeng.demo.lib;

public class ObservableCreate<T> extends Observable<T> {


    ObservableOnSubscribe<T> observableOnSubscribe;

    public ObservableCreate(ObservableOnSubscribe<T> observableOnSubscribe) {
        this.observableOnSubscribe = observableOnSubscribe;
    }


    @Override
    protected void subscribeActual(Observer<? super T> observer) {

        Emitter<T> emitter = new Emitter<T>(observer);
        observer.onSubscribe();
        try {
            observableOnSubscribe.subscribe(emitter);
            emitter.onComplete();
        } catch (Exception e) {
            emitter.error(e);
            e.printStackTrace();
        }
    }





   public static class Emitter<T> {

        Observer<? super T> observer;

        public Emitter(Observer<? super T> observer) {
            this.observer = observer;
        }

        public void next(T t) {
            observer.onNext(t);
        }

        public void error(Throwable throwable) {
            observer.onError(throwable);
        }

        public void onComplete() {
            observer.onComplete();
        }
    }
}
