package com.afanafeva.pages.elements;

import com.afanafeva.core.WebDriverManager;
import com.afanafeva.utils.Web;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class BaseElement {
    protected static WebDriver driver() {
        return WebDriverManager.getDriver();
    }

    public void clickElement(WebElement element) {
        Web.clickElement(element);
    }
}
