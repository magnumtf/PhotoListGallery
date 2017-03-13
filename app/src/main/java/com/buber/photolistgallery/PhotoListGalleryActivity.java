package com.buber.photolistgallery;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PhotoListGalleryActivity extends SingleFragmentActivity {
    @Override
    public Fragment createFragment() {
        return PhotoListGalleryFragment.newInstance();
    }

//    public Fragment createFragment() {
//        Uri ENDPOINT = Uri
//                .parse("http://www.google.com/");
//        return PhotoPageFragment.newInstance(ENDPOINT);
//    }
}
