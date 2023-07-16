
package com.ezbank.model.response.ip;

import java.util.List;
import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class IPLocation {

    @JsonProperty("borders")
    private List<String> borders;
    @JsonProperty("capital")
    private String capital;
    @JsonProperty("city")
    private String city;
    @JsonProperty("connection")
    private Connection connection;
    @JsonProperty("continentCode")
    private String continentCode;
    @JsonProperty("continentName")
    private String continentName;
    @JsonProperty("countryCode")
    private String countryCode;
    @JsonProperty("countryFlagEmoj")
    private String countryFlagEmoj;
    @JsonProperty("countryFlagEmojUnicode")
    private String countryFlagEmojUnicode;
    @JsonProperty("countryName")
    private String countryName;
    @JsonProperty("countryNameNative")
    private String countryNameNative;
    @JsonProperty("currency")
    private Currency currency;
    @JsonProperty("ip")
    private String ip;
    @JsonProperty("isEu")
    private Boolean isEu;
    @JsonProperty("languages")
    private Languages languages;
    @JsonProperty("latitude")
    private Double latitude;
    @JsonProperty("longitude")
    private Double longitude;
    @JsonProperty("officialCountryName")
    private String officialCountryName;
    @JsonProperty("phoneCode")
    private String phoneCode;
    @JsonProperty("postalCode")
    private String postalCode;
    @JsonProperty("regionCode")
    private String regionCode;
    @JsonProperty("regionName")
    private String regionName;
    @JsonProperty("security")
    private Security security;
    @JsonProperty("timeZone")
    private TimeZone timeZone;
    @JsonProperty("topLevelDomains")
    private List<String> topLevelDomains;
    @JsonProperty("userAgent")
    private UserAgent userAgent;


}
