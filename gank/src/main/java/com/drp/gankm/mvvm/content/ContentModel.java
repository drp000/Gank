package com.drp.gankm.mvvm.content;


import android.annotation.SuppressLint;
import android.util.Log;

import com.drp.base.IBaseModelListener;
import com.drp.base.PageResult;
import com.drp.base.customview.BaseCustomViewModel;
import com.drp.common.views.GankTextPicViewModel;
import com.drp.common.views.GankTextViewModel;
import com.drp.gankm.R;
import com.drp.gankm.api.GankApi;
import com.drp.gankm.beans.GankData;
import com.drp.gankm.beans.GankItem;
import com.drp.network.GankApiService;
import com.drp.network.observer.BaseObserver;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * @author durui
 * @date 2021/4/1
 * @description
 */
public class ContentModel {
    private static final String TAG = ContentModel.class.getSimpleName();
    private IBaseModelListener<List<BaseCustomViewModel>> mListener;
    private String mCategory;
    private int mPage;
    private int mSize;
    private boolean isRefresh;

    public ContentModel(String category, int size, IBaseModelListener<List<BaseCustomViewModel>> listener) {
        this.mCategory = category;
        this.mListener = listener;
        this.mSize = size;
        mPage = 1;
    }

    public void refresh() {
        isRefresh = true;
        mPage = 1;
        load();
    }


    public void loadMore() {
        isRefresh = false;
        mPage++;
        load();
    }


    @SuppressLint("CheckResult")
    private void load() {
        GankApiService.getService(GankApi.class)
                .getGankListByCategory(mCategory, mPage, mSize)
                .compose(GankApiService.getInstance().applySchedulers(new BaseObserver<GankData>() {
                    @Override
                    public void onSuccess(GankData gankData) {
                        Log.i(TAG, new Gson().toJson(gankData));
                        List<BaseCustomViewModel> baseCustomViewModels = new ArrayList<>();
                        for (GankItem result : gankData.results) {
                            BaseCustomViewModel viewModel;
                            if (result.images != null && result.images.size() > 0) {
                                viewModel = new GankTextPicViewModel(result.desc, result.url, result.images.get(0), R.mipmap.icon_weal);
                            } else {
                                viewModel = new GankTextViewModel(result.desc, result.url);
                            }
                            baseCustomViewModels.add(viewModel);
                        }
                        mListener.onLoadSuccess(baseCustomViewModels,
                                new PageResult(baseCustomViewModels.isEmpty(), isRefresh, baseCustomViewModels.size() >= mSize));
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        e.printStackTrace();
                        mListener.onLoadFailure(e.getMessage());
                    }
                }));

    }
}