package com.afanaseva.utils;

import com.afanaseva.core.Properties;

/**
 * example of constants from file
 */
public class Constants {
    //Selenium constants
    public static final int WEB_DRIVER_WAIT_DURATION = Integer.parseInt(Properties.get(""));
    public static final int MINIMUM_WEB_DRIVER_WAIT_DURATION = 0;
    public static final int PAGE_FACTORY_WAIT_DURATION = 0;
    public static final String SCREENSHOT_LOCATION = "0";
    public static final String TEST_OUTPUT_LOCATION = "";

    public static final int PAGE_LOADING_TIMEOUT = 60;
    public static final int CLICK_TIMEOUT = 15;
    public static final int AFTER_CLICK_TIMEOUT = 5;

    //Set Env variable
    private static String setEnvVariable() {
        //Reading from system properties.
        String environment = System.getProperty("Env");

        //if not specified via command line, take it from constants.properties file
        if (environment == null || environment.isEmpty()) {
            environment = "";
        }
        return environment;
    }
}