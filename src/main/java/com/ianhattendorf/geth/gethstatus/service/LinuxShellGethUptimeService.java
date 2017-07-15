package com.ianhattendorf.geth.gethstatus.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.cache.annotation.CacheResult;
import java.io.IOException;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LinuxShellGethUptimeService implements GethUptimeService {

    private static final Logger logger = LoggerFactory.getLogger(LinuxShellGethUptimeService.class);

    private static final Pattern pattern = Pattern.compile("geth\\W*(?:(?<days>\\d+)-)?(?:(?<hours>\\d{2}):)?(?<minutes>\\d{2}):(?<seconds>\\d{2})");

    private final ShellService shellService;
    private final Clock clock;

    public LinuxShellGethUptimeService(ShellService shellService, Clock clock) {
        this.shellService = shellService;
        this.clock = clock;
    }

    @CacheResult(cacheName = "gethUptime")
    @Override
    public Instant getUptime() {
        String result;
        try {
            result = shellService.execute("/bin/sh", "-c", "ps -eo comm,etime | grep geth");
        } catch (IOException e) {
            logger.error("Uptime shell exception", e);
            return null;
        }
        Matcher matcher = pattern.matcher(result);
        if (!matcher.matches()) {
            logger.error("Error matching process regex, result: {}", result);
            return null;
        }
        String seconds = matcher.group("seconds");
        String minutes = matcher.group("minutes");
        String hours = matcher.group("hours");
        String days = matcher.group("days");
        Instant instant = Instant.now(clock);
        if (days != null) {
            instant = instant.minus(Duration.ofDays(Integer.valueOf(days)));
        }
        if (hours != null) {
            instant = instant.minus(Duration.ofHours(Integer.valueOf(hours)));
        }
        instant = instant.minus(Duration.ofMinutes(Integer.valueOf(minutes)));
        instant = instant.minus(Duration.ofSeconds(Integer.valueOf(seconds)));
        // truncate to seconds to avoid appearing as a different time if off by a few milliseconds
        instant = instant.truncatedTo(ChronoUnit.SECONDS);
        logger.debug("Geth up since {}", instant);
        return instant;
    }
}
