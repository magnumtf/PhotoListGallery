package com.buber.photolistgallery;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Than on 8/28/2017.
 */

public class Driver {
    private static final String TAG = "Driver";
    public static final int MAX_PHOTO_PAGE_IMAGES = 10;
    private static final String PLACEHOLDER_URL = "https://i.redd.it/jzyq9v7s2saz.jpg";

    private int mId;
    private String mStageName;
    private float mRating;
    private int mRatingInt;
    private int mNumRatings;
    private Integer mAge;
    private float mDistance;
    private String mStatus;
    private int mPartStarResource;
    private String mCaption;
    private String mThumbUrl;
    private String mHomeImageUrl;
    private String mFlickrId;
    private String mHeadline;
    private String mCity;
    private String mGender;
    private int mNumImages;
    private int mImageIndex;
    private int mTestInt;
    private ArrayList<String> mImageUrls = new ArrayList<String>();

    public Driver() {
        // Generate non-unique identifier
        Random r = new Random();
        mId = 2500 + r.nextInt(10000);
        mStatus = "ONLINE";  // should be an enum?
        mPartStarResource = R.drawable.ic_star_gold_full_rev1_cropped_scaled3a;
        mGender = "female";
        for (int i = 0; i < MAX_PHOTO_PAGE_IMAGES; i++) {
            mImageUrls.add(PLACEHOLDER_URL);
        }
        mNumImages = 0;
        mImageIndex = 0;
        mThumbUrl = "";
        mHomeImageUrl = "";
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
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
        String url = "";
        if (mImageUrls.size() == 0 || mNumImages == 0) {
            url = mHomeImageUrl;
            Log.d(TAG, "getUrl(): Homepage = " + url);
        } else if (mImageIndex >= 0 && mImageIndex < mNumImages) {
            url = mImageUrls.get(mImageIndex);
            Log.d(TAG, "getUrl(): ArrayIndex(" + mImageIndex + ") = " + url);
        }
        else
            Log.d(TAG, "getUrl(): Empty = " + url + ". imageIndex = " + mImageIndex);
        return url;
    }

    public void setThumbUrl(String url) {
        this.mThumbUrl = url;
    }

    public String getThumbUrl() {
        return mThumbUrl;
    }

    public String getFlickrId() {
        return mFlickrId;
    }

    public void setFlickrId(String flickrId) {
        this.mFlickrId = flickrId;
    }

    public String getHeadline() {
        return mHeadline;
    }

    public void setHeadline(String headline) {
        this.mHeadline = headline;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        this.mCity = city;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String gender) {
        if (gender.startsWith("Male") || gender.startsWith("male") || gender.startsWith("MALE"))
            this.mGender = "male";
        else if (gender.startsWith("Tran") || gender.startsWith("tran") || gender.startsWith("TRAN"))
            this.mGender = "trans";
        else
            this.mGender = "female";
    }

    public void addImageUrl(String url) {
        if (mNumImages < MAX_PHOTO_PAGE_IMAGES) {
            if ((mNumImages == 0) && (mHomeImageUrl == "")) {
                Log.d(TAG, "addImageUrl(): Set homeImage to " + url);
                setHomeImageUrl(url);
                mImageUrls.add(url);
                mNumImages++;
            } else if (mNumImages > 0) {
                mImageUrls.add(url);
                mNumImages++;
            }
        }
    }

    public void addImageUrl(String url, int index) {
        boolean addImage = false;
        if (mNumImages < MAX_PHOTO_PAGE_IMAGES && index < MAX_PHOTO_PAGE_IMAGES) {
            if ((index == 0) && (mHomeImageUrl == "")) {
                Log.d(TAG, "addImageUrl(index): set homeImage to " + url);
                setHomeImageUrl(url);
                mImageUrls.set(index, url);
                addImage = true;
            } else if (index > 0) {
                mImageUrls.set(index, url);
                addImage = true;
            }
            if (addImage && (index >= mNumImages))
                mNumImages++;
        }
    }

    public void setImageUrlList(String[] urlList) {
        int i = 0;
        for (String url : urlList) {
            if (i == 0) {
                if (mHomeImageUrl == "") {
                    Log.d(TAG, "setImageUrlList(): set homeImage to " + url);
                    setHomeImageUrl(url);
                } else {
                    mImageUrls.add(i, mHomeImageUrl);
                    i++;
                }
            }
            mImageUrls.add(i, url);
            if (++i >= MAX_PHOTO_PAGE_IMAGES) {
                break;
            }
        }
        if (i > 0) {
            mNumImages = i;
        }
    }

    public void setImageUrlList(ArrayList<String> urlList) {
        int i = 0;
        for (String url : urlList) {
            if (i == 0) {
                if (mHomeImageUrl == "") {
                    Log.d(TAG, "setImageUrlList()2: set homeImage to " + url);
                    setHomeImageUrl(url);
                } else {
                    mImageUrls.add(i, mHomeImageUrl);
                    i++;
                }
            }
            mImageUrls.add(i, url);
            if (++i >= MAX_PHOTO_PAGE_IMAGES) {
                break;
            }
        }
        if (i > 0) {
            mNumImages = i;
        }
    }

    public ArrayList<String> getImageUrlList() {
        return mImageUrls;
    }

    public String getImageUrl(int index) {
        String retString = "";
        if (index >= 0 && index < MAX_PHOTO_PAGE_IMAGES) {
            try {
                retString = mImageUrls.get(index);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("Driver::getImageUrl: out of bounds Exception(" + index + ").");
            }
        }
        else {
            Log.d(TAG, "getImageUrl(): bad index: " + index);
        }
        return retString;
    }

    public void increaseIndex() {
        mImageIndex++;
        if (mImageIndex >= mNumImages) {
            mImageIndex = 0;
        }
    }

    public void decreaseIndex() {
        mImageIndex--;
        if (mImageIndex < 0) {
            mImageIndex = mNumImages - 1;
        }
    }

    public void resetIndex() {
        mImageIndex = 0;
    }

    public int getIndex() {
        return mImageIndex;
    }

    public String getHomeImageUrl() {
        return mHomeImageUrl;
    }

    public void setHomeImageUrl(String homeImageUrl) {
        if (homeImageUrl.length() > 6) {
            this.mHomeImageUrl = homeImageUrl;
            Log.d(TAG, "setHomeImageUrl(): " + homeImageUrl);
        }
    }

    public int getTestInt() {
        return mTestInt;
    }

    public void setTestInt(int testInt) {
        this.mTestInt = testInt;
    }

    public boolean havePicsDownloaded() {
        if (mNumImages > 0) {
            return true;
        }
        return false;
    }
}
