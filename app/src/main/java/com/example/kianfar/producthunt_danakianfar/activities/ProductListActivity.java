package com.example.kianfar.producthunt_danakianfar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.kianfar.producthunt_danakianfar.fragments.ProductDetailFragment;
import com.example.kianfar.producthunt_danakianfar.fragments.ProductListFragment;
import com.example.kianfar.producthunt_danakianfar.R;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * An activity representing a list of Products. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ProductDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ProductListFragment} and the item details
 * (if present) is a {@link ProductDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link ProductListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class ProductListActivity extends AppCompatActivity
        implements ProductListFragment.Callbacks {


    private String oauth_url = "https://api.producthunt.com/v1/oauth/token", posts_url = "https://api.producthunt.com/v1/posts/tech";
    private ObjectMapper mapper = new ObjectMapper();

    private void fetchProductsFromAPI() {



    }


    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_app_bar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

    }

    /**
     * Callback method from {@link ProductListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        // In single-pane mode, simply start the detail activity
        // for the selected item ID.
        Intent detailIntent = new Intent(this, ProductDetailActivity.class);
        detailIntent.putExtra(ProductDetailFragment.ARG_ITEM_ID, id);
        startActivity(detailIntent);
    }
}
