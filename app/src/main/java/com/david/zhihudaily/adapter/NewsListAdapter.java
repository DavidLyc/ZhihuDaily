package com.david.zhihudaily.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.david.zhihudaily.R;
import com.david.zhihudaily.zhihu.NewsModel;

import java.util.ArrayList;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder>
        implements View.OnClickListener {

    private ArrayList<NewsModel> mData;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public enum LOADTYPE {
        MORE, RESET
    }

    public NewsListAdapter(ArrayList<NewsModel> data, Context context) {
        mData = data;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_item, parent, false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NewsModel news = mData.get(position);
        holder.itemView.setTag(news);
        holder.mTextView.setText(news.getTitle());
        Glide.with(mContext)
                .load(news.getImageUrl().get(0))
                .asBitmap()
                .centerCrop()
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void resetAllItems(ArrayList<NewsModel> newslist) {
        mData.clear();
        appendItems(newslist);
    }

    public void appendItems(ArrayList<NewsModel> newslist) {
        mData.addAll(newslist);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onClick(view, (NewsModel) view.getTag());
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;
        TextView mTextView;

        ViewHolder(View v) {
            super(v);
            mImageView = v.findViewById(R.id.list_item_image);
            mTextView = v.findViewById(R.id.list_item_text);
        }
    }


}
