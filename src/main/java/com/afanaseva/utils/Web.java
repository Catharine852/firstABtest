package com.afanaseva.utils;

import com.afanaseva.core.Properties;
import com.afanaseva.core.WebDriverManager;
import com.afanaseva.testdata.Constants;
import com.afanaseva.testdata.Messages;
import com.afanaseva.testdata.Timeouts;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

public class Web {

    private Web() {
        throw new RuntimeException("you do not need instance of this class");
    }

    private static final Logger logger = LoggerFactory.getLogger(Web.class);

    private static WebDriver driver = driver();

    public static WebDriver driver() {
        if (driver == null) driver = WebDriverManager.getDriver();
        return driver;
    }

    public static JavascriptExecutor js() {
        return (JavascriptExecutor) driver();
    }

    public static Actions action() {
        return new Actions(driver());
    }

    //----------------------------------------------------

    public static void delay(int sec) {
        logger.info(sec + " seconds delay start");
        pause(sec * 1000);
    }

    public static void pause(int millis) {
        logger.info(millis + " millis delay start");
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }

    //----------------------------------------------------

    public static WebElement findElementUnder(WebElement e, String path) {
        return findElementsUnder(e, path).get(0);
    }

    public static List<WebElement> findElementsUnder(WebElement e, String path) {
        return e.findElements(By.xpath("." + path));
    }

    public static List<WebElement> findElements(String path) {
        return driver().findElements(By.xpath(path));
    }

    public static WebElement findElement(String path) {
        return findElements(path).get(0);
    }

    //----------------------------------------------------

    public static void waitPageLoaded() {
        Wait<WebDriver> wait = new WebDriverWait(driver(), Long.parseLong(Properties.get("timeout")));
        wait.until(driver -> js().executeScript("return document.readyState").equals("complete"));
    }

    public static String getLocator(WebElement e) {
        String locator = ("" + e).split(" -> ")[1].split(": ")[1];
        return String.format("\"%s\"", locator.substring(0, locator.length() - 1));
    }

    //----------------------------------------------------

    public static void clickActions(WebElement element) {
        action().moveToElement(element).click(element).build().perform();
    }

    public static void clickJs(WebElement element) {
        js().executeScript("arguments[0].click()", element);
    }

    public static void clickElement(WebElement element) {
        StringBuilder log = new StringBuilder("Click " + getLocator(element));
        waitUntil(Messages.ELEMENT_TO_BE_CLICKABLE, element::isEnabled, Timeouts.ELEMENT_TO_BE_CLICKABLE);
        if(!isElementInViewPort(element)) scrollToElement(element);
        boolean isPrevClickedWasSuccessful = false;
        try {
            element.click();
            isPrevClickedWasSuccessful = true;
        } catch (Exception exSimple) {
            log.append("; Simple click failed; ");
        }
        if (!isPrevClickedWasSuccessful) {
            try {
                log.append("Try click by Actions; ");
                clickActions(element);
                log.append("Click by Actions successful.");
                isPrevClickedWasSuccessful = true;
            } catch (Exception exActions) {
                log.append("Click by Actions failed; ");
            }
        }
        if (!isPrevClickedWasSuccessful) {
            try {
                log.append("Try click by JS; ");
                clickJs(element);
                log.append("Click by JS successful.");
                isPrevClickedWasSuccessful = true;
            } catch (Exception exJx) {
                log.append("Click by JS failed; ");
            }
        }
        if (!isPrevClickedWasSuccessful) {
            try {
                log.append("Try complex click; ");
                complexClick(element);
                log.append("Complex click successful.");
            } catch (Exception exComplex) {
                log.append("Click failed.");
                logger.info(log.toString());
                throw new RuntimeException("click element '" + element + "' failed");
            }
        }
        logger.info(log.toString());
        pause(100);
    }

    public static void complexClick(WebElement e) {
        waitUntilPageIsLoaded();
        getClickWait().withMessage("Click failed").until(driver -> {
            clickJs(e);
            return true;
        });
    }

    private static FluentWait<WebDriver> getClickWait() {
        return new WebDriverWait(driver(), Constants.CLICK_TIMEOUT)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(ElementClickInterceptedException.class)
                .ignoring(ElementNotInteractableException.class);
    }

    public static void dragAndDrop(Point start, Point finish){
        int xFin = finish.getX() - start.getX();
        int yFin = finish.getY() - start.getY();
        action()
                .moveByOffset(start.getX(), start.getY())
                .clickAndHold()
                .moveByOffset(xFin, yFin).release()
                .build().perform();
    }

    //----------------------------------------------------

    public static void scrollToElement(WebElement e) {
        js().executeScript("arguments[0].scrollIntoView()", e);
    }

    public static boolean isElementInViewPort(WebElement e) {
        Dimension wd = driver().manage().window().getSize();
        return e.getRect().getPoint().getX() < wd.height;
    }

    //----------------------------------------------------

    public static void waitUntilPageIsLoaded() {
        WebDriverWait wait = new WebDriverWait(driver(), Constants.PAGE_LOADING_TIMEOUT);
        wait.until(driver ->
                js().executeScript("return document.readyState").equals("complete"));
        wait.until(driver ->
                driver.findElements(By.xpath("//div[contains(@class, 'ProgressIndicator')]")).isEmpty());
    }


    public static WebElement waitElementToBeClickable(WebElement e) {
        WebDriverWait wait = new WebDriverWait(driver(), Timeouts.ELEMENT_TO_BE_CLICKABLE);
        wait.until(ExpectedConditions.elementToBeClickable(e));
        return e;
    }

    public static void waitUntil(String message, Callable<Boolean> c) {
        waitUntil(message, c, Timeouts.AFTER_CLICK);
    }

    public static void waitUntil(String message, Callable<Boolean> c, int timeout) {
        FluentWait<WebDriver> wait = new WebDriverWait(driver(), timeout).withMessage(message);
        wait.until(driver -> {
            try {
                return c.call();
            } catch (Exception ignored) {
                return false;
            }
        });
    }

    //----------------------------------------------------

    public static void typeText(String path, String text) {
        typeText(findElement(path), text);
    }

    public static void typeText(WebElement e, String text) {
        waitElementToBeClickable(e);
        e.sendKeys(text);
        if (!e.getText().equals(text)) {
            e.clear();
            e.sendKeys(text);
        }
    }

    //----------------------------------------------------

    public static String[] expectOpensInNewTabWhenClick(WebElement e) {
        return openInNewTab(() -> {
            complexClick(e);
        });
    }

    public static String[] openInNewTab(Runnable action) {
        String startTab = driver().getWindowHandle();
        String newTab = null;
        Set<String> prevTabs = driver().getWindowHandles();
        action.run();
        Set<String> newTabs = driver().getWindowHandles();
        if (newTabs.size() != prevTabs.size() + 1) {
            throw new RuntimeException("Unexpected tab count");
        }
        for (String tab : newTabs) {
            if (!prevTabs.contains(tab)) {
                newTab = tab;
                break;
            }
        }
        driver().switchTo().window(newTab);
        return new String[]{startTab, newTab};
    }
}
