package com.drp.network;


import com.drp.network.exception.ExceptionHandle;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * @author durui
 * @date 2021/3/30
 * @description
 */
public class HttpErrorHandler<T> implements Function<Throwable, Observable<T>> {

    @Override
    public Observable<T> apply(@NonNull Throwable throwable) throws Exception {
        return Observable.error(ExceptionHandle.handleException(throwable));
    }
}