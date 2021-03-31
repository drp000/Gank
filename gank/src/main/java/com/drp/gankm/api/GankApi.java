package com.drp.gankm.api;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

import com.drp.gankm.beans.GankData;
/**
 * @author durui
 * @date 2021/3/29
 * @description
 */
public interface GankApi {
    @GET("data/{category}/{size}/{page}")
    Observable<GankData> getGankListByCategory(@Path("category") String category, @Path("page") int page, @Path("size") int size);
}
