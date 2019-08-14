package com.example.android_girls;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

class GirlsViewHolder extends RecyclerView.ViewHolder {
    public TextView textView;
    public ImageView imageView;
    public GirlsViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.girl_title);
        imageView = itemView.findViewById(R.id.girl_image);
    }
}