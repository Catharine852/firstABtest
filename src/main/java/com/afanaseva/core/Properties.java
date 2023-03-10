package com.afanaseva.core;

import com.afanaseva.testdata.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class Properties {

    private static java.util.Properties properties = null;
    private static final String PROPERTIES_PATH = "src/main/resources/test.properties";
    private static Logger logger = LoggerFactory.getLogger(Properties.class);

    public static String get(String key) {
        if (properties == null) properties = read();
        return properties.getProperty(key);
    }

    private Properties() {
    }

    private static java.util.Properties read() {
        java.util.Properties properties = null;
        try {
            properties = new java.util.Properties();
            properties.load(new InputStreamReader(Files.newInputStream(new File(PROPERTIES_PATH).toPath()), StandardCharsets.UTF_8));
            logger.info(Messages.PROP_LOADED + PROPERTIES_PATH);
        } catch (IOException e) {
            logger.error(Messages.PROP_NOT_LOADED + e.getMessage());
        }
        return properties;
    }
}
