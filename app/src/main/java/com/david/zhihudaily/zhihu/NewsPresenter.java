package com.david.zhihudaily.zhihu;

import android.support.annotation.NonNull;

import com.david.zhihudaily.network.RetrofitFactory;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class NewsPresenter implements NewsContract.Presenter {

    @NonNull
    private final NewsContract.View mView;
    private CompositeDisposable mDisposables;
    private boolean isFirstLoad = true;

    NewsPresenter(@NonNull NewsContract.View view) {
        mView = view;
        mDisposables = new CompositeDisposable();
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        if (isFirstLoad) {
            isFirstLoad = false;
            getNewsList();
        }
    }

    @Override
    public void unsubscribe() {
        mDisposables.clear();
    }

    @Override
    public void getNewsList() {
        mDisposables.add(
                RetrofitFactory.getInstance()
                        .getZhihuNews()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                new Consumer<NewsListModel>() {
                                    @Override
                                    public void accept(@NonNull NewsListModel newsListModel)
                                            throws Exception {
                                        mView.loadRecyclerViewItems(newsListModel.getStories());
                                    }
                                }
                        )
        );
    }

    @Override
    public void start() {

    }
}
