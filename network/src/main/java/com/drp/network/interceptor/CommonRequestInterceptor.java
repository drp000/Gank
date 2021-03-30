package com.drp.network.interceptor;


import com.drp.network.IAppInfo;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author durui
 * @date 2021/3/29
 * @description 通用请求拦截器，可以添加一些通用的请求头和请求参数
 */
public class CommonRequestInterceptor implements Interceptor {
    private final IAppInfo appInfo;

    public CommonRequestInterceptor(IAppInfo info) {
        this.appInfo = info;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("os", "android")
                .addHeader("appVersion", String.valueOf(appInfo.getVersionCode()));
        return chain.proceed(builder.build());
    }
}