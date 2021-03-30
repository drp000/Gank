package com.drp.network.observer;


import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * @author durui
 * @date 2021/3/30
 * @description
 */
public abstract class BaseObserver<T> implements Observer<T> {

    private static final String TAG = BaseObserver.class.getSimpleName();

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        Log.i(TAG, "onSubscribe");
    }

    @Override
    public void onNext(@NonNull T t) {
        onSuccess(t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        onFailure(e);
    }

    @Override
    public void onComplete() {
        Log.i(TAG, "onComplete");
    }

    public abstract void onSuccess(T t);

    public abstract void onFailure(Throwable e);

}