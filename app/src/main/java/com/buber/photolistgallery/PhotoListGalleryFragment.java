package com.buber.photolistgallery;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Than on 1/5/2017.
 */

public class PhotoListGalleryFragment extends Fragment {
    private static final String TAG = "PhotoLGFragment";

    private RecyclerView mPhotoListRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private GridLayoutManager mGridLayoutManager;
    private List<GalleryItem> mItems = new ArrayList<>();

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
//        mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mPhotoListRecyclerView.setLayoutManager(mGridLayoutManager);

        setupAdapter();

        return v;
    }

    private void setupAdapter() {
        if (isAdded()) {
            mPhotoListRecyclerView.setAdapter(new PhotoAdapter(mItems));
        }
    }

    private class PhotoHolder extends RecyclerView.ViewHolder
                implements View.OnClickListener {
        private ImageView mItemImageView;
        private TextView mTitleTextView;
        private TextView mTitleTextView2;
        private final Uri ENDPOINT = Uri
                .parse("http://www.google.com/");

        public PhotoHolder(View itemView) {
            super(itemView);

            mTitleTextView = (TextView) itemView
                    .findViewById(R.id.fragment_photo_list_gallery_text_view);
            mTitleTextView2 = (TextView) itemView
                    .findViewById(R.id.fragment_photo_list_gallery_text_view2);
            mItemImageView = (ImageView) itemView
                    .findViewById(R.id.fragment_photo_list_gallery_image_view);
            itemView.setOnClickListener(this);
        }

//        public void bindGalleryItem(GalleryItem item) {
//            mTitleTextView.setText(item.toString());
//        }

        public void bindGalleryItem(String tagline, String rsa) {
            mTitleTextView.setText(tagline);
            mTitleTextView2.setText(rsa);
        }

        public void bindDrawable(Drawable drawable) {
            mItemImageView.setImageDrawable(drawable);
        }

        @Override
        public void onClick(View v) {
// implicit, dont use            Intent i = new Intent(Intent.ACTION_VIEW, ENDPOINT);
            Intent i = PhotoPageActivity
                    .newIntent(getActivity(), ENDPOINT);
            startActivity(i);
        }
    }

    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {
        private List<GalleryItem> mGalleryItems;

        public PhotoAdapter(List<GalleryItem> galleryItems) {
            mGalleryItems = galleryItems;
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
            String tagLine = "Big Orange Beaver";
            String rating = "rating: 4.9 ONLINE age: 19";
            photoHolder.bindGalleryItem(tagLine, rating);
            Drawable placeholder = getResources().getDrawable(R.drawable.bill_up_close);
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
