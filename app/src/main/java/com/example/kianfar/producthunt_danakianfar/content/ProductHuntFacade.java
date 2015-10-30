package com.example.kianfar.producthunt_danakianfar.content;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.kianfar.producthunt_danakianfar.DataPool;
import com.example.kianfar.producthunt_danakianfar.fragments.ProductListFragment;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProductHuntFacade {


    private static RequestQueue volleyRequestQueue;
    private ImageHelper imageHelper;
    private Context context;
    private List<String> downloadedImages = new ArrayList<>();

    public void fetchLatestPosts(final Context context) {

        Log.d("Product Hunt API", "Calling API to get latest posts..");
        this.context = context;


        imageHelper = new ImageHelper(context);
        File imagePath = new File(DataPool.imagePath);

        if (imagePath.exists()) {
            for (File f : imagePath.listFiles()) {
                downloadedImages.add(f.getName().replace(".jpg", ""));
            }
        }

        // setup request queue. Volley handles network calls in the background
        volleyRequestQueue = Volley.newRequestQueue(context);

        // Request a string response
        ProductHuntStringRequest stringRequest = new ProductHuntStringRequest(Request.Method.GET, DataPool.posts_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) { //after the api responds

                        ObjectMapper mapper = new ObjectMapper();
                        PostsResponse latestPosts;
                        try {

                            // parse json with jackson
                            latestPosts = mapper.readValue(response, new TypeReference<PostsResponse>() {
                            });

                            // Arrange in lists
                            for (Post post : latestPosts.getPosts()) {

                                if (!DataPool.getPosts_map().containsKey(post.getId())) {
                                    DataPool.getPosts_map().put(String.valueOf(post.getId()), post);
                                    DataPool.getPosts_list().add(post);
                                }
                            }

                            Log.d("Product Hunt API", "Number of items in memory: " + DataPool.getPosts_list().size());

                            // fetch images from server
                            fetchImages();

                            ProductListFragment.onApiCallComplete(); // store to db and bind views to db

                        } catch (Exception e) {
                            Log.e("Posts Volley Call", "Unsuccessful, response from producthunt: \n" + e.getMessage());
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Error handling
                Log.e("Posts Volley Call", "Unsuccessful, response from producthunt: \n" + error.getMessage());
                error.printStackTrace();

            }
        });


        // Add the request to the queue
        volleyRequestQueue.add(stringRequest);

    }


    private void fetchImages() {
        Log.d("Product Hunt API", "Fetching images from Product hunt...");
        // for each post, get the image urls of its makers
        for (Post post : DataPool.getPosts_list()) {
            for (User user : post.getMakers()) {
                String temp = user.getImageUrl().getImageUrl();
                if (temp.length() > 0) {
                    if (!downloadedImages.contains(user.getId())) { // only download image if not on device already
                        Log.d("Product Hunt API", "calling for image " + user.getId());
                        sendVolleyImageRequest(temp, user.getId() + "");
                    }
                }
            }
        }
    }


    /**
     * Fetch Images from the ProductHunt API via the Volley networking library
     *
     * @param url    url pointing to where image is stored
     * @param userid the id of the user will be used as the filename for the image
     */
    private void sendVolleyImageRequest(final String url, final String userid) {
        ProductHuntImageRequest imgRequest = new ProductHuntImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        Log.d("Product Hunt API", "image received!" + userid);
                        imageHelper.write2disk(response, userid);
                    }
                }, 0, 0, ImageView.ScaleType.FIT_XY, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Product Hunt API", "Unable to get image: " + url);
                error.printStackTrace();
            }
        });

        volleyRequestQueue.add(imgRequest);
    }


}
