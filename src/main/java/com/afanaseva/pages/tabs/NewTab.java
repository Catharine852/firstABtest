package com.afanaseva.pages.tabs;

import com.afanaseva.pages.BasePage;
import com.afanaseva.utils.Web;

public class NewTab extends BasePage {
    private static final String HEADER = "//h1[@id='sampleHeading']";

    public static String getText() {
        return Web.findElement(HEADER).getAttribute("innerText");
    }
}
