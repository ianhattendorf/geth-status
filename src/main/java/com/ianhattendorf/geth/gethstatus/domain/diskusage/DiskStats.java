package com.ianhattendorf.geth.gethstatus.domain.diskusage;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class DiskStats {
    String usedGB;
    String totalGB;
}
