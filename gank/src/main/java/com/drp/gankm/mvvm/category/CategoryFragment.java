package com.drp.gankm.mvvm.category;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.drp.gankm.R;
import com.drp.gankm.databinding.FragmentCategoryBinding;
import com.drp.gankm.databinding.TabCategoryBinding;


/**
 * @author durui
 * @date 2021/3/31
 * @description
 */
public class CategoryFragment extends Fragment {

    private FragmentCategoryBinding mBinding;

    public static CategoryFragment getInstance() {
        return new CategoryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        CategoryViewPagerAdapter viewPagerAdapter = new CategoryViewPagerAdapter(getChildFragmentManager());
        mBinding.vpGank.setAdapter(viewPagerAdapter);
        mBinding.tbCategory.setupWithViewPager(mBinding.vpGank);
        mBinding.vpGank.setOffscreenPageLimit(1);

        int tabCount = mBinding.tbCategory.getTabCount();
        for (int i = 0; i < tabCount; i++) {
            TabCategoryBinding binding = DataBindingUtil.inflate(inflater, R.layout.tab_category, null, false);
            mBinding.tbCategory.getTabAt(i).setCustomView(binding.getRoot());
            binding.setViewModel(viewPagerAdapter.getCategoryViewModel(i));
        }
    }
}