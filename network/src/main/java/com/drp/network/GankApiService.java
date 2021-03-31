package com.drp.network;


import com.drp.network.base.BaseApiService;
import com.drp.network.exception.ExceptionHandle;

import java.io.IOException;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author durui
 * @date 2021/3/30
 * @description
 */
public class GankApiService extends BaseApiService {

    @Override
    public String getTest() {
        return Constants.BASE_URL;
    }

    @Override
    public String getRelease() {
        return Constants.BASE_URL;
    }

    private static class Holder {
        private static final GankApiService instance = new GankApiService();
    }

    private GankApiService() {
        super();
    }

    public static GankApiService getInstance() {
        return Holder.instance;
    }

    public static <T> T getService(Class<T> service) {
        return getInstance().getRetrofit(service).create(service);
    }

    @Override
    protected <T> Function<T, T> getAppErrorHandler() {
        return new Function<T, T>() {
            @Override
            public T apply(@NonNull T response) throws Exception {
                if (response instanceof GankBaseResponse && ((GankBaseResponse) response).statusCode != 0) {
                    ExceptionHandle.ServerException serverException = new ExceptionHandle.ServerException();
                    serverException.code = ((GankBaseResponse) response).statusCode;
                    serverException.message = ((GankBaseResponse) response).errorMsg != null ? ((GankBaseResponse) response).errorMsg : "";
                    throw serverException;
                }
                return response;
            }
        };
    }

    @Override
    protected Interceptor getInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                builder.addHeader("Source", "source");

                return chain.proceed(builder.build());
            }
        };
    }
}