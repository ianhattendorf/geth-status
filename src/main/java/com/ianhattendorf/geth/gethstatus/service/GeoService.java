package com.ianhattendorf.geth.gethstatus.service;

import com.ianhattendorf.geth.gethstatus.domain.geoip.GeoInfo;

public interface GeoService {
    GeoInfo getInfo();
    GeoInfo getInfo(String ip);
}
