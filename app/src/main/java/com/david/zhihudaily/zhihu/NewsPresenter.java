package com.david.zhihudaily.zhihu;

import android.support.annotation.NonNull;
import android.util.Log;

import com.david.zhihudaily.network.RetrofitFactory;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class NewsPresenter implements NewsContract.Presenter {

    @NonNull
    private final NewsContract.View mView;

    private CompositeDisposable mDisposables;

    NewsPresenter(@NonNull NewsContract.View view) {
        mView = view;
        mDisposables = new CompositeDisposable();
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        getNewsList();
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
                        .concatMap(new Function<NewsListModel, ObservableSource<NewsModel>>() {
                            @Override
                            public ObservableSource<NewsModel> apply(@NonNull NewsListModel newsListModel)
                                    throws Exception {
                                List<NewsModel> newsModels = newsListModel.getStories();
                                return Observable.fromIterable(newsModels);
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<NewsModel>() {
                            @Override
                            public void accept(@NonNull NewsModel news)
                                    throws Exception {
                                mView.addRecyclerViewItem(news);
                            }
                        })
        );
    }

    @Override
    public void start() {

    }
}
