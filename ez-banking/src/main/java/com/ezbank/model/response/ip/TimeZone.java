
package com.ezbank.model.response.ip;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
@SuppressWarnings("unused")
public class TimeZone {

    @JsonProperty("code")
    private String code;
    @JsonProperty("currentTime")
    private String currentTime;
    @JsonProperty("id")
    private String id;
    @JsonProperty("timeZoneName")
    private String timeZoneName;
    @JsonProperty("utcOffset")
    private Long utcOffset;


}
