package com.drp.network;

/**
 * @author durui
 * @date 2021/3/29
 * @description 满足依赖倒置原则
 */
public interface IAppInfo {
    boolean isDebug();

    String getVersionName();

    int getVersionCode();
}
