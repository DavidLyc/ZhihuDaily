package com.david.zhihudaily.details;

import android.support.annotation.NonNull;

public class DetailPresenter implements DetailContract.Presenter {

    @NonNull
    private final DetailContract.View mView;

    DetailPresenter(@NonNull DetailContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }


    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
