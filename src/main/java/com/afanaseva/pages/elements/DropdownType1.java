package com.afanaseva.pages.elements;

import com.afanaseva.utils.Web;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class DropdownType1 extends BaseElement {

    private final String BASE;

    public static DropdownType1 byPath(String path) {
        return new DropdownType1(path);
    }

    private DropdownType1(String base) {
        this.BASE = base;
    }

    public DropdownType1 setValue(String value) {
        WebElement e = Web.findElement(BASE);
        if (value.equals("any")) {
            clickElement(e);
        } else {
            Web.typeText(BASE + "//input", value);
        }
        new Actions(Web.driver())
                .moveToElement(e)
                .moveByOffset(0, 50)
                .click()
                .build()
                .perform();

        return this;
    }
}
