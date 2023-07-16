
package com.ezbank.model.response.ip;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
@SuppressWarnings("unused")
public class UserAgent {

    @JsonProperty("browser")
    private String browser;
    @JsonProperty("browserVersion")
    private String browserVersion;
    @JsonProperty("isBot")
    private Boolean isBot;
    @JsonProperty("isDesktop")
    private Boolean isDesktop;
    @JsonProperty("isMobile")
    private Boolean isMobile;
    @JsonProperty("isRaspberry")
    private Boolean isRaspberry;
    @JsonProperty("isSmartTV")
    private Boolean isSmartTV;
    @JsonProperty("isTablet")
    private Boolean isTablet;
    @JsonProperty("isiPod")
    private Boolean isiPod;
    @JsonProperty("operatingSystem")
    private String operatingSystem;
    @JsonProperty("platform")
    private String platform;
    @JsonProperty("source")
    private String source;


}
