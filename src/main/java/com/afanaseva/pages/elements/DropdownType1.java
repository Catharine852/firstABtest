package com.afanaseva.pages.elements;

import org.openqa.selenium.WebElement;

public class DropdownSimple extends BaseElement {

    private WebElement wrap;

    public static DropdownSimple byElement(WebElement e) {
        return new DropdownSimple(e);
    }

    private DropdownSimple(WebElement e) {
        this.wrap = e;
    }
}
