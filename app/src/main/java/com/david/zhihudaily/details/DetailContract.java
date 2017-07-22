package com.david.zhihudaily.details;

import com.david.zhihudaily.BasePresenter;
import com.david.zhihudaily.BaseView;

interface DetailContract {

    interface View extends BaseView<Presenter> {
        void showZhihuWebContent(ZhihuContent content);

    }

    interface Presenter extends BasePresenter {
        void loadZhihuContent(String id);
    }
}
