package com.drp.gankm.mvvm.category;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.drp.gankm.R;
import com.drp.gankm.mvvm.category.CategoryViewModel;
import com.drp.gankm.mvvm.content.ContentFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author durui
 * @date 2021/4/1
 * @description
 */
public class CategoryViewPagerAdapter extends FragmentPagerAdapter {

    private List<ContentFragment> mFragments = new ArrayList<>();
    private List<CategoryViewModel> mModelList = new ArrayList<>();

    /*private String[] categorys = new String[]{"全部", "Android", "iOS", "前端", "休息视频",
            "福利", "拓展资源", "瞎推荐", "App"};

    private int[] drawableIds = new int[]{R.mipmap.icon_all, R.mipmap.icon_android, R.mipmap.icon_ios,
            R.mipmap.icon_web, R.mipmap.icon_video, R.mipmap.icon_weal, R.mipmap.icon_expand,
            R.mipmap.icon_recommend, R.mipmap.icon_app};*/


    public CategoryViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mModelList.add(new CategoryViewModel("全部", R.mipmap.icon_all));
        mModelList.add(new CategoryViewModel("Android", R.mipmap.icon_android));
        mModelList.add(new CategoryViewModel("iOS", R.mipmap.icon_ios));
        mModelList.add(new CategoryViewModel("前端", R.mipmap.icon_web));
        mModelList.add(new CategoryViewModel("休息视频", R.mipmap.icon_video));
        mModelList.add(new CategoryViewModel("福利", R.mipmap.icon_weal));
        mModelList.add(new CategoryViewModel("拓展资源", R.mipmap.icon_expand));
        mModelList.add(new CategoryViewModel("瞎推荐", R.mipmap.icon_recommend));
        mModelList.add(new CategoryViewModel("App", R.mipmap.icon_app));

        for (int i = 0; i < mModelList.size(); i++) {
            if (i == 0) {
                mFragments.add(ContentFragment.getInstance("all"));
            } else {
                mFragments.add(ContentFragment.getInstance(mModelList.get(i).name));
            }
        }
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mModelList.get(position).name;
    }

    public CategoryViewModel getCategoryViewModel(int position) {
        return mModelList.get(position);
    }
}