package com.afanaseva.core;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class Navigation {

    private static WebDriver driver = WebDriverManager.getDriver();

    @Step("Open link")
    public static void gotoPage(String link) {
        driver.get(link);
    }

}
