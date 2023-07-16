
package com.ezbank.model.response.ip;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Currency {

    @JsonProperty("code")
    private String mCode;
    @JsonProperty("name")
    private String mName;
    @JsonProperty("number")
    private String mNumber;
    @JsonProperty("rates")
    private Rates mRates;
    @JsonProperty("symbol")
    private String mSymbol;

}
