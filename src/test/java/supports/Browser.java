package supports;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Browser {
    private WebDriver driver;

    private Browser(WebDriver driver) {
        this.driver = driver;
    }

    public static Browser newBrowser() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        return new Browser(driver);
    }

    public void open(String url) {
        driver.get(url);
    }

    public void quit() {
        driver.quit();
    }

    public WebElement find(By by) {
        return find(by, driver);
    }

    public WebElement find(By by, SearchContext searchContext) {
        return searchContext.findElement(by);
    }

    public List<WebElement> findAll(By by) {
        return driver.findElements(by);
    }

    public void type(By by, CharSequence charSequence) {
        type(find(by), charSequence);
    }

    public void type(WebElement element, CharSequence value) {
        element.sendKeys(value);
    }

    public void click(By by) {
        click(by, driver);
    }

    public void click(By by, SearchContext searchContext) {
        WebElement element = searchContext.findElement(by);
        element.click();
    }

    public void moveToElement(WebElement element) {
        new Actions(driver).moveToElement(element).perform();
    }

    public void doubleClick(WebElement element) {
        new Actions(driver).doubleClick(element).perform();
    }

    public void executeScript(String script, Object... arguments) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(script, arguments);
    }

}
