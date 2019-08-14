package com.example.android_girls;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public class GirlsAdapter extends RecyclerView.Adapter<GirlsViewHolder> {
    private Context mContext;
    private List<GirlBean> mGirlList;
    private LayoutInflater mLayoutInflater;
    private OnItemActionListener mOnItemActionListener;

    public GirlsAdapter(Context mContext, List<GirlBean> mGirlList) {
        this.mContext = mContext;
        this.mGirlList = mGirlList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public GirlsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.adapter_item_girl, parent, false);
        GirlsViewHolder girlsViewHolder = new GirlsViewHolder(v);
        return girlsViewHolder;
    }

    @Override
    public void onBindViewHolder(final GirlsViewHolder viewHolder, int position) {
        viewHolder.textView.setText(mGirlList.get(position).getTitle());

        int resID = mContext.getResources().getIdentifier("img_" + position,
                "drawable", mContext.getPackageName());
        if(resID != 0) {
            viewHolder.imageView.setImageResource(resID);
        }

        if(mOnItemActionListener != null) {
            viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemActionListener.onItemClickListener(v, viewHolder.getPosition());
                }
            });

            viewHolder.imageView.setOnLongClickListener(new View.OnLongClickListener(){

                @Override
                public boolean onLongClick(View v) {
                    return mOnItemActionListener.onItemLongClickListener(v, viewHolder.getPosition());
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mGirlList.size();
    }

    public interface OnItemActionListener {
        void onItemClickListener(View v,int pos);
        boolean onItemLongClickListener(View v,int pos);
    }

    public void setOnItemActionListener(OnItemActionListener onItemActionListener) {
        this.mOnItemActionListener = onItemActionListener;
    }
}