package com.yanshun.mfluitmarket.common.entity;


import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.ObservableField;

import com.yanshun.mfluitmarket.BR;

public class ZiXunAll extends BaseObservable{


    private String categoryName;
    private String createTime;
    private int id;
    private String imageUrl;
    private String publicDay;
    private String publicTime;
    private String sourceUrl;
    private String title;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPublicDay() {
        return publicDay;
    }

    public void setPublicDay(String publicDay) {
        this.publicDay = publicDay;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Bindable
    public String getPublicTime() {
        return publicTime;
    }

    public void setPublicTime(String publicTime) {
        this.publicTime = publicTime;
        notifyPropertyChanged(BR.publicTime);
    }
}
