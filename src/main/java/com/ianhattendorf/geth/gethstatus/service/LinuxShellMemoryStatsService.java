package com.ianhattendorf.geth.gethstatus.service;

import com.ianhattendorf.geth.gethstatus.domain.memoryusage.MemoryStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DecimalFormat;

@Service
public class LinuxShellMemoryStatsService implements MemoryStatsService {

    private static final Logger logger = LoggerFactory.getLogger(LinuxShellMemoryStatsService.class);
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    private final ShellService shellService;

    public LinuxShellMemoryStatsService(ShellService shellService) {
        this.shellService = shellService;
    }

    @Override
    public MemoryStats getMemoryStats() {
        try {
            int memTotalKb = getMemoryStat("MemTotal");
            int memFreeKb = getMemoryStat("MemFree");
            int memUsedKb = memTotalKb - memFreeKb;
            return new MemoryStats(DECIMAL_FORMAT.format(memUsedKb / (1024 * 1024 * 1.0d)),
                    DECIMAL_FORMAT.format(memTotalKb / (1024 * 1024 * 1.0d)));
        } catch (IOException e) {
            logger.error("Couldn't get memory usage", e);
            return null;
        }
    }

    private int getMemoryStat(String statName) throws IOException {
        String result = shellService.execute("awk", String.format("/%s/ {print $2}", statName), "/proc/meminfo");
        return Integer.valueOf(result);
    }
}
