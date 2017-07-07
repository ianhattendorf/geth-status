package com.ianhattendorf.geth.gethstatus.domain.geoip.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ianhattendorf.geth.gethstatus.domain.geoip.GeoInfo;

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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getMetroCode() {
        return metroCode;
    }

    public void setMetroCode(Integer metroCode) {
        this.metroCode = metroCode;
    }

    public GeoInfo toGeoInfo() {
        GeoInfo geoInfo = new GeoInfo();
        geoInfo.setIp(ip);
        geoInfo.setCountryCode(countryCode);
        geoInfo.setCountryName(countryName);
        geoInfo.setRegionName(regionName);
        return geoInfo;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FreeGeoInfo{");
        sb.append("ip='").append(ip).append('\'');
        sb.append(", countryCode='").append(countryCode).append('\'');
        sb.append(", countryName='").append(countryName).append('\'');
        sb.append(", regionCode='").append(regionCode).append('\'');
        sb.append(", regionName='").append(regionName).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", zipCode='").append(zipCode).append('\'');
        sb.append(", timeZone='").append(timeZone).append('\'');
        sb.append(", latitude=").append(latitude);
        sb.append(", longitude=").append(longitude);
        sb.append(", metroCode=").append(metroCode);
        sb.append('}');
        return sb.toString();
    }
}
