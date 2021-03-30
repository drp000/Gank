package com.drp.network;


import android.util.Log;

import com.drp.network.beans.GankData;
import com.drp.network.interceptor.CommonRequestInterceptor;
import com.drp.network.interceptor.CommonResponseInterceptor;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
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
                .build();

        Api api = retrofit.create(Api.class);
        Call<GankData> gankList = api.getGankListByCategory(category, page, size);
        gankList.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.i(TAG, new Gson().toJson(response.body()));

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e(TAG, t.getMessage());
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