package com.drp.network;

import com.drp.network.beans.GankData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author durui
 * @date 2021/3/29
 * @description
 */
public interface Api {
    @GET("data/{category}/{size}/{page}")
    Call<GankData> getGankListByCategory(@Path("category") String category, @Path("page") int page, @Path("size") int size);
}
