package com.ianhattendorf.geth.gethstatus.domain.geoip;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class GeoInfo {
    String countryCode;
    String countryName;
}
