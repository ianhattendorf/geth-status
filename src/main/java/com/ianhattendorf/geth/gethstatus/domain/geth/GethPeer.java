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
    private String id;
    private String name;
    private List<String> caps;
    private GethPeer.Network network;

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
        private String localAddress;
        private String remoteAddress;
        private GeoInfo remoteGeoInfo;

        @JsonIgnore
        public String getLocalAddress() {
            return localAddress;
        }
    }
}
