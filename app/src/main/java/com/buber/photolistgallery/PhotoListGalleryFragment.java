package com.buber.photolistgallery;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Than on 1/5/2017.
 */

public class PhotoListGalleryFragment extends Fragment implements MenuVisible {
    private static final String TAG = "PhotoLGFragment";

    private RecyclerView mPhotoListRecyclerView;
//    private LinearLayoutManager mLinearLayoutManager;
    private GridLayoutManager mGridLayoutManager;
    private List<GalleryItem> mItems = new ArrayList<>();
    private boolean mMenuVisible;

    public static PhotoListGalleryFragment newInstance() {
        return new PhotoListGalleryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        new FetchItemsTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_list_gallery, container, false);

        mPhotoListRecyclerView = (RecyclerView) v
                .findViewById(R.id.fragment_photo_list_gallery_recycler_view);
        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mPhotoListRecyclerView.setLayoutManager(mGridLayoutManager);
        mMenuVisible = false;
        setupAdapter();

        return v;
    }

    private void setupAdapter() {
        if (isAdded()) {
            mPhotoListRecyclerView.setAdapter(new PhotoAdapter(mItems));
        }
    }

    public void setMenuVisible(boolean visible) {
        mMenuVisible = visible;
    }

    private class PhotoHolder extends RecyclerView.ViewHolder
                implements View.OnClickListener {
        private ImageView mItemImageView;
        private ImageView mRatingImageViewStar1;
        private ImageView mRatingImageViewStar2;
        private ImageView mRatingImageViewStar3;
        private ImageView mRatingImageViewStar4;
        private ImageView mRatingImageViewStar5;
        private TextView mNumRatingsPOpen;
        private TextView mNumRatingsPValue;
        private TextView mNumRatingsPClose;
        private TextView mAgeTextView;
        private TextView mAgeTextViewStatic;
        private TextView mStatusTextView;
        private TextView mDistanceTextView;
        private TextView mDistanceTextViewStatic;
        private final Uri ENDPOINT = Uri
                .parse("http://www.google.com/");

        public PhotoHolder(View itemView) {
            super(itemView);

//            mRatingTextView = (TextView) itemView
//                    .findViewById(R.id.fragment_photo_list_gallery_text_rating);
            mRatingImageViewStar1 = (ImageView) itemView
                    .findViewById(R.id.fragment_photo_list_gallery_image_rating1);
            mRatingImageViewStar2 = (ImageView) itemView
                    .findViewById(R.id.fragment_photo_list_gallery_image_rating2);
            mRatingImageViewStar3 = (ImageView) itemView
                    .findViewById(R.id.fragment_photo_list_gallery_image_rating3);
            mRatingImageViewStar4 = (ImageView) itemView
                    .findViewById(R.id.fragment_photo_list_gallery_image_rating4);
            mRatingImageViewStar5 = (ImageView) itemView
                    .findViewById(R.id.fragment_photo_list_gallery_image_rating5);
            mRatingImageViewStar2.setImageResource(R.drawable.ic_star_gold_full_rev1_cropped_scaled3a);
            mRatingImageViewStar3.setImageResource(R.drawable.ic_star_gold_full_rev1_cropped_scaled3a);
            mRatingImageViewStar4.setImageResource(R.drawable.ic_star_gold_full_rev1_cropped_scaled3a);
            mNumRatingsPOpen = (TextView) itemView
                    .findViewById(R.id.fragment_photo_list_gallery_text_num_ratings_p_open);
            mNumRatingsPValue = (TextView) itemView
                    .findViewById(R.id.fragment_photo_list_gallery_text_num_ratings_value);
            mNumRatingsPClose = (TextView) itemView
                    .findViewById(R.id.fragment_photo_list_gallery_text_num_ratings_p_close);
            mNumRatingsPOpen.setText(R.string.num_ratings_p_open);
            mNumRatingsPClose.setText(R.string.num_ratings_p_close);
            mAgeTextView = (TextView) itemView
                    .findViewById(R.id.fragment_photo_list_gallery_text_age);
            mAgeTextViewStatic = (TextView) itemView
                    .findViewById(R.id.fragment_photo_list_gallery_text_age_static);
            mAgeTextViewStatic.setText(R.string.age_static);
            mStatusTextView = (TextView) itemView
                    .findViewById(R.id.fragment_photo_list_gallery_text_status);
            mDistanceTextView = (TextView) itemView
                    .findViewById(R.id.fragment_photo_list_gallery_text_distance);
            mDistanceTextViewStatic = (TextView) itemView
                    .findViewById(R.id.fragment_photo_list_gallery_text_distance_static);
            mDistanceTextViewStatic.setText(R.string.distance_static);
            mItemImageView = (ImageView) itemView
                    .findViewById(R.id.fragment_photo_list_gallery_image_view);
            itemView.setOnClickListener(this);
        }

        //        public void bindGalleryItem(GalleryItem item) {
        //            mTitleTextView.setText(item.toString());
        //        }

        public void bindGalleryItem(String tagline, String rsa) {
//            mRatingTextView.setText(tagline);
            Integer age2 = Integer.valueOf(rsa) - 1;
            mAgeTextView.setText(age2.toString());
            String stat = "ONLINE";
            mStatusTextView.setText(stat);
            mStatusTextView.setTextColor(Color.GREEN);
//            mStatusTextView.setTextColor(Color.DKGRAY);
//            mStatusTextView.setTextColor(Color.LTGRAY);
            mRatingImageViewStar4.setImageResource(R.drawable.ic_star_gold_half_rev1_cropped_scaled3a);
            String numRatings = "865";
            mNumRatingsPValue.setText(numRatings);
            mDistanceTextView.setText(rsa);
        }

        public void bindGalleryItem2(String tagline, String rsa) {
            mAgeTextView.setText(tagline);
            mDistanceTextView.setText(rsa);
        }

        public void bindDrawable(Drawable drawable) {
            mItemImageView.setImageDrawable(drawable);
        }

        @Override
        public void onClick(View v) {
            // implicit, dont use            Intent i = new Intent(Intent.ACTION_VIEW, ENDPOINT);
            if (!mMenuVisible) {
                Intent i = PhotoPageActivity
                        .newIntent(getActivity(), ENDPOINT);
                startActivity(i);
            }
        }

    }

    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {
        private List<GalleryItem> mGalleryItems;
        private boolean mSafeMode;

        public PhotoAdapter(List<GalleryItem> galleryItems) {
            mGalleryItems = galleryItems;
            mSafeMode = true;
        }

        @Override
        public PhotoHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.gallery_item, viewGroup, false);
            return new PhotoHolder(view);
        }

        @Override
        public void onBindViewHolder(PhotoHolder photoHolder, int position) {
            GalleryItem galleryItem = mGalleryItems.get(position);
            String tagLine = "star star";
            String rating = "19";
            photoHolder.bindGalleryItem(tagLine, rating);
            tagLine = "ONLINE";
            rating = "1.9 mi";
//            photoHolder.bindGalleryItem(2, tagLine, rating);
            Drawable placeholder = getResources().getDrawable(R.drawable.bill_up_close);
            if (mSafeMode) {
                placeholder = getResources().getDrawable(R.drawable.circle_eyeball1);
            }
            photoHolder.bindDrawable(placeholder);
//            photoHolder.bindGalleryItem(galleryItem);
        }

        @Override
        public int getItemCount() {
            return mGalleryItems.size();
        }
    }

    private class FetchItemsTask extends AsyncTask<Void, Void, List<GalleryItem>> {
        @Override
        protected List<GalleryItem> doInBackground(Void... params) {
            return new FlickrFetchr().fetchItems();
        }

        @Override
        protected void onPostExecute(List<GalleryItem> items) {
            mItems = items;
            setupAdapter();
        }
    }
}
