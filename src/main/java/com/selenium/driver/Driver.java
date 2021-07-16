package com.selenium.driver;

import com.selenium.constants.Constants;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Objects;

import static com.selenium.driver.DriverManager.*;

public class Driver {
    private Driver() {
    }

    public static void inItDriver() {
        if (Objects.isNull(browser())) {
            System.setProperty("webdriver.chrome.driver", Constants.getChromedriverPath());
            setDriver(new ChromeDriver());
        }
    }

    public static void quitDriver() {
        if (Objects.nonNull(browser())) {
            browser().quit();
            closeBrowser();
        }
    }
}
