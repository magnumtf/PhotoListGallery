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

/**
 * Created by Than on 1/12/2017.
 */

/*
 * Copied from PhotoPageFragment. To page through an array of pics, comments, and details.
 */

public class PhotoPagePagerFragment extends Fragment {
    private static final String TAG = "PhotoPageFragment";
    private static final String ARG_URI = "photo_page_url";

    private Uri mUri;
    private String mDataString;
    private TextView mPhotoTextView;
    private ImageView mPhotoImageView;
    private Button mChangeImageButton;
    private boolean mSetDefaultImage;

    public static PhotoPagePagerFragment newInstance(Uri uri) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_URI, uri);

        PhotoPagePagerFragment fragment = new PhotoPagePagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUri = getArguments().getParcelable(ARG_URI);
        setRetainInstance(true);
        mSetDefaultImage = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_page, container, false);

        mPhotoImageView = (ImageView) v
                .findViewById(R.id.fragment_photo_page_image_view);

        mChangeImageButton = (Button) v
                .findViewById(R.id.btn_change_image);

        mPhotoTextView = (TextView) v
                .findViewById(R.id.fragment_photo_page_text_view);

        mChangeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (mSetDefaultImage) {
                    mPhotoImageView.setImageResource(R.drawable.original_resa);
                    mSetDefaultImage = false;
                    mPhotoTextView.setText(R.string.default_text_view);
                } else {
                    mPhotoImageView.setImageResource(R.drawable.bickle_up_close);
                    mSetDefaultImage = true;
                    mPhotoTextView.setText(R.string.next_image_text_view);
                }
            }
        }
        );
        return v;
    }

    // next set up listener and add a juicy image!
}
