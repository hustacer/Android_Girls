package com.example.android_girls;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;

import static com.example.android_girls.R.mipmap.ic_launcher;

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
        Log.d("android_gitls:onBindViewHolder", "" + mGirlList.size());

        viewHolder.imageView.setImageResource(ic_launcher);
        viewHolder.imageView.setTag(url);
//        mImageLoader.showImageByAsyncTask(viewHolder.imageView, url);
        Glide.with(mContext)
                .asBitmap()
                .load(url)
//                .into(new TransformationUtils(viewHolder.imageView));
                //.into(viewHolder.imageView);
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        //图片原始宽度
                        int width = resource.getWidth();
                        //图片原始高度
                        int height = resource.getHeight();

                        //获取屏幕的宽度
                        int screenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
                        //获取比例
                        float sy = (float) (screenWidth / 2) / (float) width;

                        //计算图片等比例放大后的高
                        int imageViewHeight = (int) (height * sy);
                        ViewGroup.LayoutParams params = viewHolder.imageView.getLayoutParams();
                        params.height = imageViewHeight;
                        viewHolder.imageView.setLayoutParams(params);
                        viewHolder.imageView.setImageBitmap(resource);
                    }
                });

        viewHolder.textView.setText(mGirlList.get(position).title);
        setItemEvent(viewHolder, position);
    }

    @Override
    public int getItemCount() {
        Log.d("android_gitls:", "" + mGirlList.size());
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