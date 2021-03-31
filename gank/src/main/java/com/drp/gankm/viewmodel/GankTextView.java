package com.drp.gankm.viewmodel;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.databinding.DataBindingUtil;

import com.drp.gankm.R;
import com.drp.gankm.base.ICustomView;
import com.drp.gankm.databinding.GankTextViewBinding;

/**
 * @author durui
 * @date 2021/3/31
 * @description
 */
public class GankTextView extends LinearLayout implements ICustomView<GankTextViewModel> {

    private GankTextViewBinding mBinding;
    private GankTextViewModel mViewModel;

    public GankTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.gank_text_view, this, false);
        mBinding.getRoot().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        addView(mBinding.getRoot());
    }

    @Override
    public void setData(GankTextViewModel data) {
        mBinding.setViewModel(data);
        mBinding.executePendingBindings();
        mViewModel = data;
    }
}