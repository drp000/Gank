package com.drp.network.environment;

/**
 * @author durui
 * @date 2021/3/30
 * @description
 */
public interface IEnvironment {
    /**
     * 获取测试环境地址
     *
     * @return
     */
    String getTest();

    /**
     * 获取正式环境地址
     *
     * @return
     */
    String getRelease();
}
