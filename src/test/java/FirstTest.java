import com.afanaseva.core.BaseTest;
import com.afanaseva.core.Workarounds;
import com.afanaseva.pages.BrowserWindowsPage;
import com.afanaseva.pages.HomePage;
import com.afanaseva.pages.PracticeFormPage;
import com.afanaseva.pages.SliderPage;
import com.afanaseva.pages.blocks.MenuList;
import com.afanaseva.pages.tabs.TabsTestable;
import com.afanaseva.testdata.PracticeFormValidData;
import com.afanaseva.utils.Web;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class FirstTest extends BaseTest {

    private final HomePage homePage = new HomePage();
    private final PracticeFormPage practiceFormPage = new PracticeFormPage();

    @Test(priority = 10)
    public void practiceFormTest() {
        homePage.openCard("Forms");
        MenuList.openItem("Practice Form");

        Workarounds.removeAdsPracticeForm();

        Map<String, String> actualData = practiceFormPage
                .fillFormWithValidData()
                .submit()
                .getData();

        Map<String, String> expectedData = PracticeFormValidData.expectedData();

        Assert.assertEquals(
                expectedData.size(),
                actualData.size(),
                "Data in modal window should correspond to submitted");

        Set<String> keys = expectedData.keySet();
        boolean equals = true;
        String diff = null;
        for (String key : keys) {
            equals = (expectedData.get(key).equals(actualData.get(key)));
            if (!equals) diff = key;
        }

        Assert.assertTrue(equals,
                String.format("Data in modal window should correspond to submitted: key = %s, value exp = %s, value act = %s",
                        diff,
                        expectedData.get(diff),
                        actualData.get(diff)));
    }

    @Test(priority = 20)
    public void browserWindowsTest() {
        homePage.openCard("Alerts, Frame & Windows");
        MenuList.openItem("Browser Windows");

        Map<String, String> expectedTexts = new HashMap<>();
        expectedTexts.put("New Tab", "This is a sample page");
        expectedTexts.put("New Window", "This is a sample page");
        expectedTexts.put("New Window Message", "Knowledge increases by sharing but not by saving. Please share this website with your friends and in your organization.");
        Set<String> buttons = expectedTexts.keySet();

        for (String button : buttons) {
            String[] tabs = Web.expectOpensInNewTabWhenClick(Web.findElement(String.format(BrowserWindowsPage.BUTTON_BY_TEXT, button)));

            String actualText = TabsTestable.getText(button);
            Assert.assertEquals(actualText, expectedTexts.get(button), button + " text different");

            Web.driver().close();
            Web.driver().switchTo().window(tabs[0]);
        }

    }

    @Test(priority = 30)
    public void sliderTest() {
        homePage.openCard("Widgets");
        MenuList.openItem("Slider");

        SliderPage sp = new SliderPage();

        int value = 70;
        sp.setSlider(value);

        //due to some page implementation-based features, it is too complex to set clear position
        // so, we will use a float\double comparison approach
        final int TOLERANCE = 2;

        Assert.assertTrue(Math.abs(sp.setSlider(value) - value) <= TOLERANCE, "Slider value was not applied");
        Assert.assertEquals(sp.getSliderInputValue(), sp.getSliderTooltipValue(), "Slider tooltip value should be equal to slider input");
    }

}
