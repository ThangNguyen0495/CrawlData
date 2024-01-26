package utilities;

import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.screenshot.Screenshot;

import java.time.Duration;
import java.util.List;

import static java.lang.Thread.sleep;

public class UICommonAction {

    final static Logger logger = LogManager.getLogger(UICommonAction.class);


    WebDriver driver;
    WebDriverWait wait;
    Actions actions;

    public UICommonAction(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        actions = new Actions(driver);
    }

    @SneakyThrows
    private void sleepInMs(int ms) {
        sleep(ms);
    }

    public WebElement getElement(By by) {
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (StaleElementReferenceException ex) {
            return wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (WebDriverException ex) {
            driver.navigate().refresh();
            return wait.until(ExpectedConditions.presenceOfElementLocated(by));
        }
    }

    public WebElement getElement(By by, int index) {
        try {
            return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by)).get(index);
        } catch (StaleElementReferenceException ex) {
            return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by)).get(index);
        }
    }

    /*
        common for POM modals
     */
    public List<WebElement> getListElement(By locator) {
        try {
            try {
                new WebDriverWait(driver, Duration.ofMillis(500)).until(ExpectedConditions.numberOfElementsToBeMoreThan(locator, 0));
            } catch (TimeoutException ignore) {
            }

            return driver.findElements(locator).isEmpty()
                    ? driver.findElements(locator)
                    : wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        } catch (WebDriverException ex) {
            driver.navigate().refresh();
            return driver.findElements(locator).isEmpty()
                    ? driver.findElements(locator)
                    : wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        }
    }

    public void click(By locator) {
        try {
            getElement(locator).click();
        } catch (StaleElementReferenceException | ElementNotInteractableException ex) {
            wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        }
    }

    public void click(By locator, int index) {
        try {
            getElement(locator, index).click();
        } catch (StaleElementReferenceException | ElementClickInterceptedException ex) {
            getElement(locator, index).click();
        }
    }

    public void clickJS(By locator) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click()", getElement(locator));
        } catch (StaleElementReferenceException ex) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click()", getElement(locator));
        }
    }

    public void clickJS(By locator, int index) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click()", getElement(locator, index));
        } catch (StaleElementReferenceException ex) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click()", getElement(locator, index));
        }
    }

    public void clickActions(By locator) {
        hoverActions(locator);
        actions.click().build().perform();
    }

    public void clickActions(By locator, int index) {
        hoverActions(locator, index);
        actions.click().build().perform();
    }

    public void hoverActions(By locator) {
        try {
            actions.moveToElement(getElement(locator)).build().perform();
            sleep(500);
        } catch (StaleElementReferenceException | InterruptedException | ElementClickInterceptedException ex) {
            actions.moveToElement(getElement(locator)).build().perform();
            try {
                sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void hoverActions(By locator, int index) {
        try {
            actions.moveToElement(getElement(locator, index)).build().perform();
        } catch (StaleElementReferenceException | ElementClickInterceptedException ex) {
            actions.moveToElement(getElement(locator, index)).build().perform();
        }
    }

    public void sendKeys(By locator, CharSequence content) {
        visibilityOfElementLocated(locator);
        clear(locator);
        try {
            getElement(locator).sendKeys(content);
        } catch (WebDriverException ex) {
            driver.navigate().refresh();
            getElement(locator).sendKeys(content);
        }

    }

    public void sendKeys(By locator, int index, CharSequence content) {
        visibilityOfElementLocated(locator, index);
        clear(locator, index);
        try {
            getElement(locator, index).sendKeys(content);
        } catch (StaleElementReferenceException ex) {
            getElement(locator, index).sendKeys(content);
        }
//        actions.keyDown(Keys.TAB).keyUp(Keys.TAB).build().perform();
    }

    public void uploads(By locator, CharSequence content) {
        try {
            getElement(locator).sendKeys(content);
        } catch (StaleElementReferenceException ex) {
            getElement(locator).sendKeys(content);
        }
    }

    public void uploads(By locator, int index, CharSequence content) {
        try {
            getElement(locator, index).sendKeys(content);
        } catch (StaleElementReferenceException ex) {
            getElement(locator, index).sendKeys(content);
        }
    }

    public String getText(By locator) {
        return getAttribute(locator, "innerText");
    }

    public String getText(By locator, int index) {
        String textContent = "";
        try {
            textContent = getElement(locator, index).getText();
        } catch (StaleElementReferenceException ignore) {
        }
        return !textContent.isEmpty() ? textContent : getText(locator, index);
    }

    public String getValue(By locator) {
        try {
            return getAttribute(locator, "value");
        } catch (StaleElementReferenceException ignore) {
            return getAttribute(locator, "value");
        }
    }

    public String getValue(By locator, int index) {
        try {
            return getAttribute(locator, index, "value");
        } catch (StaleElementReferenceException ignore) {
            return getAttribute(locator, index, "value");
        }
    }

    public String getAttribute(By locator, int index, String attribute) {
        try {
            return getElement(locator, index).getAttribute(attribute);
        } catch (WebDriverException ex) {
            driver.navigate().refresh();
            return getElement(locator, index).getAttribute(attribute);
        }
    }

    public String getAttribute(By locator, String attribute) {
        try {
            return getElement(locator).getAttribute(attribute);
        } catch (WebDriverException ex) {
            driver.navigate().refresh();
            return getElement(locator).getAttribute(attribute);
        }
    }

    void clear(By locator) {
        hoverActions(locator);
        clickActions(locator);
        actions.keyDown(Keys.CONTROL)
                .sendKeys("A")
                .keyDown(Keys.DELETE)
                .keyUp(Keys.CONTROL)
                .keyUp(Keys.DELETE)
                .build()
                .perform();
    }

    void clear(By locator, int index) {
        hoverActions(locator, index);
        clickActions(locator, index);
        actions.keyDown(Keys.CONTROL)
                .sendKeys("A")
                .keyUp(Keys.CONTROL)
                .keyDown(Keys.DELETE)
                .keyUp(Keys.DELETE)
                .build()
                .perform();
    }

    public boolean isCheckedJS(By locator) {
        try {
            return (boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].checked", getElement(locator));
        } catch (StaleElementReferenceException ex) {
            return (boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].checked", getElement(locator));
        }
    }

    public boolean isCheckedJS(By locator, int index) {
        try {
            return (boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].checked", getElement(locator, index));
        } catch (StaleElementReferenceException ex) {
            return (boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].checked", getElement(locator, index));
        }
    }

    public void removeFbBubble() {
        if (!getListElement(By.cssSelector("#fb-root")).isEmpty())
            ((JavascriptExecutor) driver).executeScript("arguments[0].remove()", getElement(By.cssSelector("#fb-root")));
    }

    public String getLangKey() {
        return ((JavascriptExecutor) driver).executeScript("return localStorage.getItem('langKey')").toString();
    }

    public void visibilityOfElementLocated(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void visibilityOfElementLocated(By locator, int index) {
        wait.until(ExpectedConditions.visibilityOf(getElement(locator, index)));
    }

    public void invisibilityOfElementLocated(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public void elementToBeClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void elementToBeClickable(By locator, int index) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(getElement(locator, index)));
        } catch (StaleElementReferenceException ex) {
            wait.until(ExpectedConditions.elementToBeClickable(getElement(locator, index)));
        }
    }

    public void waitURLShouldBeContains(String path) {
        // wait product list page is loaded
        wait.until((ExpectedCondition<Boolean>) driver -> {
            assert driver != null;
            return driver.getCurrentUrl().contains(path);
        });
    }

    /* Click and wait popup closed.*/
    public void closePopup(By locator) {
        try {
            clickJS(locator);
            sleepInMs(200);
        } catch (StaleElementReferenceException | NoSuchElementException ignore) {
        }
        if (!getListElement(locator).isEmpty()) {
            closePopup(locator);
        }
    }


    /* Click and wait popup opened.*/
    public void openPopupJS(By locator, By popup) {
        try {
            clickJS(locator);
            sleepInMs(500);
        } catch (StaleElementReferenceException ignore) {
        }
        if (getListElement(popup).isEmpty()) openPopupJS(locator, popup);
    }

    public void openPopupJS(By locator, int index, By popup) {
        try {
            clickJS(locator, index);
            sleepInMs(500);
        } catch (StaleElementReferenceException ignore) {
        }
        if (getListElement(popup).isEmpty()) openPopupJS(locator, index, popup);
    }

    public void openDropdownJS(By locator, By dropdown) {
        try {
            clickJS(locator);
            sleepInMs(200);
        } catch (StaleElementReferenceException ignore) {
        }
        if (getListElement(dropdown).isEmpty()) openDropdownJS(locator, dropdown);
    }

    public void openDropdownJS(By locator, int index, By dropdown) {
        try {
            clickJS(locator, index);
            sleepInMs(200);
        } catch (StaleElementReferenceException ignore) {
        }
        if (getListElement(dropdown).isEmpty()) openDropdownJS(locator, index, dropdown);
    }

    public void closeDropdown(By locator, By dropdown) {
        try {
            clickJS(locator);
            sleepInMs(200);
        } catch (StaleElementReferenceException ignore) {
        }
        if (!getListElement(dropdown).isEmpty()) closeDropdown(locator, dropdown);
    }

    public void viewTooltips(By locator, By tooltips) {
        hoverActions(locator);
        if (getListElement(tooltips).isEmpty()) hoverActions(locator);
    }

    public void scrollIntoView(By locator, int index) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", getElement(locator, index));
        } catch (WebDriverException ex) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", getElement(locator, index));
        }
    }
}
