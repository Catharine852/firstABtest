package com.afanaseva.pages;

import com.afanaseva.utils.Web;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BasePage {


    protected final Logger logger;

    public BasePage() {
        PageFactory.initElements(driver(), this);
        logger = LoggerFactory.getLogger(this.getClass());

    }

    protected static WebDriver driver() {
        return Web.driver();
    }

    public void clickElement(WebElement element) {
        Web.clickElement(element);
    }

    public void waitPageLoaded() {
        Web.waitPageLoaded();
    }
}

