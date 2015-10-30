package com.example.kianfar.producthunt_danakianfar.views;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kianfar.producthunt_danakianfar.DataPool;
import com.example.kianfar.producthunt_danakianfar.R;
import com.example.kianfar.producthunt_danakianfar.content.ImageUrl;
import com.example.kianfar.producthunt_danakianfar.content.Post;
import com.example.kianfar.producthunt_danakianfar.content.User;
import com.example.kianfar.producthunt_danakianfar.fragments.ProductListFragment;

import java.util.ArrayList;

public class ProductHuntLayout extends LinearLayout {

    // the post object that this view is showing
    private Post post;

    public Post getPost() {
        return post;
    }


    public void setPostAndBindData(Post post) {
        this.post = post;
        bindData();
    }

    public ProductHuntLayout(Context context) {
        super(context);
    }

    public ProductHuntLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProductHuntLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ProductHuntLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    // help it populate itself
    private void bindData() {

        // collect pointers to elements on view
        TextView name = (TextView) findViewById(R.id.text_product_title), description = (TextView) findViewById(R.id.text_product_description);
        ImageView profileImage1 = (ImageView) findViewById(R.id.image_profile1);
        ImageView profileImage2 = (ImageView) findViewById(R.id.image_profile2);
        ImageView profileImage3 = (ImageView) findViewById(R.id.image_profile3);
        Button upvoteButton = (Button) findViewById(R.id.button_upvote);


        // bind data to view
        name.setText(post.getName());
        description.setText(post.getTagline());
        upvoteButton.setText(post.getVotes_count() + "");

        // set button on click listener
        upvoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Button) v).setText(Integer.toString(getPost().getVotes_count() + 1));
                Toast.makeText(getContext(), "Upvote is not truly submitted to the server, since user account auth is required.", Toast.LENGTH_SHORT).show();
            }
        });

        // use picasso to load image from disk
        ImageView[] pics = {profileImage1, profileImage2, profileImage3};
        for (int i = 0 ; i < post.getMakers().size() ; i++) {
            User maker = post.getMakers().get(i);
            String path = DataPool.imagePath + maker.getId() + ".jpg";
            Bitmap myBitmap = BitmapFactory.decodeFile(path);

            Log.d("Image Loading", "Loading image for " + post.getName() + ", user:" + maker.getName() + " id:" + maker.getId());
            pics[i].setImageBitmap(myBitmap);
            pics[i].getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
            pics[i].getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
        }

    }
}
