package com.buber.photolistgallery;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.buber.photolistgallery.R.drawable.jan_glass_up_close;
import static com.buber.photolistgallery.R.drawable.ladybaby1;

/**
 * Created by Than on 1/12/2017.
 */

public class PhotoPageFragment extends Fragment implements MenuVisible {
    private static final String TAG = "PhotoPageFragment";
    private static final String ARG_URI = "photo_page_url";
    private static final String ARG_DRIVER_ID = "photo_page_driver_id";
    public static final float PHOTO_SCREEN_REDUCTION_FACTOR = 0.8f;
    public static final float PHOTO_SCREEN_REDUCTION_FACTOR_TABLET = 0.7f;
    public static final float PHOTO_FRAGMENT_PIC_HEIGHT_FACTOR = 0.5f;

    private Uri mUri;
    private String mDataString;
    private TextView mNameTextView;
    private TextView mMessageTextView;
    private TextView mCommentTextView;
    private ImageView mPhotoImageView;
    private Button mChangeImageButton;
    private boolean mSetDefaultImage;
    private boolean mSafeMode;
    private boolean mMenuVisible;
    private Driver mDriver;
    private Picasso mPicasso;
    private int mScreenWidth;
    private int mScreenHeight;
    private int mPicWidth;
    private int mPicHeight;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_page, container, false);
        String emptyUrl = "";

        mPhotoImageView = (ImageView) v
                .findViewById(R.id.fragment_photo_page_image_view);

        mChangeImageButton = (Button) v
                .findViewById(R.id.btn_change_image);

        mNameTextView = (TextView) v
                .findViewById(R.id.fragment_photo_page_text_name);

        mMessageTextView = (TextView) v
                .findViewById(R.id.fragment_photo_page_text_detailed_message);

        mCommentTextView = (TextView) v
                .findViewById(R.id.fragment_photo_page_text_comment);

        if (mDriver == null) {
            mNameTextView.setText(R.string.name_1);
        } else if (mDriver.getHeadline() == null) {
            if (mDriver.getStageName() == null) {
                String headline2 = mDriver.getAgeFormatted() + " year old " + mDriver.getGender() + " from " + mDriver.getCity();
                mNameTextView.setText(headline2);
            } else {
                mNameTextView.setText(mDriver.getStageName());
            }
        } else {
            mNameTextView.setText(mDriver.getHeadline());
        }

        mMessageTextView.setText(R.string.detailed_m_1);
        mCommentTextView.setText(R.string.comment_1);

        mChangeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (mMenuVisible)
                    ;
                else if (mSetDefaultImage) {
                    if (mSafeMode) {
                        mPhotoImageView.setImageResource(R.drawable.poncho1);
                    } else {
                        mPhotoImageView.setImageResource(R.drawable.jan_glass_up_close);
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

        mPhotoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), PhotoDetailedPageActivity.class);
                startActivity(i);
                // next, how to go back to calling activity, on Resume maybe set menuvisibilit ...
            }
        });

        updateUI(emptyUrl);
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
        mPicWidth = (int)(PHOTO_SCREEN_REDUCTION_FACTOR * (float)mScreenWidth);
        mPicHeight = (int)((float)mPicWidth * SingleFragmentActivity.PIC_HEIGHT_WIDTH_RATIO);
        int maxPicHeight = (int)(PHOTO_FRAGMENT_PIC_HEIGHT_FACTOR * (float)mScreenHeight);
        if (mScreenWidth > SingleFragmentActivity.TABLET_WIDTH) {
            mPicWidth = (int)(PHOTO_SCREEN_REDUCTION_FACTOR_TABLET * (float)mScreenWidth);
        } else if (mPicHeight > maxPicHeight)  {
            mPicHeight = maxPicHeight;
            if (mPicWidth > mPicHeight) {
                mPicWidth *= PHOTO_SCREEN_REDUCTION_FACTOR;
            }
        }
        String url = "";
        if (mDriver != null) {
            url = mDriver.getUrl();
        }
        updateUI(url);
    }

    public void setMenuVisible(boolean menuVis) {
        mMenuVisible = menuVis;
    }
    // next set up listener and add a juicy image!
    private void updateUI(String url) {
        if (url.length() < 4) {
            mPhotoImageView.setImageResource(R.drawable.betty_up_close);

        } else if (mPicasso != null) {
                mPicasso.load(url)
                        .placeholder(R.drawable.jan_glass_up_close)
                        .resize(mPicWidth, mPicHeight)
                        .onlyScaleDown()
                        .centerCrop()
                        .into(mPhotoImageView);
        }

    }
}
