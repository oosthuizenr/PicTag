package com.beansontoast.pictag.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.beansontoast.pictag.R;

/**
 * Created by renier on 9/21/2016.
 */

public class GalleryAdapter {

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImg;

        public MyViewHolder(View itemView) {
            super(itemView);

            mImg = (ImageView) mImg.findViewById(R.id.item_img);
        }

        public void bind() {

        }
    }
}
