package com.drp.gankm.base;


import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author durui
 * @date 2021/3/31
 * @description
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    private ICustomView view;

    public BaseViewHolder(ICustomView view) {
        super((View) view);
        this.view = view;
    }

    public void bind(BaseCustomViewModel item) {
        view.setData(item);
    }
}