package com.david.zhihudaily.network;

import com.david.zhihudaily.zhihu.NewsListModel;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface RetrofitService {
    @GET("latest")
    Observable<NewsListModel> getZhihuNews();
}
