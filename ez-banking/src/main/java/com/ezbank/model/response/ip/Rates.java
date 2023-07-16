
package com.ezbank.model.response.ip;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Rates {

    @JsonProperty("EURUSD")
    private Double mEURUSD;

}
