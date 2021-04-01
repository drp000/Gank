package com.drp.gank1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.drp.gank1.databinding.ActivityMainBinding;
import com.drp.gankm.mvvm.category.CategoryFragment;
import com.drp.gankm.mvvm.mine.MineFragment;
import com.drp.network.environment.EnvironmentActivity;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding mBinding;
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setPresenter(this);
        mBinding.toolbar.setTitle("");
        setSupportActionBar(mBinding.toolbar);
        mBinding.toolbar.setVisibility(View.GONE);
        mBinding.tvTitle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startEnvironmentSetting(v);
                return true;
            }
        });

        mFragment = CategoryFragment.getInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_main, mFragment, CategoryFragment.class.getSimpleName())
                .addToBackStack(null)
                .commit();
        mBinding.tabMain.addTab(mBinding.tabMain.newTab().setText("Gank"));
        mBinding.tabMain.addTab(mBinding.tabMain.newTab().setText("我的"));
        LinearLayout linearLayout = (LinearLayout) mBinding.tabMain.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this, R.drawable.divider_vertical)); //设置分割线的drawable
        linearLayout.setDividerPadding(9);   //该方法传入的参数为像素的大小，故需要使用dp转px的方法

        mBinding.tabMain.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {
                    if (mFragment instanceof MineFragment) {
                        mFragment = CategoryFragment.getInstance();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fl_main, mFragment, CategoryFragment.class.getSimpleName())
                                .addToBackStack(null)
                                .commit();
                    }
                } else {
                    if (mFragment instanceof CategoryFragment) {
                        mFragment = MineFragment.getInstance();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fl_main, mFragment, MineFragment.class.getSimpleName())
                                .addToBackStack(null)
                                .commit();
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {
                    if (mFragment instanceof MineFragment) {
                        mFragment = CategoryFragment.getInstance();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fl_main, mFragment, CategoryFragment.class.getSimpleName())
                                .addToBackStack(null)
                                .commit();
                    }
                } else {
                    if (mFragment instanceof CategoryFragment) {
                        mFragment = MineFragment.getInstance();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fl_main, mFragment, CategoryFragment.class.getSimpleName())
                                .addToBackStack(null)
                                .commit();
                    }
                }
            }
        });
    }

/*    @SuppressLint("CheckResult")
    public void getGankData(View view) {
        GankApiService.getService(GankApi.class)
                .getGankListByCategory("all", 1, 10)
                .compose(GankApiService.getInstance().applySchedulers(new BaseObserver<GankData>() {
                    @Override
                    public void onSuccess(GankData gankData) {
                        Log.i(TAG, new Gson().toJson(gankData));
                        binding.setResult(new Gson().toJson(gankData));
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        Log.i(TAG, e.getMessage());
                        binding.setResult(e.getMessage());
                    }
                }));
    }*/

    public void startEnvironmentSetting(View view) {
        Intent intent = new Intent(this, EnvironmentActivity.class);
        startActivity(intent);
    }
}