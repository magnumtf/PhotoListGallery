package com.buber.photolistgallery;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Than on 9/19/2017.
 */

public class PicsRealActivity extends AppCompatActivity {
    private TextView mTitle;
    private TextView mBody;
    private LinearLayout mBigPicLayout;
    private RelativeLayout mMainFrame;
    private boolean mBigPicVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pics_real); // make layout for dis.
        // user raven-pro
        mBody = (TextView) findViewById(R.id.pics_real_text_view);
        mBigPicLayout = (LinearLayout) findViewById(R.id.big_pic_overlay);
        mMainFrame = (RelativeLayout) findViewById(R.id.pics_real_relative_layout_view);
        mBigPicVisible = false;

//        Typeface face = Typeface.createFromAsset(getAssets(), "font/OpenSans-Regular");
//        mBody.setTypeface(face);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_payment, menu); //?
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        setBigPicVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return(super.onOptionsItemSelected(item));
    }

    public void onBigPicture(View v) {
        setBigPicVisible(true);
    }

    private void setBigPicVisible(boolean setVis) {
        if (setVis) {
            mBigPicLayout.setVisibility(View.VISIBLE);
            mBigPicVisible = true;
            mMainFrame.setBackgroundColor(Color.GRAY);
        } else {
            mBigPicLayout.setVisibility(View.GONE);
            mBigPicVisible = false;
            mMainFrame.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public void onBackPressed(){
        if (mBigPicVisible) {
            setBigPicVisible(false);
        } else {
            super.onBackPressed();
        }
    }
}
