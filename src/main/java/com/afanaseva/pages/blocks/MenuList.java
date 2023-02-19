package com.afanaseva.pages;


import com.afanaseva.utils.Web;

public class FormsPage extends BasePage {

    private static final String MENU_ITEM_BY_NAME = "//span[text()='%s']/ancestor::li";

    public void openItem(String name) {
        waitPageLoaded();
        clickElement(Web.findElement(String.format(MENU_ITEM_BY_NAME, name)));
        waitPageLoaded();
    }

}
