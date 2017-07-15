package com.ianhattendorf.geth.gethstatus.service;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class ShShellService implements ShellService {
    @Override
    public String execute(String... args) throws IOException {
        String[] commandArgs = new String[args.length + 2];
        commandArgs[0] = "/usr/bin/env";
        commandArgs[1] = "-i";
        System.arraycopy(args, 0, commandArgs, 2, args.length);
        ProcessBuilder processBuilder = new ProcessBuilder(commandArgs);
        Process process = processBuilder.start();
        return IOUtils.toString(process.getInputStream(), StandardCharsets.UTF_8).trim();
    }
}
