import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import page.GetImages;
import utilities.driver.InitWebDriver;

public class TestDemo {
    @Test
    void demo() {
        WebDriver driver = new InitWebDriver().getDriver("chrome", false);
        new GetImages(driver).navigateToGoogleMaps().searchAndSelectFirstResult("Công ty sản xuất phân bón khu vực Đồng Nai").getInformation();
    }
}
