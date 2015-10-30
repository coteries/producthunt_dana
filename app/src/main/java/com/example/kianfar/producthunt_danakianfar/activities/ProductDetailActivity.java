package com.example.kianfar.producthunt_danakianfar.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kianfar.producthunt_danakianfar.DataPool;
import com.example.kianfar.producthunt_danakianfar.content.Post;
import com.example.kianfar.producthunt_danakianfar.content.User;
import com.example.kianfar.producthunt_danakianfar.fragments.ProductDetailFragment;
import com.example.kianfar.producthunt_danakianfar.R;
import com.example.kianfar.producthunt_danakianfar.views.ProductHuntLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * An activity representing a single Product detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ProductListActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link ProductDetailFragment}.
 */
public class ProductDetailActivity extends AppCompatActivity {

    public static final String ARG_ITEM_ID = "item_id";

    private Post mItem;

    public ProductDetailActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        String id = getIntent().getStringExtra(ARG_ITEM_ID);
        if (id != null) {
            mItem = DataPool.getPosts_map().get(id);
        }

        // Populate UI
        if (mItem != null) {

            ((ProductHuntLayout) findViewById(R.id.phlayout_container)).setPostAndBindData(mItem);

            int diffHours = 0, diffMinutes = 0;
            StringBuffer buffer = new StringBuffer();
            buffer.append("Via " + mItem.getUser().getName());

            try {
                // time difference
                Date postDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz").parse(mItem.getCreatedAt());
                long diffMSec = new Date().getTime() - postDate.getTime();
                diffHours = (int) (diffMSec / (1000 * 60 * 60));
                diffMinutes = (int) (diffMSec / (1000 * 60));

                buffer.append(" " + (diffHours == 0 ? diffMinutes : diffHours) + " hours ago."); // use minutes or hours
            } catch (Exception e) {
                Log.d("Date Parsing", "Unable to parse date: " + mItem.getCreatedAt());
            } finally {
                ((TextView) findViewById(R.id.text_user_hour)).setText(buffer.toString());
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, ProductListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
