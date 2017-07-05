package com.ianhattendorf.geth.gethstatus.domain.diskusage;

import lombok.Value;

@Value
public class DiskStats {
    String usedGB;
    String totalGB;
}
