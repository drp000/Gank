package com.drp.gank1;


import android.app.Application;

import com.drp.network.ApiService;
import com.drp.network.IAppInfo;

/**
 * @author durui
 * @date 2021/3/29
 * @description
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ApiService.init(new IAppInfo() {
            @Override
            public boolean isDebug() {
                return BuildConfig.DEBUG;
            }

            @Override
            public String getVersionName() {
                return BuildConfig.VERSION_NAME;
            }

            @Override
            public int getVersionCode() {
                return BuildConfig.VERSION_CODE;
            }
        });
    }
}