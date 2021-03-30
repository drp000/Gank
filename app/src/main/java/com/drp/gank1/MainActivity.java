package com.drp.gank1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.drp.gank1.api.GankApi;
import com.drp.gank1.beans.GankData;
import com.drp.gank1.databinding.ActivityMainBinding;
import com.drp.network.GankApiService;
import com.drp.network.environment.EnvironmentActivity;
import com.drp.network.observer.BaseObserver;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setPresenter(this);
        binding.setResult("");
        binding.toolbar.setTitle("");
        setSupportActionBar(binding.toolbar);
        binding.tvTitle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startEnvironmentSetting(v);
                return true;
            }
        });
    }

    @SuppressLint("CheckResult")
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
    }

    public void startEnvironmentSetting(View view) {
        Intent intent = new Intent(this, EnvironmentActivity.class);
        startActivity(intent);
    }
}