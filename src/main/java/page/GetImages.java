package page;

import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilities.UICommonAction;

import java.util.List;
import java.util.stream.IntStream;

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
        System.out.println(keywords);
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

    static int count = 0;

    @SneakyThrows
    public void getInformation() {
        List<String> getListURL;
        commons.getElement(listResult);
        if (commons.getAttribute(listResult, 0, "class").contains("google-symbols")) {
            count++;
            getInfo(count);
        } else {
            int size = commons.getListElement(listResult).size();
            if (size > 0) scrollToEnd(listResult, size);
            size = commons.getListElement(listResult).size();

            getListURL = IntStream.range(0, size).mapToObj(index -> commons.getAttribute(listResult, index, "href")).toList();
            for (String url : getListURL) {
                count++;
                driver.get(url);
                getInfo(count);
            }
        }
    }

    void getInfo(int count) {
        System.out.printf("%s, %s%n", count, commons.getText(name));
        if (!commons.getListElement(phone).isEmpty())
            System.out.println(commons.getText(phone));
    }

}
