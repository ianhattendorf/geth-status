package com.ianhattendorf.geth.gethstatus.domain.memoryusage;

import java.util.Objects;

public class MemoryStats {
    private final String usedGB;
    private final String totalGB;

    public MemoryStats(String usedGB, String totalGB) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemoryStats that = (MemoryStats) o;
        return Objects.equals(usedGB, that.usedGB) &&
                Objects.equals(totalGB, that.totalGB);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usedGB, totalGB);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MemoryStats{");
        sb.append("usedGB='").append(usedGB).append('\'');
        sb.append(", totalGB='").append(totalGB).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
