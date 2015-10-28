package com.example.kianfar.producthunt_danakianfar;

import android.util.Log;
import com.example.kianfar.producthunt_danakianfar.Utils.HttpUtils;
import com.example.kianfar.producthunt_danakianfar.content.Post;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class DataPool {


    public static final String client_id = "c9045503a3d6e4a96b7f9b9f11d932f39a18c604497844d84c298ab9cb8870f2";
    public static final String client_secret = "0185c9340b7523d5d50b94c299df1fdb9ef203ace4be560bb7244c3fb09f0818";
    public static String access_token = "";
    public static final String developer_token = "624e8c5d980163ec65ae4715d1b4819d7ff1a45e4d7af989ba82b54845a4a7b6";
    public static final String oauth_url = "https://api.producthunt.com/v1/oauth/token?client_id=%s&client_secret=%s&grant_type=client_credentials",
            posts_url = "https://api.producthunt.com/v1/posts/all";
    private static ObjectMapper mapper = new ObjectMapper();
    public static final Map<Integer, Post> posts = new HashMap<>();


    public static boolean init() {

        // get access token
        // this is an unusual way to handle it, but its the fastest. all other networking operations are done with volley
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String response = HttpUtils.makeHttpRequest(String.format(oauth_url, client_id, client_secret), HttpUtils.POST);
                    OauthResponse accessTokenResponse = mapper.readValue(response, new TypeReference<OauthResponse>() {});

                    access_token = accessTokenResponse.accessToken;

                } catch (IOException e) {
                    Log.e("Http Access Token", e.getMessage());
                    //
                }
            }
        }).run();

        return true;
    }


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "expires_in",           // in this demonstration, this is ignored
            "access_token"
    })
    public class OauthResponse {

        @JsonProperty("expires_in")
        private Integer expiresIn;
        @JsonProperty("access_token")
        private String accessToken;

        /**
         * @return The expiresIn
         */
        @JsonProperty("expires_in")
        public Integer getExpiresIn() {
            return expiresIn;
        }

        /**
         * @param expiresIn The expires_in
         */
        @JsonProperty("expires_in")
        public void setExpiresIn(Integer expiresIn) {
            this.expiresIn = expiresIn;
        }


        /**
         * @return The accessToken
         */
        @JsonProperty("access_token")
        public String getAccessToken() {
            return accessToken;
        }

        /**
         * @param accessToken The access_token
         */
        @JsonProperty("access_token")
        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }
    }
}
