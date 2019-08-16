package com.example.android_girls;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class GirlsAdapter extends RecyclerView.Adapter<GirlsViewHolder> {
    private Context mContext;
    private List<GirlBean> mGirlList;
    private LayoutInflater mLayoutInflater;
    private OnItemActionListener mOnItemActionListener;
    private ImageLoader mImageLoader;

    public interface OnItemActionListener {
        void onItemClick(View view, int position);
        boolean onItemLongClick(View view, int position);
    }

    public void setOnItemActionListener(OnItemActionListener onItemActionListener) {
        this.mOnItemActionListener = onItemActionListener;
    }

    public GirlsAdapter(Context mContext, List<GirlBean> mGirlList) {
        this.mContext = mContext;
        this.mGirlList = mGirlList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.mImageLoader = new ImageLoader();
    }

    @Override
    public GirlsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.adapter_item_girl, viewGroup, false);
        GirlsViewHolder girlsViewHolder = new GirlsViewHolder(v);
        return girlsViewHolder;
    }

    @Override
    public void onBindViewHolder(final GirlsViewHolder viewHolder, int position) {
        String url = mGirlList.get(position).img_url;

        viewHolder.imageView.setTag(url);
        mImageLoader.showImageByAsyncTask(viewHolder.imageView, url);

        viewHolder.textView.setText(mGirlList.get(position).title);
        setItemEvent(viewHolder, position);
    }

    @Override
    public int getItemCount() {
        return mGirlList.size();
    }

    protected void setItemEvent(final GirlsViewHolder holder, int position) {
        if (mOnItemActionListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int layoutPosition = holder.getLayoutPosition();
                    mOnItemActionListener.onItemClick(holder.itemView, layoutPosition);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int layoutPosition = holder.getLayoutPosition();
                    mOnItemActionListener.onItemLongClick(holder.itemView, layoutPosition);
                    return false;
                }
            });
        }
    }
}

class GirlsViewHolder extends RecyclerView.ViewHolder {
    public TextView textView;
    public ImageView imageView;
    public GirlsViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.girl_title);
        imageView = itemView.findViewById(R.id.girl_image);
    }
}