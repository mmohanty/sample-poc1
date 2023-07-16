
package com.ezbank.model.response.ip;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class En {

    @JsonProperty("code")
    private String mCode;
    @JsonProperty("name")
    private String mName;
    @JsonProperty("native")
    private String mNative;

}
