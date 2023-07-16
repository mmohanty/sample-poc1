
package com.ezbank.model.response.ip;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
@SuppressWarnings("unused")
public class Security {

    @JsonProperty("isBogon")
    private Boolean isBogon;
    @JsonProperty("isCloud")
    private Boolean isCloud;
    @JsonProperty("isHosting")
    private Boolean isHosting;
    @JsonProperty("isProxy")
    private Boolean isProxy;
    @JsonProperty("isSpamhaus")
    private Boolean isSpamhaus;
    @JsonProperty("isTorExitNode")
    private Boolean isTorExitNode;
    @JsonProperty("network")
    private String network;
    @JsonProperty("suggestion")
    private String suggestion;

}
