package webConnector;

import io.cucumber.core.api.Scenario;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.Platform.WIN10;
import static org.openqa.selenium.Platform.WINDOWS;

public class WebConnector {

    public static WebDriver driver = null;
    public SessionId session = null;
    public static Properties prop = new Properties();
    public static String userHomePath = "./src/test";
    private String Text;


    public WebConnector() {
        try {
            prop.load(new FileInputStream(userHomePath + "/config/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setUpDriver(Platform platform) {
        String browser = prop.getProperty("browser");
        if (browser == null) {
            browser = "chrome";
        }
        switch (browser) {
            case "chrome":
                if (platform == WINDOWS){
                    System.setProperty("webdriver.chrome.driver", userHomePath + "/lib/chromedriver.exe");
                }
                System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                options.addArguments("--disable-notifications");
                options.addArguments("--ignore-certificate-errors");
                options.addArguments("--disable-popup-blocking");
                options.addArguments("--no-proxy-server");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                Map<String, Object> prefs = new HashMap<>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                options.setExperimentalOption("prefs", prefs);
                driver = new ChromeDriver(options);
                break;
            case "firefox":
                System.setProperty("webdriver.gecko.driver", userHomePath + "/lib/geckodriver.exe");
                driver = new FirefoxDriver();
                driver.manage().window().maximize();
                //session = ((FirefoxDriver)driver).getSessionId();
                break;
            default:
                throw new IllegalArgumentException("Browser \"" + browser + "\" isn't supported.");
        }
    }

    public void closeDriver(Scenario scenario) {
        if (scenario.isFailed()) {
            saveScreenshotsForScenario(scenario);
        }
        driver.close();
    }

    private void saveScreenshotsForScenario(final Scenario scenario) {
        final byte[] screenshot = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.BYTES);
        scenario.embed(screenshot, "image/png");
    }


    public By getElementWithLocator(String WebElement) {
        String locatorTypeAndValue = prop.getProperty(WebElement);
        String[] locatorTypeAndValueArray = locatorTypeAndValue.split(",");
        String locatorType = locatorTypeAndValueArray[0].trim();
        String locatorValue = locatorTypeAndValueArray[1].trim();
        switch (locatorType.toUpperCase()) {
            case "ID":
                return By.id(locatorValue);
            case "NAME":
                return By.name(locatorValue);
            case "TAGNAME":
                return By.tagName(locatorValue);
            case "LINKTEXT":
                return By.linkText(locatorValue);
            case "PARTIALLINKTEXT":
                return By.partialLinkText(locatorValue);
            case "XPATH":
                return By.xpath(locatorValue);
            case "CSS":
                return By.cssSelector(locatorValue);
            case "CLASSNAME":
                return By.className(locatorValue);
            default:
                return null;
        }
    }

    public WebElement FindAnElement(String WebElement) {
        return driver.findElement(getElementWithLocator(WebElement));
    }


    public void waitForCondition(String TypeOfWait, String WebElement, int Time) {
        try {
            Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Time, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS).ignoring(Exception.class);
            switch (TypeOfWait) {
                case "PageLoad":
                    wait.until(ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";"));
                    break;
                case "Clickable":
                    wait.until(ExpectedConditions.elementToBeClickable(FindAnElement(WebElement)));
                    break;
                case "Presence":
                    wait.until(ExpectedConditions.presenceOfElementLocated(getElementWithLocator(WebElement)));
                    break;
                case "Visibility":
                    wait.until(ExpectedConditions.visibilityOfElementLocated(getElementWithLocator(WebElement)));
                    break;
                case "NotPresent":
                    wait.until(ExpectedConditions.invisibilityOfElementLocated(getElementWithLocator(WebElement)));
                    break;
                default:
                    Thread.sleep(Time * 1000);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("wait For Condition \"" + TypeOfWait + "\" isn't supported.");
        }
    }


    private static int maxTry = 3;

    private int count = 0;


    public void assertTrue(boolean condition, String message) {
        Assert.assertTrue(condition, message + "    is not found");
    }


    public void assertEquals(String condition1, String contions2, String message) {
        Assert.assertEquals(condition1, contions2, message + "  assert not equal");
        System.out.println(condition1 + "  =  " + contions2 + " assert equal ");
    }


    public WebElement sendKeysBy(By by, String value) {
        WebElement element = getElementBy(by);
        if (element == null) {
            System.out.println(element + "Element is null.  do not send");
        } else {
            scrollToElement(element);
            element.clear();
            element.sendKeys(value);
        }
        return element;

    }


    public String getTextElement(By by) {
        highlightElement2(by);
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        Text = getElementBy(by).getText().trim();
        return Text;
    }


    public void clickObjectBy(By by, String message) {
        WebElement element = getElementBy(by);
        if (!element.isDisplayed()) {
            scrollToElement(element);
            System.out.println(element + message + "not click");
        } else {
            scrollToPageUp();
        }
        if (by == null && element == null) {
            System.out.println(element + "   " + message + "   " + "not click");
        }
        try {
            element.click();
        } catch (Exception e) {
            System.out.println(e + "     " + "not click");
        }
    }


    public java.util.List<WebElement> waitForElements(By by) {
        WebDriverWait webDriverWaitElements = new WebDriverWait(driver, 30);
        return webDriverWaitElements.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }


    public WebElement findElement(By by, int... index) {
        if (index.length == 0) {
            return driver.findElement(by);
        } else if (index[0] >= 0) {
            java.util.List<WebElement> elements = waitForElements(by);
            if (!elements.isEmpty() && index[0] <= elements.size()) {
                return elements.get(index[0]);
            }
        }
        return null;
    }

    protected WebElement getElementBy(By by) {
        WebElement element;
        try {
            element = driver.findElement(by);
            if (element == null) {
                if (count < maxTry) {
                    count++;
                    System.out.println(count + "search to element");
                    WebDriverWait wait = new WebDriverWait(driver, 30);
                    wait.until(ExpectedConditions.visibilityOfElementLocated(by));
                    element = driver.findElement(by);
                }
            }
        } catch (Exception ex) {
            element = null;
            System.out.println("not found" + ex.getMessage());
        }
        return element;
    }


    public String getCurrentUrl() {
        String text;
        text = driver.getCurrentUrl().trim();
        return text;
    }

    public int responseCodeCheck() {
        int respCode = 200;
        HttpURLConnection huc = null;
        String url = getCurrentUrl();
        if (url == null || url.isEmpty()) {
            System.out.println("URL is either not configured for anchor tag or it is empty");
        }
        if (!url.startsWith(url)) {
            System.out.println("URL belongs to another domain, skipping it.");
        }
        try {
            huc = (HttpURLConnection) (new URL(url).openConnection());
            huc.setRequestMethod("HEAD");
            huc.connect();
            respCode = huc.getResponseCode();
            if (respCode >= 400) {
                System.out.println(url + " is a broken link" + "  response code is   " + respCode);
            } else {
                System.out.println(url + " is a valid link" + "  response code is   " + respCode);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return respCode;
    }


    public void sleep(long time) {
        for (int i = 10; i < 13; i++) {
            try {
                long start = System.currentTimeMillis();
                Thread.sleep(time);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    public void activeButtonControl(By by, String status) {
        WebElement aa = getElementBy(by);
        String c_aa = aa.getAttribute("class");
        if (c_aa.contains("inactive") && status.equalsIgnoreCase("active")) {
            Assert.fail("Button" + c_aa + "  found. But expected " + status);
        } else if (c_aa.contains("active") && status.equalsIgnoreCase("inactive")) {
            Assert.fail("Button" + c_aa + "  found. But expected " + status);
        }
    }


    public boolean isElementPresent(By by) {
        try {
            getElementBy(by);
            highlightElement2(by);
            return true;
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    private WebElement highlightElement2(By locator) {
        WebElement elem = driver.findElement(locator);
        scrollToElement(elem);
        if (driver instanceof JavascriptExecutor) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='4px solid yellow'", elem);
        }
        return elem;
    }


    public boolean isTextPresent(String text) {
        try {
            driver.getPageSource().contains(text);
            return true;
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    protected void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        sleep(200);
    }


    public void scrollToPageUp() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, -document.body.scrollHeight);");
        sleep(1000);
    }


}
