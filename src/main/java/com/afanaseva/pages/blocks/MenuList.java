package com.afanaseva.pages.blocks;


import com.afanaseva.utils.Web;
import org.openqa.selenium.WebElement;

public class MenuList {

    private MenuList() {
        throw new RuntimeException("you do not need instance of this class");
    }

    private static final String MENU_ITEM_BY_NAME = "//span[text()='%s']/ancestor::li";

    public static void openItem(String name) {
        Web.waitPageLoaded();
        WebElement e = Web.findElement(String.format(MENU_ITEM_BY_NAME, name));
        Web.scrollToElement(e);
        Web.clickElement(e);
        Web.waitPageLoaded();
    }

}
