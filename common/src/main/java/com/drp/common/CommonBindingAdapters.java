package com.drp.common;


import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

/**
 * @author durui
 * @date 2021/4/1
 * @description
 */
public class CommonBindingAdapters {
    @BindingAdapter("imgUrl")
    public static void loadImage(ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(imageView.getContext())
                    .load(url)
//                    .transition(w)
                    .into(imageView);
        }
    }

    @BindingAdapter("android:visibility")
    public static void setVisibility(View view, Boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }
}