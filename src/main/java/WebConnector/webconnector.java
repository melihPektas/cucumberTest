package WebConnector;

import io.cucumber.core.api.Scenario;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.xssf.usermodel.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class webconnector {

    public static WebDriver driver = null;
    public SessionId session = null;
    public static Properties prop = new Properties();
    public static String userHomePath = "./src/test";
    private String Text;

    public webconnector() {
        try {
            prop.load(new FileInputStream(userHomePath + "/config/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WebDriver getDriver() {
        return this.getDriver();
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public void setUpDriver() {
        String browser = prop.getProperty("browser");
        if (browser == null) {
            browser = "chrome";
        }
        switch (browser) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", userHomePath + "/lib/chromedriver.exe");
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

    public void waitForPageLoad(int timeout) {
        ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";");
    }

    public String getSpecificColumnData(String FilePath, String SheetName, String ColumnName) throws InvalidFormatException, IOException {
        FileInputStream fis = new FileInputStream(FilePath);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheet(SheetName);
        XSSFRow row = sheet.getRow(0);
        int col_num = -1;
        for (int i = 0; i < row.getLastCellNum(); i++) {
            if (row.getCell(i).getStringCellValue().trim().equals(ColumnName))
                col_num = i;
        }
        row = sheet.getRow(1);
        XSSFCell cell = row.getCell(col_num);
        String value = cell.getStringCellValue();
        fis.close();
        System.out.println("Value of the Excel Cell is - " + value);
        return value;
    }

    public void setSpecificColumnData(String FilePath, String SheetName, String ColumnName) throws IOException {
        FileInputStream fis;
        fis = new FileInputStream(FilePath);
        FileOutputStream fos = null;
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheet(SheetName);
        XSSFRow row = null;
        XSSFCell cell = null;
        XSSFFont font = workbook.createFont();
        XSSFCellStyle style = workbook.createCellStyle();
        int col_Num = -1;
        row = sheet.getRow(0);
        for (int i = 0; i < row.getLastCellNum(); i++) {
            if (row.getCell(i).getStringCellValue().trim().equals(ColumnName)) {
                col_Num = i;
            }
        }
        row = sheet.getRow(1);
        if (row == null)
            row = sheet.createRow(1);
        cell = row.getCell(col_Num);
        if (cell == null)
            cell = row.createCell(col_Num);
        font.setFontName("Comic Sans MS");
        font.setFontHeight(14.0);
        font.setBold(true);
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);
        style.setFillForegroundColor(HSSFColor.GREEN.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cell.setCellStyle(style);
        cell.setCellValue("PASS");
        fos = new FileOutputStream(FilePath);
        workbook.write(fos);
        fos.close();
    }

    public By getElementWithLocator(String WebElement) throws Exception {
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


    public WebElement FindAnElement(String WebElement) throws Exception {
        return driver.findElement(getElementWithLocator(WebElement));
    }

    public WebElement findElement(By locator) throws Exception {
        return driver.findElement(locator);
    }

    public void PerformActionOnElement(String WebElement, String Action, String Text) throws Exception {
        switch (Action) {
            case "Click":
                FindAnElement(WebElement).click();
                break;
            case "Type":
                FindAnElement(WebElement).sendKeys(Text);
                break;
            case "Clear":
                FindAnElement(WebElement).clear();
                break;
            case "WaitForElementDisplay":
                waitForCondition("Presence", WebElement, 60);
                break;
            case "WaitForElementClickable":
                waitForCondition("Clickable", WebElement, 60);
                break;
            case "ElementNotDisplayed":
                waitForCondition("NotPresent", WebElement, 60);
                break;
            default:
                throw new IllegalArgumentException("Action \"" + Action + "\" isn't supported.");
        }
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


    private static int index = 0;
    private static int loginReTryCount = 0;
    private static int loginMaxReTryCount = 2;
    private static int loginSleepMs = 5000;
    private static int maxTry = 3;

    private int count = 0;


    //protected WaitingActions waitingAction;
    private JavascriptExecutor jsExec;
    private String aa;

	/*
	public BasePageUtil {
	//	waitingAction = new WaitingActions(driver);
		jsExec = (JavascriptExecutor) driver;
	}

	 */


    public void bigList(int a) {

        for (int i = 1; i < a; i++) {
            scrollToElement(driver.findElement(By.cssSelector(".component-big-list.component-list > article:nth-of-type" + "(" + i + ")")));
            if (driver instanceof JavascriptExecutor) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='4px solid yellow'", driver.findElement(By.cssSelector(".component-big-list.component-list > article:nth-of-type" + "(" + i + ")")));
                System.out.println(i + "big list component");
                System.out.println();
                // System.out.println(driver.findElement(By.cssSelector(".component-big-list.component-list > article:nth-of-type" + "(" + i + ")")));
            }
        }
    }

    public void smallList(int b) {

        for (int i = 1; i <= b; i++) {
            scrollToElement(driver.findElement(By.cssSelector(".component-list.component-small-list > article:nth-of-type" + "(" + i + ")")));
            if (driver instanceof JavascriptExecutor) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='4px solid yellow'", driver.findElement(By.cssSelector(".component-list.component-small-list > article:nth-of-type" + "(" + i + ")")));
                System.out.println(i + "small list component");
                //System.out.println(driver.findElement(By.cssSelector(".component-list.component-small-list > article:nth-of-type" + "(" + i + ")")));
            }
        }
    }


    protected static String getRandomDate() {
        int findDay = 0;
        int findmonth = 0;
        Date d = new Date();
        SimpleDateFormat sp = new SimpleDateFormat("dd-MM-yyyy");
        String dateNow = sp.format(d);

        String[] parts = dateNow.split("-");
        int day = Integer.valueOf(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);
        Random r = new Random();
        findmonth = r.nextInt(12 - (month - 1)) + month;
        findDay = 1 + r.nextInt(daysInMonth(findmonth, year));

        if (month == findmonth) {
            findDay = r.nextInt(daysInMonth(month, year) - (day - 1)) + day;

        }
        return findDay + "." + findmonth + "." + year;
    }

    private static int daysInMonth(int month, int leapYear) {
        int dInMonth;
        if (month == 4 || month == 6 || month == 9 || month == 11)
            dInMonth = 30;
        else if (month == 2)
            dInMonth = (leapYear % 4 == 0) ? 29 : 28;
        else
            dInMonth = 31;
        return dInMonth;
    }

    /*
     * public void waitElementDisappear(By by, int second) { int counter = 0; while
     * (isElementPresent(by) && counter < (second)) { sleep(2000); counter += 2; }
     *
     * }
     */
    public static int getRandomNumber(int listSize) {
        return new Random().nextInt(listSize);
    }

    protected static String generateRandomNumericString(int length) {
        String alphabet = "0123456789"; // 9
        int n = alphabet.length(); // 10
        String result = "";
        Random r = new Random(); // 11
        for (int i = 0; i < length; i++) {
            String stringBuilder = result +
                    alphabet.charAt(r.nextInt(n));
            result = stringBuilder; // 13
        }
        return result;
    }

    protected static String generateRandomString(int length) {
        String alphabet = "0123456789ABCDEFGHIJKLMNOPRSTUVYZabcdefghijklmnoprstuvyz"; // 9
        int n = alphabet.length(); // 10

        String result = "";
        Random r = new Random(); // 11

        for (int i = 0; i < length; i++) {
            String stringBuilder = result +
                    alphabet.charAt(r.nextInt(n));
            result = stringBuilder; // 13
        }
        System.out.println("randomKeyword:" + result);
        return result;
    }

    public static String toLowerCaseTR(String text) {
        return text.toLowerCase(new Locale("TR", "tr"));
    }

    public static String toUpperCaseTR(String text) {
        return text.toUpperCase(new Locale("TR", "tr"));
    }

    public static void resetIndex() {
        index = 0;
    }





/*
	protected void waitAllRequests() {
		waitingAction.pageLoadComplete();
		waitingAction.jQueryComplete( );
		waitingAction.waitForAngularLoad();
		waitingAction.ajaxComplete( );
	}

 */

    public void assertTrue(boolean condition, String message) {
        Assert.assertTrue(condition, message + "    is not found");
        System.out.println(condition + "Assert equal to true");
    }


    public void assertEquals(String condition1, String contions2, String message) {
        Assert.assertEquals(condition1, contions2, message + "  assert not equal");
        System.out.println(condition1 + "  =  " + contions2 + " assert equal ");
    }


    protected void assertTrueLoop(boolean condition, String message) {
        int sleepIndex = 0;

        while (true) {
            for (int i = 0; i < 13000; i++) {
                Assert.assertTrue(condition, message + "    is not found");
                if (condition == true) {
                    sleepIndex = 1299;
                }
                sleepIndex = i;
            }
        }
    }

    protected void whileLoop(boolean condition, String message) {
        while (true) {
            try {

                Assert.assertTrue(condition, message + "    is not found");
                System.out.println(condition + "   " + message);
                sleep(10000);
            } catch (NoSuchElementException ex) {
                System.out.println(condition + "   " + message);
                break;   // button is missing, exit the loop
            }
            if (!condition) {
                System.out.println(condition + "   " + message);
                break;   // button is hidden, exit the loop
            }
        }
    }

    protected void zoom() {


        try {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("document.body.style.zoom = '0.75'");
            System.out.println("zoom succes");
        } catch (Exception e) {
            System.out.println("zoom error");
            System.out.println();
        }
    }

    protected void url(String URLL) {
        driver.navigate().to(URLL);

    }

    protected void assertTrueScript(Object executeScript, String message) {

        Assert.assertTrue((Boolean) executeScript, message);
        System.out.println(executeScript + "Assert equal to true");
    }

    public WebElement submitObjectBy(By by) {
        WebElement element = getElementBy(by);
        element.submit();
        System.out.println(element + "Assert True");
        return element;
    }

    public WebElement sendKeysBy(By by, String value) {
        WebElement element = getElementBy(by);
        if (element == null) {
            System.out.println(element + "Element is null.  do not send");
        } else {
            scrollToElement(element);
            element.clear();
            element.sendKeys(value);
            System.out.println(element + "key");
        }
        return element;

    }


    protected WebElement clearKeysBy(By by) {
        WebElement element = getElementBy(by);
        element.clear();
        System.out.println(element + "key");
        return element;

    }


    public WebElement selectOptionBy(By by, String value) {
        WebElement element = driver.findElement(by);
        new Select(element).selectByVisibleText(value);

        scrollToElement(element);
        // takesScreenshot();
        return element;
    }

    public WebElement selectOptionBy(WebElement parentElement, By by, String value) {
        WebElement element = parentElement.findElement(by);
        new Select(element).selectByVisibleText(value);

        scrollToElement(element);
        // takesScreenshot();
        return element;
    }

    public WebElement selectIndexOptionBy(String name, int index) {
        WebElement element = driver.findElement(By.id(name));
        new Select(element).selectByIndex(index);
        // takesScreenshot();
        return element;
    }

    public void clickByWebElement(WebElement el) {

        if (!el.isDisplayed()) {
            scrollDownPageUntilView(el);
        }
        el.click();
    }

    public String getTextElement(WebElement element) {
        return element.getText().trim();
    }

    public String getTextElement(By by) {
        highlightElement2(by);
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        Text = getElementBy(by).getText().trim();
        return Text;
    }

    protected void scrollTo(int x, int y) {
        String jsStmt = String.format("window.scrollTo(%d %d);", x, y);
        executeJavascript(jsStmt, "execute", true);
        sleep(1000);
    }

    public String elementGetText(By by) {
        return getElementBy(by).getText().trim();
    }


    public void clickObjectBy(By by, String message) {
        //  waitAllRequests( );
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
            System.out.println(message + "     " + "click");
        } catch (Exception e) {
            System.out.println(e + "     " + "not click");
        }
    }

    protected void clickObjectByB(By by, String message) {
        WebElement element = getElementBy(by);
        if (!element.isDisplayed()) {
            scrollToElement(element);
            System.out.println(element + message + "not click");
        } else {
            scrollToPageUp();
        }
        if (by == null && element == null) {
            System.out.println(element + "   " + message + "   " + "not click");
        } else {

            sleep(1000);
            try {
                element.click();
                System.out.println(message + "     " + "click");
            } catch (Exception e) {
                System.out.println(e + "     " + "click");
            }
        }
    }


    protected void clickObjectByAsset(By by, String message) {
        WebElement element = getElementBy(by);
        if (by == null) {
            System.out.println(element + message + "not click");
        }
        if (element.isDisplayed()) {
            scrollToElement(element);
            System.out.println(element + message + "not isDisplayed");
        } else {
            scrollToPageUp();
        }


        jsExec.executeScript("arguments[0].style.backgroundColor = 'green'", element);

        sleep(1000);
        try {
            element.click();
            System.out.println(message + "     " + "click");
        } catch (Exception e) {
            System.out.println(message + "     " + "click");
        }

    }

    protected void clickObjectByWithoutHighlight(By by) {
        WebElement element = getElementBy(by);
        if (!element.isDisplayed()) {
            scrollToElement(element);
        } else {
            System.out.println("is displayed");
        }
        sleep(1000);
        element.click();
    }

    protected void clickOBJECT(By by, String message) {
        if (by != null) {
            WebElement element = getElementBy(by);
            scrollToPageUp();
            jsExec.executeScript("arguments[0].style.backgroundColor = 'green'", element);
            sleep(1000);
            System.out.println(element);
            element.click();
            System.out.println(message + "    is  click");
        } else {
            System.out.println(message + "    is not click");
        }

    }

    protected void clickOBJECTMoveElement(By by, String message) {
        sleep(1000);
        if (by != null) {
            WebElement element = getElementBy(by);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            jsExec.executeScript("arguments[0].style.backgroundColor = 'green'", element);
            jsExec.executeScript("arguments[0].scrollIntoView()", element);
            jsExec.executeScript("arguments[0].click()", element);
            sleep(2000);
            System.out.println(message + "    is  click");
        } else {
            System.out.println(message + "    is not click");
        }

    }

    protected void getTitle() {
        driver.getTitle();
        System.out.println(driver.getTitle());

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

    private java.util.List<WebElement> getElementsBy(By by) {

        java.util.List<WebElement> elementList = null;
        try {
            elementList = driver.findElements(by);
        } catch (Exception e) {
            elementList = null;
        }
        return elementList;
    }

    public boolean isDisplayBy(By by) {

        WebElement el = getElementBy(by);
        if (el == null)
            return false;
        else
            return el.isDisplayed();
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

    public void goBack() {
        driver.navigate().back();
    }

    public void sleep(long time) {
        for (int i = 10; i < 13; i++) {
            try {
                long start = System.currentTimeMillis();
                Thread.sleep(time);
                //System.out.println("Sleep time in ms = " + (System.currentTimeMillis() - start) + "  " + Thread.currentThread().getName() + "  " + i);
            } catch (InterruptedException e) {

                System.out.println(e.getMessage());
            }
        }
    }

    protected void executeJavascript(String script, String message, Object... obj) {
        ((JavascriptExecutor) driver).executeScript(script, obj);
        System.out.println("Java Script" + message);
    }


    public void activeButtonControl(By by, String statu) {
        WebElement aa = getElementBy(by);
        String c_aa = aa.getAttribute("class");
        if (c_aa.contains("inactive") && statu.equalsIgnoreCase("active")) {
            Assert.fail("Button" + c_aa + "  found. But expected " + statu);
        } else if (c_aa.contains("active") && statu.equalsIgnoreCase("inactive")) {
            Assert.fail("Button" + c_aa + "  found. But expected " + statu);
        }
    }


    public boolean isElementPresent(By by) {
        try {
            getElementBy(by);
            highlightElement2(by);
            System.out.println("is element present");
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


    protected void hoverElement(By locator) {

        WebElement element = getElementBy(locator);
        if ("chrome".equals(System.getProperty("browser"))) {
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
            System.out.println("ok");
        } else {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String locatorType = locator.toString().substring(3);
            String elem = "var elem = document;";
            if (locatorType.startsWith("id")) {
                elem = "var elem = document.getElementById(\"" + locatorType.substring(4) + "\");";
            } else if (locatorType.startsWith("xpath")) {
                String snippet = "document.getElementByXPath = function(sValue) { var a = this.evaluate(sValue,	this, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null); if (a.snapshotLength > 0)	{ return a.snapshotItem(0); } }; ";
                js.executeScript(snippet);
                elem = "var elem = document.getElementByXPath(\"" + locatorType.substring(7) + "\");";
            } else if (locatorType.startsWith("className")) {
                elem = "var elem = document.getElementsByClassName(\"" + locatorType.substring(14) + "\")[0];";
            }
            String mouseOverScript = elem
                    + " if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover',true, false);"
                    + " elem.dispatchEvent(evObj);} else if(document.createEventObject) { elem.fireEvent('onmouseover');}";
            js.executeScript(mouseOverScript);
        }
    }


    public void moveToElement(By locator) {

        hoverElement(locator);
    }

    public Object getRandomContent(java.util.List<?> contentList) {
        Random rand = new Random();
        int n = rand.nextInt(contentList.size());
        return contentList.get(n);
    }

    public WebElement getButtonByText(WebElement element, String text) {
        try {
            return element.findElement(By.xpath("//button[contains(text(),'" + text + "')]"));
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public WebElement getByContainsText(String text) {
        try {
            return
                    getElementBy(By.xpath("//*[contains(text(),'" + text + "')]"));
        } catch
        (NoSuchElementException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public WebElement getByContainsAttributeText(String attribute, String text) {
        try {
            return getElementBy(By.xpath("//*[contains(@" + attribute + ",'" + text + "')]"));
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public WebElement getByContainsAttributeTextAndFindChildElement(String attribute, String attributeText, String text) {
        try {
            return getElementBy(By.xpath("//*[contains(@" + attribute + ",'" + attributeText +
                    "')] //*[contains(text(),'" + text + "')]"));
        } catch (NoSuchElementException
                e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public WebElement findParentElementByXpath(By by, int parentIndex) {
        String xpath = "";
        try {
            for (int i = 0; i < parentIndex; i++) {
                xpath += "/src/main/java/WebConnector";
            }
            System.out.println("findParentElementByXpath:" + by.toString().substring(10) + xpath);
            return getElementBy(By.xpath(by.toString().substring(10) + xpath));
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public WebElement findParentElementByXpath(WebElement element, int parentIndex) {
        try {
            for (int i = 0; i < parentIndex; i++) {
                element = element.findElement(By.xpath(".."));
            }
            return element;
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    protected void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        sleep(200);
    }

    protected void scrollDownPageUntilView(By by) {


        WebElement element = driver.findElement(by);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        scrollToPageDown();
        sleep(1000);
        System.out.println(element);
    }

    private void scrollDownPageUntilView(WebElement element) {


        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        scrollToPageDown();
        sleep(1000);
        System.out.println(element);
    }

    protected void scrollToPageDown() {


        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
        sleep(1000);
    }

    public void scrollToPageUp() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, -document.body.scrollHeight);");
        sleep(1000);
    }


    public String toUpperCaseTr(String text) {
        return text.toUpperCase(new Locale("tr", "TR"));
    }


    /**
     * Arraylist sıralama
     */

    public void arraySortDescending(ArrayList<String> list) {
        Collections.sort(list, Collections.reverseOrder());
        System.out.println("ListSortDescending");
    }

    protected void switchToFrame(By frame) {

        driver.switchTo().defaultContent();
        driver.switchTo().frame(getElementBy(frame));
    }

    /**
     * Switch to default content
     */
    protected void switchToDefaultContent() {
        sleep(1000);
        System.out.println("switchToDefaultContent");
        driver.switchTo().defaultContent();
    }

}
