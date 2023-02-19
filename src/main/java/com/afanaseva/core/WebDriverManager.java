package com.afanafeva.core;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
public class WebDriverManager {

    private static WebDriver driver;

    public static WebDriver setDriver(String browserName) {
        io.github.bonigarcia.wdm.WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.setAcceptInsecureCerts(true)
                .addPreference("dom.webnotifications.enabled", false)
                .addPreference("dom.push.enabled", false);
        driver = new FirefoxDriver(options);


        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
        return driver;
    }

    public static void tearDown() {
        driver.quit();
        driver = null;
    }

    public static WebDriver getDriver() {
        if (driver == null) driver = setDriver(Properties.get("browser"));
        return driver;
    }


}
