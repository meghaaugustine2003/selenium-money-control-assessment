package com.selenium.utils;

import lombok.extern.java.Log;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;

import static com.selenium.constants.Constants.getConfigFilePath;

@Log
public class ReadPropertyFile {
    private ReadPropertyFile() {
    }

    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream stream = new FileInputStream(getConfigFilePath())) {
            PROPERTIES.load(stream);
        } catch (IOException ex) {
            log.log(Level.SEVERE, "Failed to load the properties", ex);
        }
    }

    public static String propertyOf(String key) {
        return PROPERTIES.getProperty(key);
    }
}
