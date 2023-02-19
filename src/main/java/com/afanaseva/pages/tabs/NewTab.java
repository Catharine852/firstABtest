package com.afanaseva.pages;

import com.afanaseva.utils.Web;

public class NewTab extends BasePage{
    private static final String HEADER = "//h1[@id='sampleHeading']";

    public static String getHeader(){
        return Web.findElement(HEADER).getAttribute("innerText");
    }
}
