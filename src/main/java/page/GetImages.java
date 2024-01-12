package page;

import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utilities.UICommonAction;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class GetImages extends MapsLocator {
    WebDriver driver;
    UICommonAction commons;
    Logger logger = LogManager.getLogger(GetImages.class);

    public GetImages(WebDriver driver) {
        this.driver = driver;
        commons = new UICommonAction(driver);
    }

    public GetImages navigateToGoogleMaps() {
        driver.get("https://www.google.com/maps");
        return this;
    }

    public GetImages searchAndSelectFirstResult(String keywords) {
        commons.sendKeys(searchBox, "%s\n".formatted(keywords));
        return this;
    }

    @SneakyThrows
    void scrollToEnd(By locator, int size) {
        commons.scrollIntoView(locator, size - 1);
        int newSize = commons.getListElement(locator).size();
        if (commons.getListElement(endOfPage).isEmpty()) {
            scrollToEnd(locator, newSize);
        }
    }

    @SneakyThrows
    public void getInformation() {
        List<String> getListURL = new ArrayList<>();
        commons.getElement(listResult);
        int size = commons.getListElement(listResult).size();
        if (size > 0) scrollToEnd(listResult, size);
        size = commons.getListElement(listResult).size();

        for (int index = 0; index <size; index ++) {
            getListURL.add(commons.getAttribute(listResult, index, "href"));
        }

        int count = 0;
        for (String url : getListURL) {
            count ++;
            driver.get(url);
            System.out.printf("%s, %s%n", count, commons.getText(name));
            if (!commons.getListElement(phone).isEmpty())
                System.out.println(commons.getText(phone));
        }
    }

}
