
package com.example.kianfar.producthunt_danakianfar.content;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "100px"
})
public class ImageUrl {


    @JsonProperty("100px")
    private String _100px;


    public ImageUrl(String _100px) {
        this._100px = _100px;
    }


    /**
     * @return The _100px
     */
    @JsonProperty("100px")
    public String get100px() {
        return _100px;
    }

    /**
     * @param _100px The 100px
     */
    @JsonProperty("100px")
    public void set100px(String _100px) {
        this._100px = _100px;
    }

    public ImageUrl with100px(String _100px) {
        this._100px = _100px;
        return this;
    }


}
