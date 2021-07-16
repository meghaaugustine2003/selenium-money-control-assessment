package com.selenium.constants;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Constants {
    private static final String RESOURCES_PATH = System.getProperty("user.dir") + "/src/test/resources";
    private static final String CHROMEDRIVER_PATH = RESOURCES_PATH + "/executables/chromedriver";
    private static final String CONFIG_FILE_PATH = RESOURCES_PATH + "/config/config.properties";
    private static final String SCREENSHOTS_FILE_PATH = System.getProperty("user.dir") + "/target/screenshots/";
    private static final String TEST_REPORT_PATH = System.getProperty("user.dir") + "/target/reports/";

    private Constants() {
        // initialization now allowed
    }

    public static String getChromedriverPath() {
        return CHROMEDRIVER_PATH;
    }

    public static String getConfigFilePath() {
        return CONFIG_FILE_PATH;
    }

    @SneakyThrows
    public static String getScreenshotsFilePath() {
        if (!Files.exists(Paths.get(SCREENSHOTS_FILE_PATH))) {
            Files.createDirectories(Paths.get(SCREENSHOTS_FILE_PATH));
        }
        return SCREENSHOTS_FILE_PATH;
    }

    @SneakyThrows
    public static String getTestReportPath() {
        if (!Files.exists(Paths.get(TEST_REPORT_PATH))) {
            Files.createDirectories(Paths.get(TEST_REPORT_PATH));
        }
        return TEST_REPORT_PATH;
    }
}
