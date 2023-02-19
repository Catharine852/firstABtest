import com.afanafeva.core.BaseTest;
import com.afanafeva.pages.HomePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;


public class DriverTest  {
    @Autowired
    HomePage homePage;// = new HomePage();

    @Test
    public void homePageTest() {
        homePage.openCard("Forms");
        System.out.println();
    }

}
