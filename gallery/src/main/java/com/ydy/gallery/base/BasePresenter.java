package com.ydy.gallery.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author ydy
 */
public abstract class BasePresenter<V extends BaseView> {

    public V view;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    protected void start() {

    }

    void attachModelView(V v) {
        this.view = v;
        start();
    }

    void detachView() {
        this.view = null;
        if (compositeDisposable != null && compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    public Disposable add(Disposable disposable) {
        compositeDisposable.add(disposable);
        return disposable;
    }

}