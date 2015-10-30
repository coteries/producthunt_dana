
package com.example.kianfar.producthunt_danakianfar.content;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "220px"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageUrl {


    @JsonProperty("220px")
    private String imageUrl;

    public ImageUrl() {}

    public ImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    @JsonProperty("220px")
    public String getImageUrl() {
        return imageUrl;
    }

    @JsonProperty("220px")
    public void setImageUrl(String imageurl) {
        this.imageUrl = imageurl;
    }



}
