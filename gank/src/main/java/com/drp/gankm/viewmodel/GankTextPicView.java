package com.drp.gankm.viewmodel;


import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.drp.gankm.R;
import com.drp.gankm.base.ICustomView;
import com.drp.gankm.databinding.GankTextPicViewBinding;

/**
 * @author durui
 * @date 2021/3/31
 * @description
 */
public class GankTextPicView extends LinearLayout implements ICustomView<GankTextPicViewModel> {

    private GankTextPicViewBinding mBinding;
    private GankTextPicViewModel mViewModel;
    private Context context;

    public GankTextPicView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.gank_text_pic_view, this, false);
        mBinding.getRoot().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "打开详情：" + mViewModel.url, Toast.LENGTH_SHORT).show();

            }
        });
        addView(mBinding.getRoot());
    }

    @Override
    public void setData(GankTextPicViewModel data) {
        mBinding.setViewModel(data);
        mBinding.executePendingBindings();
        mViewModel = data;
    }

    @BindingAdapter("imgUrl")
    public static void loadImage(ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(imageView.getContext())
                    .load(url)
//                    .transition(w)
                    .into(imageView);
        }
    }
}