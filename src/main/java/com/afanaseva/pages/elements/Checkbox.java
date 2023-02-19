package com.afanaseva.pages.elements;

import com.afanaseva.utils.Web;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class Checkbox extends BaseElement {
    private WebElement wrap;
    private static final String WRAP_BY_LABEL = "//label[text()='%s']/ancestor::div[@id='hobbiesWrapper']";
    private static final String INPUT_BY_LABEL = "//label[text()='%s']/preceding-sibling::input";
    private static final String OPTIONS_LIST = "//input/following-sibling::label";

    private Checkbox(WebElement wrap) {
        this.wrap = wrap;
    }


    public static Checkbox byLabel(String label) {
        return byPath(String.format(WRAP_BY_LABEL, label));
    }

    public static Checkbox byPath(String path) {
        return byWrapElement(Web.findElement(path));
    }

    public static Checkbox byWrapElement(WebElement wrap) {
        return new Checkbox(wrap);
    }

    public List<String> getOptions() {
        return Web.findElementsUnder(wrap, OPTIONS_LIST)
                .stream()
                .map(e -> e.getAttribute("innerText"))
                .collect(Collectors.toList());
    }

    public Checkbox checkOption(String option) {
        if (!isChecked(option)) toggleOption(option);
        return this;
    }

    private Boolean isChecked(String option) {
        return false; //FIXME pseudo element
    }

    private void toggleOption(String option) {
        clickElement(Web.findElementUnder(wrap, String.format(INPUT_BY_LABEL, option)));
    }
}
