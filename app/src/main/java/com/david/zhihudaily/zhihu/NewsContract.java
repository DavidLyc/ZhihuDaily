package com.david.zhihudaily.zhihu;

import com.david.zhihudaily.BasePresenter;
import com.david.zhihudaily.BaseView;

public interface NewsContract {

    interface View extends BaseView<Presenter> {
        void addRecyclerViewItem(NewsModel news);
    }

    interface Presenter extends BasePresenter {
        void getNewsList();

        void start();
    }
}
