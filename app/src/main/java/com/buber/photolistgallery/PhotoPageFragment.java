package com.buber.photolistgallery;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static com.buber.photolistgallery.R.drawable.ladybaby1;

/**
 * Created by Than on 1/12/2017.
 */

public class PhotoPageFragment extends Fragment {
    private static final String TAG = "PhotoPageFragment";
    private static final String ARG_URI = "photo_page_url";

    private Uri mUri;
    private String mDataString;
    private TextView mNameTextView;
    private TextView mMessageTextView;
    private TextView mCommentTextView;
    private ImageView mPhotoImageView;
    private Button mChangeImageButton;
    private boolean mSetDefaultImage;
    private boolean mSafeMode;

    public static PhotoPageFragment newInstance(Uri uri) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_URI, uri);

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_page, container, false);

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

        mNameTextView.setText(R.string.name_1);
        mMessageTextView.setText(R.string.detailed_m_1);
        mCommentTextView.setText(R.string.comment_1);

        if (mSafeMode) {
            mPhotoImageView.setImageResource(R.drawable.ladybaby1);
        }

        mChangeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (mSetDefaultImage) {
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
        return v;
    }

    // next set up listener and add a juicy image!
}
