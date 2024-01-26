package page;

import org.openqa.selenium.By;

public class MapsLocator {
    By searchBox = By.cssSelector("#searchboxinput");
    By listResult = By.xpath("//*[contains(@class, 'Cw1rxd google-symbols')]|//*[contains(@class, 'hfpxzc')]");
    By noList = By.cssSelector("[jsaction='keydown:pane.wfvdle7tabs.keydown']");
    By name = By.xpath("//h1[text()]");
    By phone = By.xpath("//button[contains(@aria-label,'Phone:')]/div/div[2]/div[1]");
    By endOfPage = By.xpath("//*[contains(text(), \"You've reached the end of the list.\")]");

}
