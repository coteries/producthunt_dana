package com.example.kianfar.producthunt_danakianfar.content;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kianfar.producthunt_danakianfar.DataPool;
import com.example.kianfar.producthunt_danakianfar.R;
import com.example.kianfar.producthunt_danakianfar.fragments.ProductListFragment;
import com.example.kianfar.producthunt_danakianfar.views.ProductHuntLayout;

import java.util.ArrayList;


public class ProducthuntCursorAdapter extends CursorAdapter {

    private int limit = 0;
    private int previousId;

    public ProducthuntCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.short_product_item, parent, false);
    }

//    @Override
//    public int getCount() { // limits the number of items that are accessed,
//                              but this is a hard limit. to get an infinitely scrolling list, the cursor needs to be replaced
//        return 20;
//    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        ProductHuntLayout productHuntLayout = (ProductHuntLayout) view;
        productHuntLayout.setOnClickListener(ProductListFragment.currentFragment);

        Log.d("Cursor Adapter", "Retrieved " + cursor.getCount() + " items");
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

        // if there are makers, split them via "," since they are aggregated
        if (!(cursor.getString(9) == null || cursor.getString(9).equalsIgnoreCase(null))) {
            String[] makerIds = cursor.getString(9).split(","), makerNames = cursor.getString(10).split(","), makerUrls = cursor.getString(11).split(",");
            for (int i = 0; i < makerIds.length; i++) {
                post.getMakers().add(new User(Integer.valueOf(makerIds[i]), makerNames[i], new ImageUrl(makerUrls[i])));
            }
        }



        // If not already in memory, Add to datapool
        if (!DataPool.getPosts_map().containsKey(post.getId())) {
            DataPool.getPosts_map().put(String.valueOf(post.getId()), post);
            DataPool.getPosts_list().add(post);
        }

        // This binds the object to the view
        productHuntLayout.setPostAndBindData(post);

        // add to map of views on main screens
        DataPool.getPost_views().put(String.valueOf(productHuntLayout.getPost().getId()), productHuntLayout);
    }

}

    /*
    @Override
    public void bindView(View view, final Context context, Cursor cursor) {


            The cursor performs a query that has a repeating section. This implies that multiples rows
            in the cursor will be dedicated to the same record.
            This is a problem since this adapter treats every row as a new record. Without changing this,
            the app would show duplicate posts.
            To counter this, we keep track of the id of the previous row. this way, we can avoid adding this view.

    if (cursor.getInt(0) != previousId) {// filter out unique rows only

        ProductHuntLayout productHuntLayout = (ProductHuntLayout) view.findViewById(R.id.phlayout_container);
        productHuntLayout.setOnClickListener(ProductListFragment.currentFragment);

        Log.d("Cursor Adapter", "Retrieved " + cursor.getCount() + " items");
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


        previousId = post.getId(); // to keep track of the last post. this helps to keep

        // TODO this is buggy
        // add makers to post object
        do {

            if (cursor.getInt(0) == post.getId()) { // if post id of current row in cursor is the same as the next one

                post.getMakers().add(
                        new User(
                                cursor.getInt(9),               // maker id
                                cursor.getString(10),           // maker name
                                new ImageUrl(cursor.getString(11)))); // image url

            } else { // otherwise its not the same post, break loop and move on
                cursor.moveToPrevious();
                break;
            }
            cursor.moveToNext();
        }


        while (cursor.getPosition() < cursor.getCount());

        // If not already in memory, Add to datapool
        if (!DataPool.getPosts_map().containsKey(post.getId())) {
            DataPool.getPosts_map().put(String.valueOf(post.getId()), post);
            DataPool.getPosts_list().add(post);
        }

        // This binds the object to the view
        productHuntLayout.setPostAndBindData(post);

        // add to map of views on main screens
        DataPool.getPost_views().put(String.valueOf(productHuntLayout.getPost().getId()), productHuntLayout);
    }
    else { // duplicate row. destory view
        view.

    }
}
     */

