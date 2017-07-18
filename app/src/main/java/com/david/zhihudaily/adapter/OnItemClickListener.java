package com.david.zhihudaily.adapter;

import android.view.View;

import com.david.zhihudaily.zhihu.NewsModel;

public interface OnItemClickListener {
    void onClick(View view, NewsModel newsModel);
}
