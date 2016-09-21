package com.beansontoast.pictag.helpers;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.beansontoast.pictag.listeners.RecyclerViewOnClickListenerInternal;


/**
 * Created by renier on 3/3/2016.
 */
public class ClickableViewHolder extends RecyclerView.ViewHolder {
    private RecyclerViewOnClickListenerInternal mListener;

    public ClickableViewHolder(View v, RecyclerViewOnClickListenerInternal listener) {
        super(v);

        mListener = listener;

        v.setOnClickListener(v1 -> {
            if (mListener != null) {
                mListener.onItemClick(getAdapterPosition());
            }
        });
    }
}
