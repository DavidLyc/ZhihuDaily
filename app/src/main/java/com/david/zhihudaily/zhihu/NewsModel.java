package com.david.zhihudaily.zhihu;

import android.graphics.Bitmap;

import java.util.List;

public class NewsModel {

    private String title;
    private List<String> images;
    private Bitmap bitmapImage;

    public NewsModel(String title, List<String> images) {
        this.title = title;
        this.images = images;
    }

    public void setBitmapImage(Bitmap bitmapImage) {
        this.bitmapImage = bitmapImage;
    }

    public Bitmap getBitmapImage(){
        return bitmapImage;
    }

    public List<String> getImageUrl() {
        return images;
    }

    public String getTitle() {
        return title;
    }
}
