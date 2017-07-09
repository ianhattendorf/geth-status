package com.ianhattendorf.geth.gethstatus.service;

import com.ianhattendorf.geth.gethstatus.domain.memoryusage.MemoryStats;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;

@Service
public class LinuxShellMemoryStatsService implements MemoryStatsService {

    private static final Logger logger = LoggerFactory.getLogger(LinuxShellMemoryStatsService.class);
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

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

    private static int getMemoryStat(String statName) throws IOException {
        ProcessBuilder processBuilder =
                new ProcessBuilder("awk", String.format("/%s/ {print $2}", statName), "/proc/meminfo");
        Process process = processBuilder.start();
        String result = IOUtils.toString(process.getInputStream(), StandardCharsets.UTF_8).trim();
        return Integer.valueOf(result);
    }
}
