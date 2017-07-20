package com.david.zhihudaily.network;

import com.david.zhihudaily.zhihu.NewsListModel;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitService {
    @GET("latest")
    Observable<NewsListModel> getZhihuNews();

    @GET("{id}")
    Observable<ResponseBody> getZhihuNewsDetail(@Path("id") String id);
}
