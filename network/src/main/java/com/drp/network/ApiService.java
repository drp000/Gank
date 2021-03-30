package com.drp.network;


import android.util.Log;

import com.drp.network.beans.GankData;
import com.drp.network.interceptor.CommonRequestInterceptor;
import com.drp.network.interceptor.CommonResponseInterceptor;
import com.drp.network.observer.BaseObserver;
import com.google.gson.Gson;

import io.reactivex.Observable;
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

    public static void init(IAppInfo info) {
        appInfo = info;
    }

    public static void getGankListByCategory(String category, int page, int size) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .client(getOkhttp())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Observable<GankData> gankList = api.getGankListByCategory(category, page, size);
        gankList.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<GankData>() {

                    @Override
                    public void onSuccess(GankData gankData) {
                        Log.i(TAG, "onNext:" + new Gson().toJson(gankData));
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        Log.i(TAG, "onError:" + e.getMessage());
                    }
                });
    }

    private static OkHttpClient getOkhttp() {
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
}