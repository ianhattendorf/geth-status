package com.ianhattendorf.geth.gethstatus.domain.geoip.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ianhattendorf.geth.gethstatus.domain.geoip.GeoInfo;
import lombok.Data;
import lombok.val;

@Data
public class FreeGeoInfo {
    String ip;
    @JsonProperty("country_code")
    String countryCode;
    @JsonProperty("country_name")
    String countryName;
    @JsonProperty("region_code")
    String regionCode;
    @JsonProperty("region_name")
    String regionName;
    String city;
    @JsonProperty("zip_code")
    String zipCode;
    @JsonProperty("time_zone")
    String timeZone;
    Double latitude;
    Double longitude;
    @JsonProperty("metro_code")
    Integer metroCode;

    public GeoInfo toGeoInfo() {
        val geoInfo = new GeoInfo();
        geoInfo.setCountryCode(countryCode);
        geoInfo.setCountryName(countryName);
        return geoInfo;
    }
}
