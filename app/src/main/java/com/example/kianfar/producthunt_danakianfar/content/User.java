
package com.example.kianfar.producthunt_danakianfar.content;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name",
        "image_url"
})
public class User {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("image_url")
    private ImageUrl imageUrl;


    public User(Integer id, String name, ImageUrl imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
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

    public User withId(Integer id) {
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

    public User withName(String name) {
        this.name = name;
        return this;
    }


    /**
     * @return The imageUrl
     */
    @JsonProperty("image_url")
    public ImageUrl getImageUrl() {
        return imageUrl;
    }

    /**
     * @param imageUrl The image_url
     */
    @JsonProperty("image_url")
    public void setImageUrl(ImageUrl imageUrl) {
        this.imageUrl = imageUrl;
    }

    public User withImageUrl(ImageUrl imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

}
