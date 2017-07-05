package com.ianhattendorf.geth.gethstatus.service;

import com.ianhattendorf.geth.gethstatus.domain.diskusage.DiskStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.DecimalFormat;

@Service
public class FileDiskStatsService implements DiskStatsService {

    private final String gethDataDir;

    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @Autowired
    public FileDiskStatsService(String gethDataDir) {
        this.gethDataDir = gethDataDir;
    }

    @Override
    public DiskStats getDiskStats() {
        File file = new File(gethDataDir);
        long totalBytes = file.getTotalSpace();
        long usedBytes = file.getTotalSpace() - file.getFreeSpace();
        String totalGB = bytesToGBString(totalBytes);
        String usedGB = bytesToGBString(usedBytes);
        return new DiskStats(usedGB, totalGB);
    }

    private String bytesToGBString(long bytes) {
        double gb = bytes / (1024 * 1024 * 1024 * 1.0d);
        return decimalFormat.format(gb);
    }
}
