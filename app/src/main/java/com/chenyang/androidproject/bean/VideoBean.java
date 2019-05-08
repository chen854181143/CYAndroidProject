package com.chenyang.androidproject.bean;

import com.chenyang.androidproject.bean.base.BaseBean;

public class VideoBean extends BaseBean {
    private String coverImage;//封面图片
    private String videoUrl;//视频地址
    private String videoTitle;//视频标题

    public VideoBean(String coverImage, String videoUrl, String videoTitle) {
        this.coverImage = coverImage;
        this.videoUrl = videoUrl;
        this.videoTitle = videoTitle;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }
}
