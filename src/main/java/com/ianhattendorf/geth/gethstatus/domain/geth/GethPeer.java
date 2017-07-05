package com.ianhattendorf.geth.gethstatus.domain.geth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ianhattendorf.geth.gethstatus.domain.geoip.GeoInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@JsonIgnoreProperties("protocols")
public class GethPeer {
    String id;
    String name;
    List<String> caps;
    GethPeer.Network network;

    @JsonIgnore
    public String getId() {
        return id;
    }

    @JsonIgnore
    public List<String> getCaps() {
        return caps;
    }

    @Data
    @EqualsAndHashCode(exclude = {"remoteGeoInfo"})
    public static class Network {
        String localAddress;
        String remoteAddress;
        GeoInfo remoteGeoInfo;

        @JsonIgnore
        public String getLocalAddress() {
            return localAddress;
        }
    }
}
