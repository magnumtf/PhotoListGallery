package com.buber.photolistgallery;

/**
 * Created by Than on 1/5/2017.
 */

public class GalleryItem {
    private String mCaption;
    private String mComment;
    private String mId;
    private String mUrl;

    public String getCaption() {
        return mCaption;
    }

    public void setCaption(String caption) {
        this.mCaption = caption;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String mComment) {
        this.mComment = mComment;
    }

    @Override
    public String toString() {
        return mCaption;
    }

}
