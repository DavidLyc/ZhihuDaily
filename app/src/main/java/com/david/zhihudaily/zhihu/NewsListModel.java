package com.david.zhihudaily.zhihu;

import java.util.List;

public class NewsListModel {

    private String date;
    private List<NewsModel> stories;

    public NewsListModel() {
    }

    public String getDate() {
        return date;
    }

    public List<NewsModel> getStories() {
        return stories;
    }
}
