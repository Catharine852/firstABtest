package com.afanaseva.pages.tabs;

public interface TabsTestable {
    static String getText(String buttonName) {
        switch (buttonName) {
            case "New Tab":
            case "New Window": {
                return NewTab.getText();
            }
            case "New Window Message": {
                return NewWindowMessage.getText();
            }
            default:
                throw new RuntimeException();
        }
    }
}
