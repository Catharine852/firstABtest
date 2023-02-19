package com.afanaseva.core;

import com.afanaseva.testdata.Timeouts;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.concurrent.TimeUnit;

public class WebDriverManager {

    private static WebDriver driver = null;
    private static final String DEFAULT_RESOLUTION = "maximized";
    public static WebDriver setDriver(String browserName) {

        switch (browserName) {
            case "firefox": {
                io.github.bonigarcia.wdm.WebDriverManager.firefoxdriver().setup();
                FirefoxOptions options = new FirefoxOptions();
                options.setAcceptInsecureCerts(true)
                        .addPreference("dom.webnotifications.enabled", false)
                        .addPreference("dom.push.enabled", false);
                driver = new FirefoxDriver(options);
                break;
            }
            case "chrome": {
                io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();
                ChromeOptions cOptions = new ChromeOptions();
                String hl = Properties.get("headless");
                if (hl != null && hl.equals("true")) cOptions.addArguments("--headless");
//                if (Properties.get("resolution").equals("maximized")) cOptions.addArguments("start-maximized");
                driver = new ChromeDriver(cOptions);
                break;
            }
            default:
                throw new RuntimeException("no such a browser");
        }

        driver.manage().timeouts().implicitlyWait(Timeouts.AFTER_CLICK, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(Timeouts.PAGE_LOADING, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(Timeouts.JS_EXECUTION, TimeUnit.SECONDS);

        String resolutionValue = Properties.get("resolution") != null ? Properties.get("resolution") : DEFAULT_RESOLUTION;
        if (resolutionValue.matches("^[\\d]{4}[x][\\d]{4}$")) {
            String[] dim = Properties.get("resolution").split("x");
            driver.manage().window().setSize(new Dimension(Integer.parseInt(dim[0]), Integer.parseInt(dim[1])));
        } else if (resolutionValue.equals("maximized")){
            driver.manage().window().maximize();
        }

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
