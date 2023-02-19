package com.afanaseva.pages.elements;

import com.afanaseva.utils.Web;
import org.openqa.selenium.WebElement;

public abstract class BaseElement {
    public void clickElement(WebElement element) {
        Web.clickElement(element);
    }
}
