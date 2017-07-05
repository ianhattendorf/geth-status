package com.ianhattendorf.geth.gethstatus.domain.diskusage;

import java.util.Objects;

public class DiskStats {
    private final String usedGB;
    private final String totalGB;

    public DiskStats(String usedGB, String totalGB) {
        this.usedGB = usedGB;
        this.totalGB = totalGB;
    }

    public String getUsedGB() {
        return usedGB;
    }

    public String getTotalGB() {
        return totalGB;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DiskStats{");
        sb.append("usedGB=").append(usedGB);
        sb.append(", totalGB=").append(totalGB);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiskStats diskStats = (DiskStats) o;
        return Objects.equals(usedGB, diskStats.usedGB) &&
                Objects.equals(totalGB, diskStats.totalGB);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usedGB, totalGB);
    }
}
