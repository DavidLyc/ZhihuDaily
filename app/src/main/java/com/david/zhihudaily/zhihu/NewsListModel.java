package com.david.zhihudaily.zhihu;

import java.util.ArrayList;

public class NewsListModel {

    private String date;
    private ArrayList<NewsModel> stories;

    public NewsListModel() {
    }

    public String getDate() {
        return date;
    }

    public ArrayList<NewsModel> getStories() {
        return stories;
    }
}
