package com.buber.photolistgallery;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

/**
 * Created by Than on 9/23/2017.
 */

public class PhotoDetailedPageActivity extends AppCompatActivity {
    private static final String TAG = "PhotoDetailedPage";
    private WebView mDetailedView;
    private String mUrlPic;
    private String mUrlSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);
        mDetailedView = (WebView) findViewById(R.id.detailed_webview);
        mDetailedView.setWebViewClient(new MyBrowser());
        mUrlPic = "https://i.redd.it/m3fthaoe7cgz.jpg";
        mUrlSearch = "https://images.google.com/?gws_rd=ssl";
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
        mDetailedView.getSettings().setLoadsImagesAutomatically(true);
        mDetailedView.getSettings().setJavaScriptEnabled(true);
        mDetailedView.getSettings().setBuiltInZoomControls(true);
        mDetailedView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mDetailedView.loadUrl(mUrlPic);
    }

    public void onSearchPic(View v) {
        Log.d(TAG, "onSearchPic() called");
        ClipboardManager clipboard = (ClipboardManager) getSystemService(this.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(null, mUrlPic);
        clipboard.setPrimaryClip(clip);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(mUrlSearch));
        startActivity(i);
    }

    public void onGoBack(View v) {
        onBackPressed();
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
