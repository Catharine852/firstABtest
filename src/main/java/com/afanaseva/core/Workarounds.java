package com.afanaseva.core;

import com.afanaseva.utils.Web;
import org.openqa.selenium.WebElement;

public class Workarounds {
    private final static String ADD_IFRAME = "//div[contains(@id, 'google_ads_iframe')]/..";

    public static void removeAdsPracticeForm() {
        for (WebElement ad : Web.findElements(ADD_IFRAME))
            Web.js().executeScript("arguments[0].remove()", ad);
    }
}
