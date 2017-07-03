package com.buber.photolistgallery;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class PhotoListGalleryActivity extends SingleFragmentActivity {
    private static final String TAG = "PhotoListGalleryAct";

    @Override
    public Fragment createFragment() {
        return PhotoListGalleryFragment.newInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

//    public Fragment createFragment() {
//        Uri ENDPOINT = Uri
//                .parse("http://www.google.com/");
//        return PhotoPageFragment.newInstance(ENDPOINT);
//    }
}
