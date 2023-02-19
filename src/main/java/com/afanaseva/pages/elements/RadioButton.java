package com.afanafeva.pages.elements;

import com.afanafeva.utils.Web;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class RadioButton extends BaseElement {
    private WebElement wrap;
    private static final String WRAP_BY_LABEL = "//div[text()='%s']/ancestor::div[contains(@id, 'Wrap')]";
    private static final String INPUT_BY_LABEL = "//label[text()='%s']/preceding-sibling::input";
    private static final String OPTIONS_LIST = "//input/following-sibling::label";

    private RadioButton(WebElement wrap) {
        this.wrap = wrap;
    }


    public static RadioButton byLabel(String label) {
        return byPath(String.format(WRAP_BY_LABEL, label));
    }

    public static RadioButton byPath(String path) {
        return byWrapElement(Web.findEl(path));
    }

    public static RadioButton byWrapElement(WebElement wrap) {
        return new RadioButton(wrap);
    }

    public List<String> getOptions() {
        return Web.findElsUnder(wrap, OPTIONS_LIST)
                .stream()
                .map(e -> e.getAttribute("innerText"))
                .collect(Collectors.toList());
    }

    public RadioButton chooseOption(String option) {
        clickElement(Web.findElUnder(wrap, String.format(INPUT_BY_LABEL, option)));
        return this;
    }
}
