package com.beansontoast.pictag.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.beansontoast.pictag.R;
import com.beansontoast.pictag.helpers.ClickableViewHolder;
import com.beansontoast.pictag.listeners.RecyclerViewOnClickListener;
import com.beansontoast.pictag.listeners.RecyclerViewOnClickListenerInternal;
import com.beansontoast.pictag.model.MediaStoreData;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by renier on 9/21/2016.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder> {

    private List<MediaStoreData> mData;
    private RecyclerViewOnClickListener<MediaStoreData> mListener;

    public GalleryAdapter() {
        this.mData = new ArrayList<>();
    }

    public void setOnClickListener(RecyclerViewOnClickListener<MediaStoreData> listener) {
        this.mListener = listener;
    }

    public void setData(List<MediaStoreData> data) {
        this.mData = data;
    }

    public void clearData() {
        if (mData != null)
            mData.clear();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return
                new MyViewHolder(
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.gallery_list_item, parent, false),
                        position -> {
                            try {
                                if (mListener != null) {
                                    mListener.onItemClick(mData.get((int)position));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends ClickableViewHolder {
        private ImageView mImg;

        public MyViewHolder(View itemView, RecyclerViewOnClickListenerInternal listener) {
            super(itemView, listener);

            mImg = (ImageView) itemView.findViewById(R.id.item_img);
        }

        public void bind(MediaStoreData item) {
            Glide.with(mImg.getContext()).load(item.getUri())
                    .thumbnail(0.5f)
                    .override(200,200)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(mImg);
        }
    }
}
