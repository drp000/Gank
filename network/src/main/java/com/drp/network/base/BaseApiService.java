package com.drp.network.base;


import com.drp.network.HttpErrorHandler;
import com.drp.network.IAppInfo;
import com.drp.network.environment.EnvironmentActivity;
import com.drp.network.environment.IEnvironment;
import com.drp.network.interceptor.CommonRequestInterceptor;
import com.drp.network.interceptor.CommonResponseInterceptor;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author durui
 * @date 2021/3/30
 * @description
 */
public abstract class BaseApiService implements IEnvironment {
    private static IAppInfo appInfo;
    private static Map<String, Retrofit> retrofitMap = new HashMap<>();
    private static boolean mIsTest;
    private String mBaseUrl;
    private OkHttpClient mOkHttpClient;

    protected BaseApiService() {
        if (mIsTest) {
            this.mBaseUrl = getTest();
        } else {
            this.mBaseUrl = getRelease();
        }
    }

    public static void init(IAppInfo info) {
        appInfo = info;
        mIsTest = EnvironmentActivity.isTestEnvironment(appInfo.getApplicationContext());
    }

    protected Retrofit getRetrofit(Class service) {
        if (retrofitMap.get(mBaseUrl + service.getName()) != null) {
            return (retrofitMap.get(mBaseUrl + service.getName()));
        }
        Retrofit retrofit = new Retrofit.Builder().baseUrl(mBaseUrl)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        retrofitMap.put(mBaseUrl + service.getName(), retrofit);
        return retrofit;
    }

    private OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            if (getInterceptor() != null) {
                builder.addInterceptor(getInterceptor());
            }
            builder.addInterceptor(new CommonRequestInterceptor(appInfo));
            builder.addInterceptor(new CommonResponseInterceptor());
            if (appInfo != null && appInfo.isDebug()) {
                HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(httpLoggingInterceptor);
            }
            mOkHttpClient = builder.build();
        }
        return mOkHttpClient;
    }

    public <T> ObservableTransformer<T, T> applySchedulers(final Observer<T> observer) {
        return (upstream) -> {
            Observable<T> observable = upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(getAppErrorHandler())
                    .onErrorResumeNext(new HttpErrorHandler());
            observable.subscribe(observer);
            return observable;
        };
    }

    /**
     * 统一处理错误
     *
     * @param <T>
     * @return
     */
    protected abstract <T> Function<T, T> getAppErrorHandler();

    /**
     * 获取自定义拦截器
     *
     * @return
     */
    protected abstract Interceptor getInterceptor();
}