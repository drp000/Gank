package com.drp.network.interceptor;


import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @author durui
 * @date 2021/3/29
 * @description 通用响应拦截器
 */
public class CommonResponseInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        long startTime = System.currentTimeMillis();
        Response response = chain.proceed(chain.request());
        Log.i("ApiService", "requestTime=" + (System.currentTimeMillis() - startTime));
        return response;
    }
}