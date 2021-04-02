package com.drp.gank1;


import android.app.Application;

import com.drp.base.IAppInfo;
import com.drp.base.model.BasicDataPreferenceUtil;
import com.drp.network.base.BaseApiService;

/**
 * @author durui
 * @date 2021/3/29
 * @description
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        IAppInfo appInfo = new AppInfo(this);
        BaseApiService.init(appInfo);
        BasicDataPreferenceUtil.init(appInfo);

    }
}