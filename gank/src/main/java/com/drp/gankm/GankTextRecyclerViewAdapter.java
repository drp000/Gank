package com.drp.gankm;


import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.drp.gankm.base.BaseCustomViewModel;
import com.drp.gankm.base.BaseViewHolder;
import com.drp.gankm.viewmodel.GankTextPicView;
import com.drp.gankm.viewmodel.GankTextView;
import com.drp.gankm.viewmodel.GankTextViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author durui
 * @date 2021/3/31
 * @description
 */
public class GankTextRecyclerViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int TYPE_TEXT = 1;
    private static final int TYPE_PICTURE = 2;
    private List<BaseCustomViewModel> mItems = new ArrayList<>();

    public void setData(List<BaseCustomViewModel> items) {
        this.mItems = items;
        notifyDataSetChanged();
    }

    public void add(BaseCustomViewModel item) {
        this.mItems.add(item);
        notifyDataSetChanged();
    }

    public void addAll(List<BaseCustomViewModel> items) {
        this.mItems.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mItems.get(position) instanceof GankTextViewModel) {
            return TYPE_TEXT;
        } else {
            return TYPE_PICTURE;
        }
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_TEXT) {
            GankTextView gankTextView = new GankTextView(parent.getContext());
            return new BaseViewHolder(gankTextView);
        } else if (viewType == TYPE_PICTURE) {
            GankTextPicView gankTextPicView = new GankTextPicView(parent.getContext());
            return new BaseViewHolder(gankTextPicView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.bind(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        if (mItems != null && mItems.size() > 0) {
            return mItems.size();
        }
        return 0;
    }
}