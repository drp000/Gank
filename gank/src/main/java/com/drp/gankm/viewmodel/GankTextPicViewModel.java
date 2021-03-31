package com.drp.gankm.viewmodel;


import com.drp.gankm.base.BaseCustomViewModel;

/**
 * @author durui
 * @date 2021/3/31
 * @description
 */
public class GankTextPicViewModel extends BaseCustomViewModel {

    public String title;
    public String url;
    public String imgUrl;

    public GankTextPicViewModel(String title, String url, String imgUrl) {
        this.title = title;
        this.url = url;
        this.imgUrl = imgUrl;
    }

}