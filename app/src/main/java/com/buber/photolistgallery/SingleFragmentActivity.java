package com.buber.photolistgallery;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public abstract class SingleFragmentActivity extends AppCompatActivity {
    private static final String TAG = "SingleFragementActivity";
    private ImageButton mMenuImage;
    private LinearLayout mMenuOverlay;
    private FrameLayout mFragmentFrame;
    private boolean mMenuVisible;
    private Toolbar mTopToolBar;

    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        setTitle(null);

        mTopToolBar = (Toolbar)findViewById(R.id.myToolbar);
        setSupportActionBar(mTopToolBar);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

        mMenuOverlay = (LinearLayout) findViewById(R.id.overlay);
        mFragmentFrame = (FrameLayout) findViewById(R.id.fragment_container);
        setMenuVisible(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        setMenuVisible(false);
    }

    public void onDisplayMenuOverlay(View v) {
        Log.d(TAG, "onDisplayMenuOverlay() called");
        setMenuVisible(true);
    }

    public void onPaymentMethod(View v) {
        Log.d(TAG, "onPaymentMethod() called");
        Intent i = new Intent(SingleFragmentActivity.this, PaymentMethodActivity.class);
        startActivity(i);
        // next, how to go back to calling activity, on Resume maybe set menuvisibilit ...
    }

    public void onYourTrips(View v) {
        Log.d(TAG, "onYourTrips() called");
    }

    public void onFreeRides(View v) {
        Log.d(TAG, "onFreeRides() called");
    }

    public void onNeedHelp(View v) {
        Log.d(TAG, "onNeedHelp() called");
    }

    public void onGoToSettings(View v) {
        Log.d(TAG, "onGoToSettings() called");
    }

    public void onDriveWithBuber(View v) {
        Log.d(TAG, "onDriveWithBuber() called");
    }

    public void onLegal(View v) {
        Log.d(TAG, "onLegal() called");
    }

    private void setMenuVisible(boolean setVis) {
        FragmentManager fm = getSupportFragmentManager();
        List<Fragment> listFrags = new ArrayList<Fragment>();

        if (setVis) {
            mMenuOverlay.setVisibility(View.VISIBLE);
            mFragmentFrame.getForeground().setAlpha(150);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorToolbarOverlay)));
            mMenuVisible = true;
        } else {
            mMenuOverlay.setVisibility(View.GONE);
            mFragmentFrame.getForeground().setAlpha(0);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorToolbarBackground)));
            mMenuVisible = false;
        }

        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment != null) {
            ((MenuVisible) fragment).setMenuVisible(mMenuVisible);
        }
    }

    @Override
    public void onBackPressed(){
        if (mMenuVisible) {
            setMenuVisible(false);
        } else {
            super.onBackPressed();
        }
    }

    public boolean getMenuVisible() {
        return mMenuVisible;
    }
}
