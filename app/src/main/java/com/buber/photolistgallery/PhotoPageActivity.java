package com.buber.photolistgallery;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;

/**
 * Created by Than on 1/14/2017.
 */

public class PhotoPageActivity extends SingleFragmentActivity {

    private static final String DRIVER_ID = "com.buber.photolistgallery.driver_id";

    public static Intent newIntent(Context context, Uri photoPageUri, int id) {
        Intent i = new Intent(context, PhotoPageActivity.class);
        i.setData(photoPageUri);
        i.putExtra(DRIVER_ID, id);
        return i;
    }

    @Override
    protected Fragment createFragment() {
        return PhotoPageFragment.newInstance(getIntent().getData(), getIntent().getIntExtra(DRIVER_ID, 0));
    }
}
