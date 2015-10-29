package com.example.kianfar.producthunt_danakianfar.content;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kianfar.producthunt_danakianfar.DataPool;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProductHuntFacade {


    private static RequestQueue volleyRequestQueue;
    private ImageHelper imageHelper;
    private Context context;

    public void fetchLatestPosts(final Context context) {

        Log.d("Product Hunt API", "Calling API to get latest posts..");

        this.context = context;
        imageHelper = new ImageHelper(context);

        // setup request queue. Volley handles network calls in the background
        volleyRequestQueue = Volley.newRequestQueue(context);

        // Request a string response
        StringRequest stringRequest = new StringRequest(Request.Method.GET, DataPool.posts_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) { //after the api responds

                        ObjectMapper mapper = new ObjectMapper();
                        PostsResponse latestPosts;
                        try {

                            // parse json with jackson
                            latestPosts = mapper.readValue(response, new TypeReference<PostsResponse>() {
                            });

                            List<Post> postList = new ArrayList<>();
                            Map<Integer, Post> postMap = new HashMap<Integer, Post>();

                            // Arrange in lists
                            for (Post post : latestPosts.getPosts()) {
                                postList.add(post);
                                postMap.put(post.getId(), post);
                            }

                            // Put data in memory
                            DataPool.clearData();
                            DataPool.setPostsList(postList);
                            DataPool.setPostsMap(postMap);


//                            // call routine to store to db
//                            new DatabaseHelper(context).storePostsInDB(DataPool.getPosts_list());

                            // fetch images from server
                            fetchImages();

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
        // for each post, get the image urls of its makers
        for (Post post : DataPool.getPosts_list()) {
            for (User user : post.getMakers()) {
                String temp = user.getImageUrl().get100px();
                if (temp.length() > 0)
                    sendVolleyRequest(temp, user.getId()+"");
            }
        }
    }


    /**
     * Fetch Images from the ProductHunt API via the Volley networking library
     * @param url url pointing to where image is stored
     * @param userid the id of the user will be used as the filename for the image
     */
    private void sendVolleyRequest(String url, final String userid) {
        ProductHuntImageRequest imgRequest = new ProductHuntImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imageHelper.write2disk(response, userid);
                    }
                }, 0, 0, ImageView.ScaleType.FIT_XY, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                mImageView.setBackgroundColor(Color.parseColor("#ff0000"));
                error.printStackTrace();
            }
        });

        volleyRequestQueue.add(imgRequest);
    }


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "posts"
    })
    private class PostsResponse {

        private PostsResponse() {
        }

        @JsonProperty("posts")
        private List<Post> posts = new ArrayList<Post>();

        @JsonProperty("posts")
        public List<Post> getPosts() {
            return posts;
        }

        @JsonProperty("posts")
        public void setPosts(List<Post> posts) {
            this.posts = posts;
        }
    }

}
