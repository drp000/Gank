package com.drp.common.views;


import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.drp.base.customview.BaseCustomView;
import com.drp.common.R;
import com.drp.common.databinding.GankTextViewBinding;

/**
 * @author durui
 * @date 2021/3/31
 * @description
 */
public class GankTextView extends BaseCustomView<GankTextViewBinding, GankTextViewModel> {

    public GankTextView(Context context) {
        super(context);
    }

    @Override
    protected void setDataToView(GankTextViewModel viewModel) {
        getDataBinding().setViewModel(viewModel);
    }

    @Override
    protected void onRootViewClicked(View v) {
        Toast.makeText(getContext(), "打开详情页：" + getViewModel().url, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected int getViewLayoutId() {
        return R.layout.gank_text_view;
    }
}