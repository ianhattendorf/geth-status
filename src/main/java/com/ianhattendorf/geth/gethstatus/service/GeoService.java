package com.ianhattendorf.geth.gethstatus.service;

import com.ianhattendorf.geth.gethstatus.domain.FreeGeoInfo;

public interface GeoService {
    FreeGeoInfo getInfo(String ip);
}
