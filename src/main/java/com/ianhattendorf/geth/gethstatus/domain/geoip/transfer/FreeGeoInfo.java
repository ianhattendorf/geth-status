package com.ianhattendorf.geth.gethstatus.domain.geoip.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ianhattendorf.geth.gethstatus.domain.geoip.GeoInfo;
import lombok.Data;

@Data
public class FreeGeoInfo {
    private String ip;
    @JsonProperty("country_code")
    private String countryCode;
    @JsonProperty("country_name")
    private String countryName;
    @JsonProperty("region_code")
    private String regionCode;
    @JsonProperty("region_name")
    private String regionName;
    private String city;
    @JsonProperty("zip_code")
    private String zipCode;
    @JsonProperty("time_zone")
    private String timeZone;
    private Double latitude;
    private Double longitude;
    @JsonProperty("metro_code")
    private Integer metroCode;

    public GeoInfo toGeoInfo() {
        GeoInfo geoInfo = new GeoInfo();
        geoInfo.setCountryCode(countryCode);
        geoInfo.setCountryName(countryName);
        return geoInfo;
    }
}
