package com.david.zhihudaily.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.david.zhihudaily.R;
import com.david.zhihudaily.zhihu.NewsModel;

import java.util.ArrayList;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

    private ArrayList<NewsModel> mData;

    public NewsListAdapter(ArrayList<NewsModel> data) {
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NewsModel news = mData.get(position);
        holder.mTextView.setText(news.getTitle());
        holder.mImageView.setImageBitmap(news.getBitmapImage());
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public void addItem(NewsModel news) {
        int position = mData.size();
        mData.add(position, news);
        notifyItemInserted(position);
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
