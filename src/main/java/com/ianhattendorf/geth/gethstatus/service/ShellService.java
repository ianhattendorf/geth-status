package com.ianhattendorf.geth.gethstatus.service;

import java.io.IOException;

public interface ShellService {
    String execute(String... args) throws IOException;
}
