package com.example.kianfar.producthunt_danakianfar.content;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "posts"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostsResponse {

    public PostsResponse() {
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