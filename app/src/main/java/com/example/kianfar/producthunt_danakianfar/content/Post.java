
package com.example.kianfar.producthunt_danakianfar.content;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name",
        "tagline",
        "day",
        "created_at",
        "redirect_url",
        "user",
        "makers",
        "votes_count"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Post {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("votes_count")
    private Integer votes_count;
    @JsonProperty("name")
    private String name="";
    @JsonProperty("tagline")
    private String tagline="";
    @JsonProperty("day")
    private String day;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("redirect_url")
    private String redirectUrl;
    @JsonProperty("user")
    private User user;
    @JsonProperty("makers")
    private List<User> makers = new ArrayList<User>();

    /**
     * No args constructor for use in serialization
     */
    public Post() {
    }


    public Post(Integer id, Integer votes_count, String name, String tagline, String day, String createdAt, String redirectUrl, User user, List<User> makers) {
        this.id = id;
        this.votes_count = votes_count;
        this.name = name;
        this.tagline = tagline;
        this.day = day;
        this.createdAt = createdAt;
        this.redirectUrl = redirectUrl;
        this.user = user;
        this.makers = makers;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("votes_count")
    public Integer getVotes_count() {
        return votes_count;
    }

    @JsonProperty("votes_count")
    public void setVotes_count(Integer votes_count) {
        this.votes_count = votes_count;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("tagline")
    public String getTagline() {
        return tagline;
    }

    @JsonProperty("tagline")
    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    @JsonProperty("day")
    public String getDay() {
        return day;
    }

    @JsonProperty("day")
    public void setDay(String day) {
        this.day = day;
    }

    @JsonProperty("created_at")
    public String getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("redirect_url")
    public String getRedirectUrl() {
        return redirectUrl;
    }

    @JsonProperty("redirect_url")
    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    @JsonProperty("user")
    public User getUser() {
        return user;
    }

    @JsonProperty("user")
    public void setUser(User user) {
        this.user = user;
    }

    @JsonProperty("makers")
    public List<User> getMakers() {
        return makers;
    }

    @JsonProperty("makers")
    public void setMakers(List<User> makers) {
        this.makers = makers;
    }

}
