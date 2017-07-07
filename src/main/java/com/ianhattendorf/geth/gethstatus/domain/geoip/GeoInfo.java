package com.ianhattendorf.geth.gethstatus.domain.geoip;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public class GeoInfo {

    private String ip;
    private String countryCode;
    private String countryName;
    private String regionName;

    @JsonIgnore
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

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeoInfo geoInfo = (GeoInfo) o;
        return Objects.equals(ip, geoInfo.ip) &&
                Objects.equals(countryCode, geoInfo.countryCode) &&
                Objects.equals(countryName, geoInfo.countryName) &&
                Objects.equals(regionName, geoInfo.regionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, countryCode, countryName, regionName);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GeoInfo{");
        sb.append("ip='").append(ip).append('\'');
        sb.append(", countryCode='").append(countryCode).append('\'');
        sb.append(", countryName='").append(countryName).append('\'');
        sb.append(", regionName='").append(regionName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
