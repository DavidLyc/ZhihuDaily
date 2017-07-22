package com.david.zhihudaily.zhihu;

import java.util.List;

public class NewsModel {

    private String title;
    private List<String> images;
    private String id;

    public List<String> getImageUrl() {
        return images;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

}
