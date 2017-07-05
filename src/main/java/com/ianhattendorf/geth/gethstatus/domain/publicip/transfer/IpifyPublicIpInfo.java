package com.ianhattendorf.geth.gethstatus.domain.publicip.transfer;

public class IpifyPublicIpInfo {
    private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("IpifyPublicIpInfo{");
        sb.append("ip='").append(ip).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
