package com.drp.gankm.mvvm.content;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.drp.base.IBaseModelListener;
import com.drp.base.PageResult;
import com.drp.base.customview.BaseCustomViewModel;
import com.drp.common.views.LoadMoreViewModel;
import com.drp.gankm.R;
import com.drp.gankm.databinding.FragmentContentBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * @author durui
 * @date 2021/3/31
 * @description
 */
public class ContentFragment extends Fragment implements IBaseModelListener<List<BaseCustomViewModel>> {

    private static final String TAG = ContentFragment.class.getSimpleName();
    private static final String KEY_CATEGORY = "category";
    private static final int VISIBLE_THRESHOLE = 3;

    private FragmentContentBinding mBinding;
    private ContentTextRecyclerViewAdapter mRecyclerViewAdapter;
    private List<BaseCustomViewModel> mContentList = new ArrayList<>();

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
        mRecyclerViewAdapter = new ContentTextRecyclerViewAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mBinding.rvContent.setLayoutManager(layoutManager);
        mBinding.rvContent.setAdapter(mRecyclerViewAdapter);
        mBinding.srlContent.setRefreshing(true);
        String category = getArguments().getString(KEY_CATEGORY);

        ContentModel contentModel = new ContentModel(category, 10, this);

        //下拉刷新
        mBinding.srlContent.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                contentModel.refresh();
            }
        });
        //上拉加载更多
        mBinding.rvContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int itemCount = layoutManager.getItemCount();
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                if (!mBinding.srlContent.isRefreshing() && itemCount <= lastVisibleItemPosition + VISIBLE_THRESHOLE) {
                    contentModel.loadMore();
                }
            }
        });
        //首次加载数据
        mBinding.srlContent.setRefreshing(true);
        contentModel.refresh();
    }

    @Override
    public void onLoadSuccess(List<BaseCustomViewModel> data, PageResult... pageResults) {
        if (pageResults[0].isFirstPage()) {
            //第一页，意味着是刷新获取到的数据
            mContentList.clear();
            mBinding.srlContent.setRefreshing(false);
        } else {
            //加载更多完成后需要移除加载更多的View
            mRecyclerViewAdapter.removeLoadMore();
        }
        mContentList.addAll(data);
        mRecyclerViewAdapter.setData(mContentList);
        if (pageResults[0].isHasNextPage()) {
            //有更多的数据，需要展示加载更多的View
            mRecyclerViewAdapter.add(new LoadMoreViewModel());
        }
    }

    @Override
    public void onLoadFailure(String error, PageResult... pageResults) {
        Toast.makeText(getContext(), "数据加载失败：" + error, Toast.LENGTH_SHORT).show();
        mBinding.srlContent.setRefreshing(false);
    }
}