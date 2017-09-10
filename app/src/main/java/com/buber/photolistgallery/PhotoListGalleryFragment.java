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
import android.support.v4.util.Pair;
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
    private GridLayoutManager mGridLayoutManager;
    private boolean mMenuVisible;
    private DriverAdapter mAdapter;

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
        updateUI(null);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI(null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mAdapter = null;
    }

    private void updateUI(List<Driver> items) {
        DriverLab driverLab = DriverLab.get(getActivity());
        List<Driver> drivers;
        if (items == null) {
            drivers = driverLab.getDrivers();
        } else {
            drivers = items;
        }

        Log.d(TAG, "updateUI(): Drivers = ");
        Integer dnum = 1;
        for (Driver d : drivers) {
            Log.d(TAG, "updateUI(): Driver " + dnum.toString() + ": Name = " + d.getStageName());
            dnum += 1;
        }

        if (mAdapter == null) {
            mAdapter = new DriverAdapter(drivers);
            mPhotoListRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    public void setMenuVisible(boolean visible) {
        mMenuVisible = visible;
    }

    private class DriverHolder extends RecyclerView.ViewHolder
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

        public DriverHolder(View itemView) {
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
            mRatingImageViewStar1.setImageResource(R.drawable.ic_star_gold_full_rev1_cropped_scaled3a);
            mRatingImageViewStar2.setImageResource(R.drawable.ic_star_gold_full_rev1_cropped_scaled3a);
            mRatingImageViewStar3.setImageResource(R.drawable.ic_star_gold_full_rev1_cropped_scaled3a);
            mRatingImageViewStar4.setImageResource(R.drawable.ic_star_gold_full_rev1_cropped_scaled3a);
            mRatingImageViewStar5.setImageResource(R.drawable.ic_star_gold_full_rev1_cropped_scaled3a);
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

        public void bindDriver(Driver driver) {
            String age = driver.getAgeFormatted();
            mAgeTextView.setText(age);
            mStatusTextView.setText(driver.getStatus());
            Integer endStarNumber = driver.getEndStarNumber();
            int partialStarResource = driver.getPartialStarResource();
            ImageView ratingStarView;
            for (int i=0; i<5; i++) {
                ratingStarView = getRatingStarView(i);
                if (ratingStarView == null) {
                    continue;
                } else if (i < endStarNumber) {
                    ratingStarView.setImageResource(R.drawable.ic_star_gold_full_rev1_cropped_scaled3a);
                } else if (i == endStarNumber) {
                    ratingStarView.setImageResource(partialStarResource);
                }
                else {
                    ratingStarView.setImageResource(R.drawable.ic_star_gold_empty_rev1_cropped_scaled3a);
                }
            }
            // do nothing test
            Integer numRatings = driver.getNumRatings();
            String retRating = driver.getRatingFormatted();
// use for debugging stars           mNumRatingsPValue.setText(retRating);
            mNumRatingsPValue.setText(numRatings.toString());
            String distance = driver.getDistanceFormatted();
            mDistanceTextView.setText(distance);
            Log.d(TAG, "bindDriver(): Age = " + age + ". Rating = " + retRating + ". endStarIndex = " +  endStarNumber.toString() + ". Distance = " + distance);
        }

        private ImageView getRatingStarView(int index) {
            ImageView retview;
            switch (index) {
                case 0:
                    retview = mRatingImageViewStar1;
                    break;
                case 1:
                    retview = mRatingImageViewStar2;
                    break;
                case 2:
                    retview = mRatingImageViewStar3;
                    break;
                case 3:
                    retview = mRatingImageViewStar4;
                    break;
                case 4:
                    retview = mRatingImageViewStar5;
                    break;
                default:
                    retview = null;
            }
            return retview;
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

    private class DriverAdapter extends RecyclerView.Adapter<DriverHolder> {
        private List<Driver> mDrivers;
        private boolean mSafeMode;

        public DriverAdapter(List<Driver> drivers) {
            mDrivers = drivers;
            mSafeMode = true;
        }

        @Override
        public DriverHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.gallery_item, viewGroup, false);
            return new DriverHolder(view);
        }

        @Override
        public void onBindViewHolder(DriverHolder holder, int position) {
            Driver driver = mDrivers.get(position);
            holder.bindDriver(driver);
        }

        @Override
        public int getItemCount() {
            return mDrivers.size();
        }
    }

    private class FetchItemsTask extends AsyncTask<Void, Void, List<Driver>> {
        @Override
        protected List<Driver> doInBackground(Void... params) {
            return new FlickrFetchr().fetchItems();
        }

        @Override
        protected void onPostExecute(List<Driver> items) {
            updateUI(items);
        }
    }

}
