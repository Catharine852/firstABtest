package com.afanaseva.pages;

import com.afanaseva.utils.Web;
import org.openqa.selenium.WebElement;

public class SliderPage extends BasePage {
    private static final String SLIDER_DIMENSIONS = "//input[@type='range']";
    private static final String SLIDER_BUTTON = "//input[@type='range']/following-sibling::div";
    private static final String SLIDER_INPUT_VALUE = "//input[@id='sliderValue']";

    private static final String SLIDER_TOOLTIP_VALUE = "//div[contains(@class, 'tooltip') and contains(@class, 'label')]";

    public int setSlider(int value) {
        WebElement sliderDimensions = Web.findElement(SLIDER_DIMENSIONS); //хелпер для позиционироания
        WebElement sliderPoint = Web.findElement(SLIDER_BUTTON);
        final int deltaY = -sliderDimensions.getRect().getHeight() / 2 - 14; //прицел на точку по вертикали, потому что элемент на стр ниже, чем видимая точка (особенность реализации страницы)

        int sliderPointCurrentPos = sliderPoint.getRect().getPoint().getX(); //текущее положение точки на линейке
        int fullWidth = sliderDimensions.getRect().getWidth(); //длина линейки
        int deltaXFromZero = fullWidth * value / 100; //на сколько пикселей надо переместить точку, считая от нуля
        int zeroPositionX = sliderDimensions.getRect().getPoint().x; //расстояние до начала линейки от левого края стр
        int xOff = zeroPositionX + deltaXFromZero - sliderPointCurrentPos;

        Web.action()
                .moveToElement(sliderPoint)
                .moveByOffset(0, deltaY)
                .clickAndHold()
                .moveByOffset(xOff, 0)
                .release()
                .build().perform();

        return getSliderInputValue();
    }

    public int getSliderInputValue() {
        return Integer.parseInt(Web.findElement(SLIDER_INPUT_VALUE).getAttribute("value"));
    }

    public int getSliderTooltipValue() {
        return Integer.parseInt(Web.findElement(SLIDER_TOOLTIP_VALUE).getAttribute("innerText"));
    }

}
