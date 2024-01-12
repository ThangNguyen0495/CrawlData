package utilities.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;

public class InitWebDriver {

    private WebDriver driver;

    public WebDriver getDriver(String browser, boolean... headlessMode) {
        boolean headless = headlessMode.length == 0 || headlessMode[0];
        if (driver == null) {
            switch (browser) {
                case "firefox" -> {
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    if (headless) firefoxOptions.addArguments("--headless");
                    driver = new FirefoxDriver(firefoxOptions);
                }
                case "edge" -> {
                    WebDriverManager.edgedriver().setup();
                    EdgeOptions edgeOptions = new EdgeOptions();
                    if (headless) edgeOptions.addArguments("--headless");
                    driver = new EdgeDriver(edgeOptions);
                }
                case "safari" -> {
                    WebDriverManager.safaridriver().setup();
                    driver = new SafariDriver();
                }
                default -> {
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    if (headless) chromeOptions.addArguments("--headless");
                    // fix org.openqa.selenium.WebDriverException: unknown error: cannot determine loading status from no such window
                    chromeOptions.addArguments("--disable-site-isolation-trials");
                    // fix 403 Forbidden
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    driver = new ChromeDriver(chromeOptions);
                }
            }
        }
        if (headless) driver.manage().window().setSize(new Dimension(1920, 1080));
        else driver.manage().window().maximize();
        return driver;
    }
}
