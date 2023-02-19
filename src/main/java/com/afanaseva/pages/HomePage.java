package com.afanaseva.pages;


import com.afanaseva.utils.Web;

public class HomePage extends BasePage {

    private static final String CARD_BY_NAME = "//h5[text()='%s']/ancestor::div[contains(@class, 'top-card')]";

    public void openCard(String name) {
        waitPageLoaded();
        clickElement(Web.findElement(String.format(CARD_BY_NAME, name)));
        waitPageLoaded();
    }

}
