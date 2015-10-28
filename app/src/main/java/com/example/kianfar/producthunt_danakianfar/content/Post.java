
package com.example.kianfar.producthunt_danakianfar.content;

import java.util.ArrayList;
import java.util.List;
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
        "featured",
        "comments_count",
        "votes_count",
        "redirect_url",
        "user",
        "makers",
})
public class Post {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("tagline")
    private String tagline;
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


    public Post(Integer id, String name, String tagline, String day, String redirectUrl, User user, List<User> makers) {
        this.id = id;
        this.name = name;
        this.tagline = tagline;
        this.day = day;
        this.createdAt = createdAt;
        this.redirectUrl = redirectUrl;
        this.user = user;
        this.makers = makers;
    }

    /**
     * @return The id
     */
    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    public Post withId(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * @return The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public Post withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * @return The tagline
     */
    @JsonProperty("tagline")
    public String getTagline() {
        return tagline;
    }

    /**
     * @param tagline The tagline
     */
    @JsonProperty("tagline")
    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public Post withTagline(String tagline) {
        this.tagline = tagline;
        return this;
    }

    /**
     * @return The day
     */
    @JsonProperty("day")
    public String getDay() {
        return day;
    }

    /**
     * @param day The day
     */
    @JsonProperty("day")
    public void setDay(String day) {
        this.day = day;
    }

    public Post withDay(String day) {
        this.day = day;
        return this;
    }

    /**
     * @return The createdAt
     */
    @JsonProperty("created_at")
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt The created_at
     */
    @JsonProperty("created_at")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Post withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    /**
     * @return The redirectUrl
     */
    @JsonProperty("redirect_url")
    public String getRedirectUrl() {
        return redirectUrl;
    }

    /**
     * @param redirectUrl The redirect_url
     */
    @JsonProperty("redirect_url")
    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public Post withRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
        return this;
    }

    /**
     * @return The user
     */
    @JsonProperty("user")
    public User getUser() {
        return user;
    }

    /**
     * @param user The user
     */
    @JsonProperty("user")
    public void setUser(User user) {
        this.user = user;
    }

    public Post withUser(User user) {
        this.user = user;
        return this;
    }

    /**
     * @return The makers
     */
    @JsonProperty("makers")
    public List<User> getMakers() {
        return makers;
    }

    /**
     * @param makers The makers
     */
    @JsonProperty("makers")
    public void setMakers(List<User> makers) {
        this.makers = makers;
    }

    public Post withMakers(List<User> makers) {
        this.makers = makers;
        return this;
    }

}
