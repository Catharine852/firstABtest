package com.afanaseva.pages.tabs;

import com.afanaseva.pages.BasePage;
import com.afanaseva.utils.Web;

public class NewWindow extends BasePage {
    private final static String HEADER = "//h1[@id='sampleHeading']";

    public static String getText() {
        return Web.findElement(HEADER).getAttribute("innerText");
    }
}
