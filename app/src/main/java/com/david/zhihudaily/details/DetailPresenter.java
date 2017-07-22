package com.david.zhihudaily.details;

import android.support.annotation.NonNull;

import com.david.zhihudaily.network.RetrofitFactory;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DetailPresenter implements DetailContract.Presenter {

    @NonNull
    private final DetailContract.View mView;
    private CompositeDisposable mDisposables;

    DetailPresenter(@NonNull DetailContract.View view) {
        mView = view;
        mDisposables = new CompositeDisposable();
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
    }

    @Override
    public void unsubscribe() {
        mDisposables.clear();
    }

    @Override
    public void loadZhihuContent(String id) {
        mDisposables.add(
                RetrofitFactory.getInstance()
                        .getZhihuNewsDetail(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<ZhihuContent>() {
                            @Override
                            public void accept(@NonNull ZhihuContent content) throws Exception {
                                mView.showZhihuWebContent(content);
                            }
                        })
        );
    }
}
