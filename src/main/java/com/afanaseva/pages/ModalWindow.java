package com.afanaseva.pages;

import com.afanaseva.testdata.Timeouts;
import com.afanaseva.utils.Web;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModalWindow extends BasePage {

    private static final String BASE = "//div[@class='modal-content']";

    private static final String ROWS = BASE + "//tbody/tr";

//    private static final String KEY = "(((//div[@class='modal-content']//tr)[%d])/td)[1]";
//    private static final String VALUE = "(((//div[@class='modal-content']//tr)[%d])/td)[2]";

    public Map<String, String> getData() {
        Web.waitUntil("Modal window is not visible",
                () -> Web.findElement(BASE).isDisplayed(),
                Timeouts.ELEMENT_TO_BE_VISIBLE);

        List<WebElement> rows = Web.findElements(ROWS);
        Map<String, String> res = new HashMap<>();
        for(WebElement row : rows) {
            res.put(
                    Web.findElementUnder(row, "/td[1]").getAttribute("innerText"),
                    Web.findElementUnder(row, "/td[2]").getAttribute("innerText"));
        }
        return res;
    }
}
