package com.drp.gankm.mvvm.category;


import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

/**
 * @author durui
 * @date 2021/3/31
 * @description
 */
public class CategoryViewModel extends BaseObservable {
    @Bindable
    public String name;
    @Bindable
    public int icon;

    public CategoryViewModel(String name, int icon) {
        this.name = name;
        this.icon = icon;
        notifyChange();
    }

    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, int resId) {
        view.setImageResource(resId);
    }
}