package com.ianhattendorf.geth.gethstatus.domain.geth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ianhattendorf.geth.gethstatus.domain.geoip.GeoInfo;

import java.util.List;
import java.util.Objects;

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

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public List<String> getCaps() {
        return caps;
    }

    public void setCaps(List<String> caps) {
        this.caps = caps;
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GethPeer{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", caps=").append(caps);
        sb.append(", network=").append(network);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GethPeer gethPeer = (GethPeer) o;
        return Objects.equals(id, gethPeer.id) &&
                Objects.equals(name, gethPeer.name) &&
                Objects.equals(caps, gethPeer.caps) &&
                Objects.equals(network, gethPeer.network);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, caps, network);
    }

    public static class Network {
        private String localAddress;
        private String remoteAddress;
        private GeoInfo remoteGeoInfo;

        @JsonIgnore
        public String getLocalAddress() {
            return localAddress;
        }

        public void setLocalAddress(String localAddress) {
            this.localAddress = localAddress;
        }

        public String getRemoteAddress() {
            return remoteAddress;
        }

        public void setRemoteAddress(String remoteAddress) {
            this.remoteAddress = remoteAddress;
        }

        public GeoInfo getRemoteGeoInfo() {
            return remoteGeoInfo;
        }

        public void setRemoteGeoInfo(GeoInfo remoteGeoInfo) {
            this.remoteGeoInfo = remoteGeoInfo;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Network{");
            sb.append("localAddress='").append(localAddress).append('\'');
            sb.append(", remoteAddress='").append(remoteAddress).append('\'');
            sb.append(", remoteGeoInfo=").append(remoteGeoInfo);
            sb.append('}');
            return sb.toString();
        }

        // ignore remoteGeoInfo
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Network network = (Network) o;
            return Objects.equals(localAddress, network.localAddress) &&
                    Objects.equals(remoteAddress, network.remoteAddress);
        }

        // ignore remoteGeoInfo
        @Override
        public int hashCode() {
            return Objects.hash(localAddress, remoteAddress);
        }
    }
}
