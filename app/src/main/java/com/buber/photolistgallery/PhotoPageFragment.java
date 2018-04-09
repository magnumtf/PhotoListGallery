package com.buber.photolistgallery;

import android.content.Intent;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Arrays;

/**
 * Created by Than on 1/12/2017.
 */

public class PhotoPageFragment extends Fragment implements MenuVisible {
    private static final String TAG = "PhotoPageFragment";
    private static final String ARG_URI = "photo_page_url";
    private static final String ARG_DRIVER_ID = "photo_page_driver_id";
    private static final float ARROW_EXTENSION_FACTOR = 1.3f;
    public static final float PHOTO_SCREEN_REDUCTION_FACTOR = 0.8f;
    public static final int PHOTO_SCREEN_REDUCTION_PIXELS = 48;
    public static final float PHOTO_SCREEN_REDUCTION_FACTOR_TABLET = 0.7f;
    public static final float PHOTO_FRAGMENT_PIC_HEIGHT_FACTOR = 0.5f;

    private Uri mUri;
    private String mDataString;
    private TextView mNameTextView;
    private TextView mMessageTextView;
    private TextView mCommentTextView;
    private ImageView mPhotoImageView;
    private ImageView mLeftArrowView;
    private ImageView mRightArrowView;
    private Button mChangeImageButton;
    private FrameLayout mPhotoFrameLayout;
    private boolean mSetDefaultImage;
    private boolean mSafeMode;
    private boolean mMenuVisible;
    private Driver mDriver;
    private Picasso mPicasso;
    private int mScreenWidth;
    private int mScreenHeight;
    private int mPicWidth;
    private int mPicHeight;
    private Rect mRightArrowRect = new Rect();
    private Rect mLeftArrowRect = new Rect();
    private Handler mHandler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Log.d(TAG, "runnable(): ran");
            // get urlList and assign to driver.
            int listNum = 0;
            String[] urlList;
            String[] urlList2;
            DriverLab driverLab = DriverLab.get(getActivity());
            if (mDriver != null) {
                if (!mDriver.havePicsDownloaded()) {
                    listNum = mDriver.getTestInt();
                    urlList = driverLab.getTestUrlList(listNum);
                    urlList2 = Arrays.copyOfRange(urlList, 2, urlList.length);
                    mDriver.setImageUrlList(urlList2);
                }
            }
            mHandler.removeCallbacks(runnable);
        }
    };

    private View.OnClickListener rightArrowViewListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick(): RightArrow");
            onRightArrowClicked();
        }
    };

    private View.OnClickListener leftArrowViewListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick(): LeftArrow");
            onLeftArrowClicked();
        }
    };

    private View.OnTouchListener photoImageViewListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return onImageTouchEvent(event);
        }
    };

    public static PhotoPageFragment newInstance(Uri uri, int driverId) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_URI, uri);
        args.putInt(ARG_DRIVER_ID, driverId);
        PhotoPageFragment fragment = new PhotoPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUri = getArguments().getParcelable(ARG_URI);
        setRetainInstance(true);
        mSetDefaultImage = false;
        mSafeMode = true;
        DriverLab driverLab = DriverLab.get(getActivity());
        int driverId = getArguments().getInt(ARG_DRIVER_ID);
        mDriver = driverLab.getDriver(driverId);
        mPicasso = Picasso.with(getActivity());
        mPicHeight = 391;
        mPicWidth = 384;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_page, container, false);
        String url = "";

        mPhotoImageView = (ImageView) v
                .findViewById(R.id.fragment_photo_page_image_view);

        mLeftArrowView = (ImageView) v
                .findViewById(R.id.fragment_photo_page_left_arrow_view);

        mRightArrowView = (ImageView) v
                .findViewById(R.id.fragment_photo_page_right_arrow_view);

        mChangeImageButton = (Button) v
                .findViewById(R.id.btn_change_image);

        mNameTextView = (TextView) v
                .findViewById(R.id.fragment_photo_page_text_name);

        mMessageTextView = (TextView) v
                .findViewById(R.id.fragment_photo_page_text_detailed_message);

        mCommentTextView = (TextView) v
                .findViewById(R.id.fragment_photo_page_text_comment);

        mPhotoFrameLayout = (FrameLayout) v
                .findViewById(R.id.fragment_photo_frame_layout);

        if (mDriver == null) {
            mNameTextView.setText(R.string.name_1);
        } else if (mDriver.getHeadline() == null) {
            if (mDriver.getStageName() == null) {
                String headline2 = mDriver.getAgeFormatted() + " year old " + mDriver.getGender() + " from " + mDriver.getCity();
                mNameTextView.setText(headline2);
            } else {
                mNameTextView.setText(mDriver.getStageName());
            }
            url = mDriver.getHomeImageUrl();
        } else {
            mNameTextView.setText(mDriver.getHeadline());
            url = mDriver.getHomeImageUrl();
        }

        mMessageTextView.setText(R.string.detailed_m_1);
        mCommentTextView.setText(R.string.comment_1);

        mLeftArrowView.setOnClickListener(leftArrowViewListener);
        mRightArrowView.setOnClickListener(rightArrowViewListener);
        mChangeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (mMenuVisible)
                    ;
                else if (mSetDefaultImage) {
                    if (mSafeMode) {
                        mPhotoImageView.setImageResource(R.drawable.poncho1);
                    } else {
                        mPhotoImageView.setImageResource(R.drawable.original_resa);
                    }
                    mSetDefaultImage = false;
                    mNameTextView.setText(R.string.name_3);
                    mMessageTextView.setText(R.string.detailed_m_3);
                    mCommentTextView.setText(R.string.comment_3);
                } else {
                    if (mSafeMode) {
                        mPhotoImageView.setImageResource(R.drawable.angry_black1);
                    } else {
                        mPhotoImageView.setImageResource(R.drawable.tara_feet_up_close);
                    }
                    mSetDefaultImage = true;
                    mNameTextView.setText(R.string.name_4);
                    mMessageTextView.setText(R.string.detailed_m_4);
                    mCommentTextView.setText(R.string.long_comment_test);
                }
            }
        }
        );

//        mPhotoImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//              Intent i = new Intent(getActivity(), PhotoDetailedPageActivity.class);
   //            startActivity(i);
      //          // next, how to go back to calling activity, on Resume maybe set menuvisibilit ...
        //    }
//        });

        mPhotoImageView.setOnTouchListener(photoImageViewListener);

        Log.d(TAG, "onCreateView(): updateUI(" + url + ")");
        updateUI(url);
        if (mDriver != null) {
            if (!mDriver.havePicsDownloaded()) {
                // fetch ur other pics here.
                Log.d(TAG, "onCreateView(): start runnable");
                mHandler.postDelayed(runnable, 2000);
            }
        }
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        // do stuff
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mScreenHeight = displayMetrics.heightPixels;
        mScreenWidth = displayMetrics.widthPixels;
        int frameHeight = mScreenHeight / 3;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(mScreenWidth, frameHeight);
        // mPhotoFrameLayout.setLayoutParams(lp);
        // yo, mtf setLayoutParams works to set the size of the frame layout,
        // but this version is too small - it looks better on the LG without it.

        int maxPicHeight = (int)(PHOTO_FRAGMENT_PIC_HEIGHT_FACTOR * (float)mScreenHeight);
        if (mScreenWidth > SingleFragmentActivity.TABLET_WIDTH) {
            mPicWidth = (int)(PHOTO_SCREEN_REDUCTION_FACTOR_TABLET * (float)mScreenWidth);
            mPicHeight = (int)((float)mPicWidth * SingleFragmentActivity.PIC_HEIGHT_WIDTH_RATIO);
        }
        else {
            mPicWidth = mScreenWidth - (2 * PHOTO_SCREEN_REDUCTION_PIXELS);
            mPicHeight = (int)((float)mPicWidth * SingleFragmentActivity.PIC_HEIGHT_WIDTH_RATIO);
            if (mPicHeight > maxPicHeight) {
                mPicWidth = mScreenWidth - (2 * PHOTO_SCREEN_REDUCTION_PIXELS);
                mPicHeight = maxPicHeight;
                if (mPicWidth > mPicHeight) {
                    // maybe, but use below mPicWidth *= PHOTO_SCREEN_REDUCTION_FACTOR;
                    mPicWidth = mPicHeight;
                }
            }
        }
        mRightArrowView.getHitRect(mRightArrowRect);
        boolean rightSide = true;
        extendArrowTouchCoordinates(mRightArrowRect, rightSide, ARROW_EXTENSION_FACTOR);
        mLeftArrowView.getHitRect(mLeftArrowRect);
        rightSide = false;
        extendArrowTouchCoordinates(mLeftArrowRect, rightSide, ARROW_EXTENSION_FACTOR);
        String url = "";
        if (mDriver != null) {
            url = mDriver.getUrl();
        }
        Log.d(TAG, "OnResume(): Screen Height = " + mScreenHeight + ". Screen Width = " + mScreenWidth + ". Pic Height = " + mPicHeight + ". Pic Width = " + mPicWidth);
        updateUI(url);
    }

    public void setMenuVisible(boolean menuVis) {
        mMenuVisible = menuVis;
    }
    // next set up listener and add a juicy image!

    private void onLeftArrowClicked() {
        Log.d(TAG, "onLeftArrowClicked() called");
        String url = "";
        if (mDriver != null) {
            mDriver.decreaseIndex();
            url = mDriver.getUrl();
        }
        updateUI(url);
    }

    private void onRightArrowClicked() {
        String url = "";
        Integer ind = -1;
        if (mDriver != null) {
            mDriver.increaseIndex();
            url = mDriver.getUrl();
            ind = mDriver.getIndex();
        }
        Log.d(TAG, "onRightArrowClick() called. index = " + ind.toString());
        updateUI(url);
    }

    private boolean onImageTouchEvent(MotionEvent event) {
        int touchedX = (int) event.getX();
        int touchedY = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mRightArrowRect.contains(touchedX, touchedY)) {
                    Log.d(TAG, "onImageTouchEvent(): RightArrow");
                    onRightArrowClicked();
                } else if (mLeftArrowRect.contains(touchedX, touchedY)) {
                    Log.d(TAG, "onImageTouchEvent(): LeftArrow");
                    onLeftArrowClicked();
                } else {
                    Log.d(TAG, "onImageTouchEvent(): Pic");
                    Intent i = new Intent(getActivity(), PhotoDetailedPageActivity.class);
                    startActivity(i);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }

    private void extendArrowTouchCoordinates(Rect viewRect, boolean rightSide, float extendFactor) {
        float width = (float)viewRect.width() * extendFactor;
        float height = (float)viewRect.height() * extendFactor;
        int centerX = viewRect.centerX();
        int centerY = viewRect.centerY();
        int leftC = viewRect.left;
        int rightC = viewRect.right;
        int bottomC = viewRect.bottom;
        int topC = viewRect.top;
        if (rightSide) {
            leftC = centerX - (int)width;
        } else {
            rightC = centerX + (int) width;
        }
        topC = centerY - (int)height;
        bottomC = centerY + (int)height;
        viewRect.set(leftC, topC, rightC, bottomC);
    }

    private void updateUI(String url) {
        if (url.length() < 4) {
            mPhotoImageView.setImageResource(R.drawable.original_resa);

        } else if (mPicasso != null) {
                mPicasso.load(url)
                        .placeholder(R.drawable.original_resa)
                        .resize(mPicWidth, mPicHeight)
                        .onlyScaleDown()
                        .centerCrop()
                        .into(mPhotoImageView);
        }
        Rect testBounds = new Rect();
        mRightArrowView.getHitRect(testBounds);
        int cX = testBounds.centerX();
        int cY = testBounds.centerY();
        int width = testBounds.right - testBounds.left;
        int height = testBounds.bottom - testBounds.top;

        Log.d(TAG, "updateUI(): RightArrowWidth = " + mRightArrowView.getMeasuredWidth() + ", RightArrowHeight = " + mRightArrowView.getMeasuredHeight() + ", " + mPicWidth + ", " + mPicHeight);
        Log.d(TAG, "updateUI(): RightArrowWidth2 = " + width + ", RightArrowHeight2 = " + height + ", " + mPicWidth + ", " + mPicHeight);
        Log.d(TAG, "updateUI(): url = " + url + ". RightArrow cX = " + cX + ", RightArrow cY = " + cY + ", " + mPicWidth + ", " + mPicHeight);
    }
}
