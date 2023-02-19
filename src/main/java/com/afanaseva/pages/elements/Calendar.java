package com.afanaseva.pages.elements;

import com.afanaseva.testdata.Timeouts;
import com.afanaseva.utils.Web;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class Calendar extends BaseElement {
    private static final String WRAP = "//div[@class='react-datepicker']";
    private static final String YEAR_DROPDOWN = WRAP + "//select[contains(@class, 'year')]";
    private static final String MONTH_DROPDOWN = WRAP + "//select[contains(@class, 'month')]";

    private static final String YEAR_DROPDOWN_OPTION = YEAR_DROPDOWN + "/option[@value='%s']";
    private static final String MONTH_DROPDOWN_OPTION = MONTH_DROPDOWN + "/option[text()='%s']";
    private static final String DAYS = WRAP + "//div[contains(@class, 'react-datepicker__day')]";

    public static void setDate(String date) {
        Web.waitUntil("Calendar did not appear", () -> Web.findElement(WRAP).isDisplayed(), Timeouts.CALENDAR);

        DateTimeFormatter df = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("dd LLL yyyy")
                .toFormatter(Locale.ENGLISH);

        LocalDate dateParsed = LocalDate.parse(date, df);
        setYear(dateParsed.getYear());
        setMonth(StringUtils.capitalize(dateParsed.getMonth().toString().toLowerCase()));
        setDay(dateParsed.getDayOfMonth());
    }

    private static void setYear(int year) {
        Web.clickElement(Web.findElement(YEAR_DROPDOWN));
        Web.clickElement(Web.findElement(String.format(YEAR_DROPDOWN_OPTION, year)));
        Web.clickElement(Web.findElement(YEAR_DROPDOWN));
    }

    private static void setMonth(String month) {
        Web.clickElement(Web.findElement(MONTH_DROPDOWN));
        Web.clickElement(Web.findElement(String.format(MONTH_DROPDOWN_OPTION, month)));
        Web.clickElement(Web.findElement(MONTH_DROPDOWN));
    }

    private static void setDay(int day) {
        List<WebElement> days = Web.findElements(DAYS)
                .stream()
                .filter(e -> e.getAttribute("innerText").equals(String.valueOf(day)))
                .collect(Collectors.toList());

        if (days.size() != 1) throw new RuntimeException("Can't find date in calendar");

        Web.clickElement(days.get(0));
    }

}
