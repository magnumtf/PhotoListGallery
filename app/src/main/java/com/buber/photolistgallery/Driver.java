package com.buber.photolistgallery;

import java.util.UUID;

/**
 * Created by Than on 8/28/2017.
 */

public class Driver {
    private UUID mId;
    private String mStageName;
    private float mRating;
    private int mRatingInt;
    private int mNumRatings;
    private Integer mAge;
    private float mDistance;
    private int mThumbNail;
    private String mStatus;
    private int mPartStarResource;
    private String mCaption;
    private String mUrl;
    private String mFlickrId;

    public Driver() {
        // Generate unique identifier
        mId = UUID.randomUUID();
        mStatus = "ONLINE";  // should be an enum?
        mPartStarResource = R.drawable.ic_star_gold_full_rev1_cropped_scaled3a;
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        this.mId = id;
    }

    public String getStageName() {
        return mStageName;
    }

    public void setStageName(String stageName) {
        this.mStageName = stageName;
    }

    public float getRating() {
        return mRating;
    }

    public String getRatingFormatted() {
        String outString = String.format("%.1f", mRating);
        return outString;
    }

    public void setRating(float rating) {
        this.mRating = rating;
        this.mRatingInt = (int) rating;
        float frating = rating - (float)mRatingInt;
        if (frating > 0.9) {
            mPartStarResource = R.drawable.ic_star_gold_full_rev1_cropped_scaled3a;
        }
        else if (frating > 0.2) {
            mPartStarResource = R.drawable.ic_star_gold_half_rev1_cropped_scaled3a;
        }
        else {
            mPartStarResource = R.drawable.ic_star_gold_empty_rev1_cropped_scaled3a;
        }
    }

    public int getNumRatings() {
        return mNumRatings;
    }

    public void setNumRatings(int numRatings) {
        this.mNumRatings = numRatings;
    }

    public int getAge() {
        return (int)mAge;
    }

    public String getAgeFormatted() {
        return mAge.toString();
    }

    public void setAge(int age) {
        this.mAge = age;
    }

    public float getDistance() {
        return mDistance;
    }

    public String getDistanceFormatted() {
        String outString;
        Integer iDistance = (int) Math.round(mDistance);
        if (mDistance >= 9.95f) {
            outString = iDistance.toString();
        }
        else if (mDistance <= 0.1f) {
            outString = "0.1";
        }
        else {
            outString = String.format("%.1f", mDistance);
        }
        return outString;
    }

    public void setDistance(float distance) {
        this.mDistance = distance;
    }

    public int getThumbNail() {
        return mThumbNail;
    }

    public void setThumbNail(int thumbNail) {
        this.mThumbNail = thumbNail;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        this.mStatus = status;
    }

    public int getEndStarNumber() { return mRatingInt; }

    public int getPartialStarResource() { return mPartStarResource; }

    public String getCaption() {
        return mCaption;
    }

    public void setCaption(String caption) {
        this.mCaption = caption;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    public String getFlickrId() {
        return mFlickrId;
    }

    public void setFlickrId(String flickrId) {
        this.mFlickrId = flickrId;
    }
}