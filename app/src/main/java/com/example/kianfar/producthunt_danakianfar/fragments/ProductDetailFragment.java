package com.example.kianfar.producthunt_danakianfar.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kianfar.producthunt_danakianfar.DataPool;
import com.example.kianfar.producthunt_danakianfar.R;
import com.example.kianfar.producthunt_danakianfar.activities.ProductDetailActivity;
import com.example.kianfar.producthunt_danakianfar.activities.ProductListActivity;
import com.example.kianfar.producthunt_danakianfar.content.Post;

/**
 * A fragment representing a single Product detail screen.
 * This fragment is either contained in a {@link ProductListActivity}
 * in two-pane mode (on tablets) or a {@link ProductDetailActivity}
 * on handsets.
 */
public class ProductDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Post mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProductDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DataPool.getPosts_map().get(Integer.valueOf(getArguments().getString(ARG_ITEM_ID)));

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_product_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.text_product_title)).setText(mItem.getName());
            ((TextView) rootView.findViewById(R.id.text_product_description)).setText(mItem.getTagline());
            ((Button) rootView.findViewById(R.id.button_upvote)).setText(Integer.toString(mItem.getVotes_count()));

            ImageView profileImage1 = (ImageView) rootView.findViewById(R.id.image_profile1);
            ImageView profileImage2 = (ImageView) rootView.findViewById(R.id.image_profile2);
            ImageView profileImage3 = (ImageView) rootView.findViewById(R.id.image_profile3);

            // use picasso to load image from disk
            ImageView[] pics = {profileImage1, profileImage2, profileImage3};
            for (int i = 0; i < mItem.getMakers().size(); i++) {
                String path = DataPool.imagePath + mItem.getMakers().get(i).getId() + ".jpg";
                Bitmap myBitmap = BitmapFactory.decodeFile(path);

                Log.d("Image Loading", "Loading image for " + mItem.getName());
                pics[i].setImageBitmap(myBitmap);
                pics[i].getLayoutParams().width = 80;
                pics[i].getLayoutParams().height = 80;
                pics[i].requestLayout();
            }
        }
        return rootView;
    }
}
