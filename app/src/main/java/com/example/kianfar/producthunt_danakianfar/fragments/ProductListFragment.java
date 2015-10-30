package com.example.kianfar.producthunt_danakianfar.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kianfar.producthunt_danakianfar.DataPool;
import com.example.kianfar.producthunt_danakianfar.activities.ProductListActivity;
import com.example.kianfar.producthunt_danakianfar.content.DatabaseHelper;
import com.example.kianfar.producthunt_danakianfar.content.ProductHuntFacade;
import com.example.kianfar.producthunt_danakianfar.content.ProducthuntCursorAdapter;
import com.example.kianfar.producthunt_danakianfar.views.ProductHuntLayout;

/**
 * A list fragment representing a list of Products. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link ProductDetailFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class ProductListFragment extends ListFragment implements View.OnClickListener {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;


    private DatabaseHelper databaseHelper;
    public static ProductListFragment currentFragment;

    @Override
    public void onClick(View v) {
        Integer id = ((ProductHuntLayout) v).getPost().getId();
        onListItemClick(getListView(), v, 1, Long.valueOf(id));
    }


    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(String id);
    }


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProductListFragment() {
        currentFragment = this;
    }

    public static boolean offlineBrowsing = false, newDataReady = false;
    private SharedPreferences preferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new DatabaseHelper(getActivity());
        preferences = getActivity().getSharedPreferences("com.example.kianfar.producthunt_danakianfar", Activity.MODE_PRIVATE);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCallbacks = new Callbacks() {
            @Override
            public void onItemSelected(String id) {
                Log.d("Master View Fragment", "item selected! " + id);
                ((ProductListActivity) getActivity()).onItemSelected(id);
            }
        };

        // a boolean flag to signal offline browsing to the user
        if (offlineBrowsing) {
            Toast.makeText(currentFragment.getActivity(), "Offline browsing..", Toast.LENGTH_SHORT);
        }

        // Note: the ProductListActivity automatically sends requests to the api for new data

        // setup the database connection
        if (preferences.getBoolean("openedBefore", false)) {
            storeAndBindData();
        } else {
            if (offlineBrowsing) {
                Toast.makeText(currentFragment.getActivity(), "You need an internet connection to recieve data.", Toast.LENGTH_SHORT);
            } else {
                Toast.makeText(currentFragment.getActivity(), "Downloading data, please wait", Toast.LENGTH_SHORT);
            }
        }

        if (newDataReady) {
            preferences.edit().putBoolean("openedBefore", true);
            storeAndBindData();
        }

    }


    /**
     * Once the access_token is received, this method is called by its parent activity to get data from producthunt
     */
    public static void fetchData(boolean tokenObtained) {

        // if no token has been obtained (no internet connection), then continue to offline browsing
        if (tokenObtained) {
            ProductHuntFacade facade = new ProductHuntFacade();
            facade.fetchLatestPosts(currentFragment.getActivity());
        } else {
            offlineBrowsing = true;
            onApiCallComplete();
        }
    }


    private void storeAndBindData() {
        Log.d("API", " refreshing data...");
        currentFragment.databaseHelper.storePostsInDB(DataPool.getPosts_list());
        currentFragment.bindDBToAdapter();
//        getListView().invalidateViews(); // force redraw, android is buggy
    }


    /**
     * Once all data has been received from Product Hunt, this method is invoked to write the data to the database and to bind the views to the database
     */
    public static void onApiCallComplete() {
        newDataReady = true;
        if (currentFragment.isVisible()) { // if this happens post-inflating
            currentFragment.storeAndBindData();
        }
    }


    /**
     * Sets up the list adapter
     */
    public void bindDBToAdapter() {
        Log.d("Adapter", "Setting up adapter");
        SQLiteDatabase db = new DatabaseHelper(getActivity()).getReadableDatabase();
        // do select query on data
        Cursor cursor = db.rawQuery(DatabaseHelper.select_latest_products, null);

        // bind everything together
        ProducthuntCursorAdapter adapter = new ProducthuntCursorAdapter(getActivity(), cursor, 0);
        setListAdapter(adapter);
//        adapter.notifyDataSetChanged(); // force it to render
        Log.d("Adapter", "Done!");
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

//        // Activities containing this fragment must implement its callbacks.
//        if (!(activity instanceof Callbacks)) {
//            throw new IllegalStateException("Activity must implement fragment's callbacks.");
//        }

//        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        Log.d("Master View Fragment", "item selected! pos:" + position + ", id:" + id);
        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected.
        mCallbacks.onItemSelected(DataPool.getPosts_list().get(position).getId() + "");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        databaseHelper.close();
    }

}
