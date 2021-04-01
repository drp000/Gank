package com.drp.base;

/**
 * @author durui
 * @date 2021/4/1
 * @description
 */
public interface IBaseModelListener<T> {

    void onLoadSuccess(T data, PageResult... pageResults);

    void onLoadFailure(String error, PageResult... pageResults);

}
