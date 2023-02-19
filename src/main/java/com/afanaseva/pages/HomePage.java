package com.afanafeva.pages;


import com.afanafeva.utils.Web;

public class HomePage extends BasePage {

    private static final String CARD_BY_NAME = "//h5[text()='%s']/ancestor::div[contains(@class, 'top-card')]";

//    @FindBy(xpath = "//h5[text()='Forms']/ancestor::div[contains(@class, 'top-card')]")
//    WebElement cardForms;

    public void openCard(String name) {
        waitPageLoaded();
        clickElement(Web.findEl(String.format(CARD_BY_NAME, name)));
        waitPageLoaded();
    }

}
