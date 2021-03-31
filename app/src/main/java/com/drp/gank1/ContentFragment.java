package com.drp.gank1;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.drp.gank1.databinding.FragmentContentBinding;
import com.drp.gankm.GankTextRecyclerViewAdapter;
import com.drp.gankm.api.GankApi;
import com.drp.gankm.base.BaseCustomViewModel;
import com.drp.gankm.beans.GankData;
import com.drp.gankm.beans.GankItem;
import com.drp.gankm.viewmodel.GankTextPicViewModel;
import com.drp.gankm.viewmodel.GankTextViewModel;
import com.drp.network.GankApiService;
import com.drp.network.observer.BaseObserver;
import com.google.gson.Gson;

/**
 * @author durui
 * @date 2021/3/31
 * @description
 */
public class ContentFragment extends Fragment {

    private static final String KEY_CATEGORY = "category";
    private static final String TAG = ContentFragment.class.getSimpleName();

    private FragmentContentBinding mBinding;
    private int mPage = 1;

    public static ContentFragment getInstance(String category) {
        ContentFragment fragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_CATEGORY, category);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_content, container, false);
        return mBinding.getRoot();
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        GankTextRecyclerViewAdapter adapter = new GankTextRecyclerViewAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mBinding.rvContent.setLayoutManager(layoutManager);
        mBinding.rvContent.setAdapter(adapter);
        mBinding.srlContent.setRefreshing(true);
        String category = getArguments().getString(KEY_CATEGORY);

        GankApiService.getService(GankApi.class)
                .getGankListByCategory(category, mPage, 10)
                .compose(GankApiService.getInstance().applySchedulers(new BaseObserver<GankData>() {
                    @Override
                    public void onSuccess(GankData gankData) {
                        mBinding.srlContent.setRefreshing(false);
                        Log.i(TAG, new Gson().toJson(gankData));
                        if (gankData != null && gankData.results != null && gankData.results.size() > 0) {
                            for (GankItem result : gankData.results) {
                                BaseCustomViewModel viewModel;
                                if (result.images != null && result.images.size() > 0) {
                                    viewModel = new GankTextPicViewModel(result.desc, result.url, result.images.get(0));
                                } else {
                                    viewModel = new GankTextViewModel(result.desc, result.url);
                                }
                                adapter.add(viewModel);
                            }
                        } else {
                            //TODO 没有数据

                        }
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        Log.i(TAG, e.getMessage());
                    }
                }));

    }
}