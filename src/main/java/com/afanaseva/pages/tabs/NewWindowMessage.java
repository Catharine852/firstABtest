package com.afanaseva.pages.tabs;

import com.afanaseva.pages.BasePage;
import com.afanaseva.utils.Web;

public class NewWindowMessage extends BasePage {
    private final static String BODY = "//body";

    public static String getText() {
        return Web.findElement(BODY).getAttribute("innerText");
    }
}
