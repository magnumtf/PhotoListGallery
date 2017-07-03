package com.buber.photolistgallery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public abstract class SingleFragmentActivity extends AppCompatActivity {
    private static final String TAG = "SingleFragementActivity";
    private ImageButton mMenuImage;
    private LinearLayout mMenuOverlay;
    private FrameLayout mFragmentFrame;

    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        setTitle(null);

        Toolbar topToolBar = (Toolbar)findViewById(R.id.myToolbar);
        setSupportActionBar(topToolBar);

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
        mFragmentFrame.getForeground().setAlpha(0);
    }

    public void scrollToTop(View v) {
        Log.d(TAG, "scrollToTop() called");
        mMenuOverlay.setVisibility(View.VISIBLE);
        mFragmentFrame.getForeground().setAlpha(150);
    }
}
