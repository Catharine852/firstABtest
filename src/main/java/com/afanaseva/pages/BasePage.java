package com.afanafeva.pages;

import com.afanafeva.core.WebDriverManager;
import com.afanafeva.utils.Web;
import com.afanafeva.utils.WebUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BasePage {

    protected static WebUtils webUtils;
    protected final Logger logger;

    public BasePage() {
        PageFactory.initElements(driver(), this);
        logger = LoggerFactory.getLogger(this.getClass());
        webUtils = new WebUtils();
    }

    protected static WebDriver driver() {
        return WebDriverManager.getDriver();
    }

    public void clickElement(WebElement element) {
       Web.clickElement(element);
    }

    public void waitPageLoaded() {
        Web.waitForPageLoading();
    }
}

