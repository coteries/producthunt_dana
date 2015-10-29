package com.example.kianfar.producthunt_danakianfar.content;


import android.content.Context;
import android.util.Log;

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


    private static RequestQueue requestQueue ;

    public void getLatestPosts(Context context) {

        requestQueue = Volley.newRequestQueue(context);

        // Request a string response
        StringRequest stringRequest = new StringRequest(Request.Method.GET, DataPool.posts_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ObjectMapper mapper = new ObjectMapper();
                        PostsResponse latestPosts;
                        try {

                            // parse json with jackson
                            latestPosts = mapper.readValue(response, new TypeReference<PostsResponse>() {});

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
        requestQueue.add(stringRequest);

    }


    private void fetchImages() {



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
