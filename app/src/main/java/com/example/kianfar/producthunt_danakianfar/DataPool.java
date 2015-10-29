package com.example.kianfar.producthunt_danakianfar;

import android.os.Environment;
import android.util.Log;

import com.example.kianfar.producthunt_danakianfar.Utils.HttpUtils;
import com.example.kianfar.producthunt_danakianfar.content.Post;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DataPool {


    public static final String client_id = "c9045503a3d6e4a96b7f9b9f11d932f39a18c604497844d84c298ab9cb8870f2";
    public static final String client_secret = "0185c9340b7523d5d50b94c299df1fdb9ef203ace4be560bb7244c3fb09f0818";
    public static String access_token = "";
    public static final String developer_token = "624e8c5d980163ec65ae4715d1b4819d7ff1a45e4d7af989ba82b54845a4a7b6";
    public static final String oauth_url = "https://api.producthunt.com/v1/oauth/token?client_id=%s&client_secret=%s&grant_type=client_credentials",
            posts_url = "https://api.producthunt.com/v1/posts_map/all?per_page=50";
    private static ObjectMapper mapper = new ObjectMapper();
    public static final String imagePath = Environment.getExternalStorageDirectory().getPath() + "/producthunt_kianfar/";
    private static List<Post> posts_list = new ArrayList<>();
    private static Map<Integer, Post> posts_map = new HashMap<>();


    public static boolean init() {
        Log.d("DataPool", "attempting to get access token");
        // get access token
        // this is an unusual way to handle it, but its the fastest. all other networking operations are done with the Volley library.
        // Volley can't be used here because it requires access to the app's context, which is not available when this methods is called from
        // the class ProductListActivity.
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String response = HttpUtils.makeHttpRequest(String.format(oauth_url, client_id, client_secret), HttpUtils.POST);
                    OauthResponse accessTokenResponse = mapper.readValue(response, new TypeReference<OauthResponse>() {
                    });

                    access_token = accessTokenResponse.accessToken;
                    Log.d("DataPool", "Access token obtained.");

                } catch (IOException e) {
                    Log.e("Http Access Token", e.getMessage());
                    //
                }
            }
        }).start();

        return true;
    }

    public static void clearData() {
        posts_list.clear();
        posts_map.clear();
    }

    public static List<Post> getPosts_list() {
        return posts_list;
    }

    public static Map<Integer, Post> getPosts_map() {
        return posts_map;
    }

    public static void setPostsList(List<Post> postsList) {
        if (posts_list.isEmpty()) {
            posts_list = postsList;
        }
    }

    public static void setPostsMap(Map<Integer, Post> postsMap) {
        if (posts_map.isEmpty()) {
            posts_map = postsMap;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "expires_in",           // in this demonstration, this is ignored
            "access_token"
    })
    private class OauthResponse {

        @JsonProperty("expires_in")
        private Integer expiresIn;
        @JsonProperty("access_token")
        private String accessToken;

        private OauthResponse() {
        }

        @JsonProperty("expires_in")
        public Integer getExpiresIn() {
            return expiresIn;
        }

        @JsonProperty("expires_in")
        public void setExpiresIn(Integer expiresIn) {
            this.expiresIn = expiresIn;
        }

        @JsonProperty("access_token")
        public String getAccessToken() {
            return accessToken;
        }

        @JsonProperty("access_token")
        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }
    }
}
