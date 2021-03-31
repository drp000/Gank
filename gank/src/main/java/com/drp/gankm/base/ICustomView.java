package com.drp.gankm.base;

/**
 * @author durui
 * @date 2021/3/31
 * @description
 */
public interface ICustomView<S extends BaseCustomViewModel> {

    void setData(S data);

}
