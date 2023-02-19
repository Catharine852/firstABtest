package com.afanafeva.utils;

import com.afanafeva.core.Properties;
import com.afanafeva.core.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Callable;


public class Web {
    /**
     * this class contains useful static methods for convenient handling different element/actions
     * be attentive: some method contains assertions, other ones return value.
     */
    private static Logger logger = LoggerFactory.getLogger(Web.class);
    private static WebDriver driver;

    /**
     * doBefore() always to be called the first line in methods where the driver field is used set the correct value
     * to avoid org.openqa.selenium.NoSuchSessionException: Session ID is null. Using WebDriver after calling quit()?
     * moved to a separate method because when you switch selenide/selenium, the method of obtaining driver will change
     */
    private static void doBefore() {
//        driver = WebDriverRunner.getWebDriver(); //for Selenide
        driver = WebDriverManager.getDriver(); //for Selenium
    }

    public static void setDriver(final WebDriver driver) {
        Web.driver = driver;
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static void refreshPage() {
        doBefore();
        String url = driver.getCurrentUrl();
        driver.get(url);
    }

    public static void delay(int sec) {
        logger.info(sec + " seconds delay start");
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException ignored) {
        }
    }

    public static WebElement findEl(String path) {
        doBefore();
        return driver.findElement(By.xpath(path));
    }

    public static WebElement findElUnder(WebElement e, String path) {
        doBefore();
        return findElsUnder(e, path).get(0);
    }

    public static List<WebElement> findElsUnder(WebElement e, String path) {
        doBefore();
        return e.findElements(By.xpath("." + path));
    }

    public static List<WebElement> els(String path) {
        doBefore();
        return driver.findElements(By.xpath(path));
    }

    public static void delayShort(int millis) {
        logger.info(millis + " millis delay start");
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }

    public static void waitForPageLoading() {
        doBefore();
        Wait<WebDriver> wait = new WebDriverWait(driver, Long.parseLong(Properties.get("timeout")));
        wait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
    }

    public static void clickElement(WebElement element) {
        StringBuilder log = new StringBuilder("Click " + WebUtils.getLocator(element));
        waitUntil(Messages.ELEMENT_TO_BE_CLICKABLE, element::isEnabled, Timeouts.ELEMENT_TO_BE_CLICKABLE);
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
                WebUtils.clickActions(element);
                log.append("Click by Actions successful.");
                isPrevClickedWasSuccessful = true;
            } catch (Exception exActions) {
                log.append("Click by Actions failed; ");
            }
        }
        if (!isPrevClickedWasSuccessful) {
            try {
                log.append("Try click by JS; ");
                WebUtils.clickJs(element);
                log.append("Click by JS successful.");
                isPrevClickedWasSuccessful = true;
            } catch (Exception exJx) {
                log.append("Click by JS failed; ");
            }
        }
        if (!isPrevClickedWasSuccessful) {
            try {
                log.append("Try complex click; ");
                WebUtils.complexClick(element);
                log.append("Complex click successful.");
            } catch (Exception exComplex) {
                log.append("Click failed.");
                logger.info(log.toString());
                throw new RuntimeException("click element '" + element + "' failed");
            }
        }
        logger.info(log.toString());
        WebUtils.pause(100);
    }


    public static WebElement waitElementToBeClickable(WebElement e) {
        WebDriverWait wait = new WebDriverWait(driver, Timeouts.ELEMENT_TO_BE_CLICKABLE);
        wait.until(ExpectedConditions.elementToBeClickable(e));
        return e;
    }

    public static void waitUntil(String message, Callable<Boolean> c) {
        waitUntil(message, c, Timeouts.AFTER_CLICK);
    }

    public static void waitUntil(String message, Callable<Boolean> c, int timeout) {
        doBefore();
        FluentWait<WebDriver> wait = new WebDriverWait(driver, timeout).withMessage(message);
        wait.until(driver -> {
            try {
                return c.call();
            } catch (Exception ignored) {
                return false;
            }
        });
    }

    public static void typeText(String path, String text) {
        typeText(findEl(path), text);
    }

    public static void typeText(WebElement e, String text) {
        doBefore();
        e = waitElementToBeClickable(e);
        e.sendKeys(text);
        if (!e.getText().equals(text)) {
            e.clear();
            e.sendKeys(text);
        }
    }


}
