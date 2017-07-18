package com.david.zhihudaily.zhihu;

import com.david.zhihudaily.BasePresenter;
import com.david.zhihudaily.BaseView;

import java.util.ArrayList;

interface NewsContract {

    interface View extends BaseView<Presenter> {
        void addRecyclerViewItems(ArrayList<NewsModel> newslist);

    }

    interface Presenter extends BasePresenter {
        void getNewsList();

        void start();
    }
}
