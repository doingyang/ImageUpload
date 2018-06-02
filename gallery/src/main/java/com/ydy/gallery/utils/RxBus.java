package com.ydy.gallery.utils;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * @author ydy
 */
public class RxBus {

    private static final RxBus BUS = new RxBus();
    private final PublishSubject<Object> bus = PublishSubject.create();

    public static RxBus singleton() {
        return BUS;
    }

    public void post(Object o) {
        bus.onNext(o);
    }

    public <T> Observable<T> toObservable(Class<T> eventType) {
        return bus.ofType(eventType);
    }
}
