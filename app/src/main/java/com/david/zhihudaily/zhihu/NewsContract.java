package com.david.zhihudaily.zhihu;

import com.david.zhihudaily.BasePresenter;
import com.david.zhihudaily.BaseView;

import java.util.ArrayList;

interface NewsContract {

    interface View extends BaseView<Presenter> {
        void loadRecyclerViewItems(ArrayList<NewsModel> newslist);
        void loadMoreRecyclerViewItems(ArrayList<NewsModel> newslist);
    }

    interface Presenter extends BasePresenter {
        void getNewsList();
        void getBeforeNews(String date);
        void start();
    }
}
