package com.selenium.driver;

import org.openqa.selenium.WebDriver;

public class DriverManager {
    private DriverManager() {
    }

    private static WebDriver driver = null;

    public static WebDriver browser() {
        return driver;
    }

    public static void setDriver(WebDriver driverRef) {
        driver = driverRef;
    }

    public static void closeBrowser() {
        driver = null;
    }
}
