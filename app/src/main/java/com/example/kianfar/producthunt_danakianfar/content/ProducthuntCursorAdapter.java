package com.example.kianfar.producthunt_danakianfar.content;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kianfar.producthunt_danakianfar.DataPool;
import com.example.kianfar.producthunt_danakianfar.R;
import com.example.kianfar.producthunt_danakianfar.views.ProductHuntLayout;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;


public class ProducthuntCursorAdapter extends CursorAdapter {


    public ProducthuntCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.short_product_item, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        ProductHuntLayout layout = (ProductHuntLayout) view;

        // collect all data
        TextView name = (TextView) view.findViewById(R.id.text_product_title), description = (TextView) view.findViewById(R.id.text_product_description);
        ImageView profileImage = (ImageView) view.findViewById(R.id.image_profile);
        Button upvoteButton = (Button) view.findViewById(R.id.button_upvote);


//        "SELECT p.id, p.votes_count, p.name, p.tagline, p.day_, p.created_at, p.redirect_url, p.user_id, u2.name as user_name, u.id as makerid, u.name as makername, u.imageurl as makerimage";
        Post post = new Post(
                cursor.getInt(0), // id
                cursor.getInt(1), // votes_count
                cursor.getString(2), // name
                cursor.getString(3), // tagline
                cursor.getString(4), // day
                cursor.getString(5), //created at
                cursor.getString(6), // redirect url
                new User(cursor.getInt(7), // user id
                        cursor.getString(8), // user name
                        null),
                new ArrayList<User>());

        // add makers to post object
        while (true) {

            // Since there is a 1-many relationship between product and maker, we need to try to find
            // all repeating sections
            cursor.moveToNext();


            if (cursor.getPosition() < cursor.getCount() && cursor.getInt(0) == post.getId()) { // if the next row in the cursor has the same post id, then continue

                post.getMakers().add(new User(
                        cursor.getInt(9),
                        cursor.getString(10),
                        new ImageUrl(cursor.getString(11))));
            } else { // otherwise its not the same post, break loop and move on
                cursor.moveToPrevious();
                break;
            }

        }


        // If not already in memory, Add to datapool
        if (!DataPool.getPosts_map().containsKey(post.getId())) {
            DataPool.getPosts_map().put(post.getId(), post);
            DataPool.getPosts_list().add(post);
        }

        // bind data to view
        name.setText(post.getName());
        description.setText(post.getTagline());
        upvoteButton.setText(post.getVotes_count() + "");

        layout.setPost(post);

        // set button on click listener
        upvoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(context.getResources().getColor(R.color.colorButton));
                ProductHuntLayout parentLayout = (ProductHuntLayout) v.getParent();
                ((Button) v).setText(Integer.toString(parentLayout.getPost().getVotes_count() + 1));
                Toast.makeText(context, "Upvote is not truly submitted to the server, since user account auth is required.", Toast.LENGTH_SHORT).show();
            }
        });


        // use picasso to load image from disk
        // TODO add up to 3 photos
        if (post.getMakers().size() > 0) {
            String path = DataPool.imagePath + post.getMakers().get(0).getId() + ".jpg";

            Bitmap myBitmap = BitmapFactory.decodeFile(path);

            profileImage.setImageBitmap(myBitmap);


//            Picasso.with(context)
//                    .load(path)
//                    .fit()
//                    .tag(context)
//                    .into(profileImage);
        }
    }


}
