package com.david.zhihudaily.zhihu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.david.zhihudaily.adapter.NewsListAdapter;
import com.david.zhihudaily.network.RetrofitFactory;
import com.david.zhihudaily.util.NetworkUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class NewsPresenter implements NewsContract.Presenter {

    @NonNull
    private final NewsContract.View mView;
    @NonNull
    private Context mContext;
    private CompositeDisposable mDisposables;
    private boolean mIsFirstLoad = true;

    NewsPresenter(@NonNull NewsContract.View view, @NonNull Context context) {
        mView = view;
        mContext = context;
        mDisposables = new CompositeDisposable();
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        if (mIsFirstLoad) {
            mIsFirstLoad = false;
            getNewsList();
        }
    }

    @Override
    public void unsubscribe() {
        mDisposables.clear();
    }

    @Override
    public void getNewsList() {
        if (!NetworkUtil.isNetworkAvailable(mContext)) {
            mView.showNetworkError();
            return;
        }
        mDisposables.add(RetrofitFactory.getInstance()
                .getZhihuNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<NewsListModel>() {
                    @Override
                    public void accept(@NonNull NewsListModel newsListModel)
                            throws Exception {
                        mView.loadRecyclerViewItems(newsListModel.getStories());
                    }
                })
        );
    }

    @Override
    public void getBeforeNews(String date, final int loadType) {
        if (!NetworkUtil.isNetworkAvailable(mContext)) {
            mView.showNetworkError();
            return;
        }
        mDisposables.add(RetrofitFactory.getInstance()
                .getZhihuBeforeNews(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<NewsListModel>() {
                    @Override
                    public void accept(@NonNull NewsListModel newsListModel) throws Exception {
                        if (loadType == NewsListAdapter.LOADTYPE.MORE.ordinal()) {
                            mView.loadMoreRecyclerViewItems(newsListModel.getStories());
                        } else {
                            mView.loadRecyclerViewItems(newsListModel.getStories());
                        }
                    }
                })
        );
    }

    @Override
    public void start() {

    }
}
