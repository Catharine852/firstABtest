package com.afanaseva.core;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;


public abstract class BaseTest {
    protected WebDriver driver;

    @BeforeClass(alwaysRun = true)
    protected void beforeClass() {
        driver = WebDriverManager.getDriver();
    }

    @BeforeMethod(alwaysRun = true)
    protected void beforeMethod() {
        Navigation.gotoPage(Properties.get("baseurl"));
    }

    @AfterMethod(alwaysRun = true)
    protected void afterMethod() {
    }

    @AfterClass(alwaysRun = true)
    protected void afterClass() {
        driver.quit();
    }
}
