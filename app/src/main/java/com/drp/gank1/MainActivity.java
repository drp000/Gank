package com.drp.gank1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.drp.gank1.api.GankApi;
import com.drp.gank1.beans.GankData;
import com.drp.gank1.databinding.ActivityMainBinding;
import com.drp.network.ApiService;
import com.drp.network.observer.BaseObserver;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setPresenter(this);
    }

    @SuppressLint("CheckResult")
    public void getGankData(View view) {
        ApiService.getService(GankApi.class)
                .getGankListByCategory("all", 1, 10)
                .compose(ApiService.applySchedulers(new BaseObserver<GankData>() {
                    @Override
                    public void onSuccess(GankData gankData) {
                        Log.i(TAG, new Gson().toJson(gankData));
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        Log.i(TAG, e.getMessage());
                    }
                }));
    }
}