package com.ianhattendorf.geth.gethstatus;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

public final class TestHelper {

    /**
     * Load response body from __files directory.
     * @param filePath Path to load, relative to __files.
     * @return File body as a {@link String}.
     */
    public static String loadResponseBody(String filePath) {
        Objects.requireNonNull("filePath must not be null");
        if (!filePath.startsWith("/")) {
            filePath = "/".concat(filePath);
        }
        if (!filePath.startsWith("/__files")) {
            filePath = "/__files".concat(filePath);
        }
        return fileToString(filePath);
    }

    /**
     * Load a file as a {@link String}, relative to {@link TestHelper}.
     * @param filePath File path to load.
     * @return The file as a {@link String}.
     */
    public static String fileToString(String filePath) {
        try (
                InputStream inputStream = TestHelper.class.getResourceAsStream(filePath);
                Scanner scanner = new Scanner(inputStream).useDelimiter("\\A")
        ) {
            return scanner.hasNext() ? scanner.next() : "";
        } catch (IOException e) {
            // testing, let exception bubble up
            throw new RuntimeException(e);
        }
    }
}
