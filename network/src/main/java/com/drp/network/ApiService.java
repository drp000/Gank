package com.drp.network;


import com.drp.network.interceptor.CommonRequestInterceptor;
import com.drp.network.interceptor.CommonResponseInterceptor;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author durui
 * @date 2021/3/29
 * @description
 */
public class ApiService {
    private static final String TAG = ApiService.class.getSimpleName();
    private static IAppInfo appInfo;
    private static Map<String, Retrofit> retrofitMap = new HashMap<>();

    public static void init(IAppInfo info) {
        appInfo = info;
    }

    private static Retrofit getRetrofit(Class service) {
        if (retrofitMap.get(Constants.BASE_URL + service.getName()) != null) {
            return (retrofitMap.get(Constants.BASE_URL + service.getName()));
        }
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        retrofitMap.put(Constants.BASE_URL + service.getName(), retrofit);
        return retrofit;
    }

    public static <T> T getService(Class<T> service) {
        return getRetrofit(service).create(service);
    }

    /*public static void getGankListByCategory(String category, int page, int size, Observer observer) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .client(getOkhttp())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        api.getGankListByCategory(category, page, size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }*/

    private static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new CommonRequestInterceptor(appInfo));
        builder.addInterceptor(new CommonResponseInterceptor());

        if (appInfo != null && appInfo.isDebug()) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);
        }
        return builder.build();
    }

    public static <T> ObservableTransformer<T, T> applySchedulers(final Observer<T> observer) {
        return (upstream) -> {
            Observable<T> observable = upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            observable.subscribe(observer);
            return observable;
        };
    }
}