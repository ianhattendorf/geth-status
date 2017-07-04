package com.ianhattendorf.geth.gethstatus.service;

import com.ianhattendorf.geth.gethstatus.domain.GeoInfo;

public interface GeoService {
    GeoInfo getInfo(String ip);
}
