package com.drp.gankm.mvvm.content;


import android.annotation.SuppressLint;

import com.drp.base.customview.BaseCustomViewModel;
import com.drp.base.model.MvvmBaseModel;
import com.drp.common.views.GankTextPicViewModel;
import com.drp.common.views.GankTextViewModel;
import com.drp.gankm.R;
import com.drp.gankm.api.GankApi;
import com.drp.gankm.beans.GankData;
import com.drp.gankm.beans.GankItem;
import com.drp.network.GankApiService;
import com.drp.network.observer.BaseNetObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * @author durui
 * @date 2021/4/1
 * @description 需要考虑分页，缓存，预置
 */
public class ContentModel extends MvvmBaseModel<GankData, List<BaseCustomViewModel>> {
    private static final String TAG = ContentModel.class.getSimpleName();
    private final String mCategory;
    private final int mSize;

    public ContentModel(String category, int size) {
        super(GankData.class, true, "cache_" + category, null, 1);
        this.mCategory = category;
        this.mSize = size;
    }

    @SuppressLint("CheckResult")
    protected void load() {
        GankApiService.getService(GankApi.class)
                .getGankListByCategory(mCategory, mPageNumber, mSize)
                .compose(GankApiService.getInstance()
                        .applySchedulers(new BaseNetObserver<GankData>(this, this)));

    }

    @Override
    public void onSuccess(GankData gankData, boolean isFromCache) {
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
        notifyResultToListeners(gankData, baseCustomViewModels, isFromCache);
    }

    @Override
    public void onFailure(Throwable e) {
        e.printStackTrace();
        loadFail(e.getMessage());
    }
}