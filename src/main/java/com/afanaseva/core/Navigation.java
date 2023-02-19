package com.afanafeva.core;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class Navigation {
    /**
     * this class used to get the page to be tested
     * as a rule, URL consists of constant host and path
     * so let's put paths into map<page, path> where the page an enum constant, and the path is a String
     */
    private static WebDriver driver = WebDriverManager.getDriver();

    @Step("Open link")
    public static void gotoPage(String link) {
        driver.get(link);
    }

}
