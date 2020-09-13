package ApplicationPages;

import WebConnector.webconnector;
import org.testng.Assert;

import static ConstantVariables.Constant.*;
import static WebConnector.webconnector.driver;

public class Homepage {
    webconnector wc = new webconnector();

    public void goToHomePage(String url) {
        driver.get(url);
        wc.waitForCondition("PageLoad", "", 60);
    }


    public void verifyPageTitle(String arg0)  {
        wc.assertTrue(wc.isTextPresent(arg0)," not ok" );
        wc.assertTrue(wc.isElementPresent(FORM_BUTTON), "FORM_BUTTON");
        wc.clickObjectBy(FORM_BUTTON, "FORM_BUTTON");
        wc.assertTrue(wc.isTextPresent(arg0)," not ok" );
    }

    public void verifyPageLogoIsVisible() throws Exception {
        wc.assertTrue(wc.isElementPresent(SITE_LOGO), "SITE_LOGO");
        wc.assertTrue(wc.isElementPresent(HOME_BUTTON), "HOME_BUTTON");
        wc.clickObjectBy(HOME_BUTTON, "HOME_BUTTON");
        wc.assertTrue(wc.isElementPresent(SITE_LOGO), "SITE_LOGO");
    }

    public void iClickTheHomeButtonGetNavigatedToTheHomePageUrl(String arg0) {
        wc.assertTrue(wc.isElementPresent(HOME_BUTTON), "HOME_BUTTON");
        wc.clickObjectBy(HOME_BUTTON, "HOME_BUTTON");
        wc.assertEquals(wc.getCurrentUrl(), arg0, "url");
    }

    public void iClickOnTheHomeButtonItIsActiveStatus() {
        wc.assertTrue(wc.isElementPresent(HOME_BUTTON), "HOME_BUTTON");
        wc.clickObjectBy(HOME_BUTTON, "HOME_BUTTON");
        wc.activeButtonControl(HOME_BUTTON, "active");
    }

    public void iClickTheFormButtonGetNavigatedToTheFormPageUrl(String arg0) {
        wc.assertTrue(wc.isElementPresent(FORM_BUTTON), "FORM_BUTTON");
        wc.clickObjectBy(FORM_BUTTON, "FORM_BUTTON");
        wc.assertEquals(wc.getCurrentUrl(), arg0, "url");
    }

    public void iClickOnTheFormButtonItIsActiveStatus() {
        wc.assertTrue(wc.isElementPresent(FORM_BUTTON), "FORM_BUTTON");
        wc.clickObjectBy(FORM_BUTTON, "FORM_BUTTON");
        wc.activeButtonControl(FORM_BUTTON, "active");
    }

    public void iClickOnTheErrorButtonItTakeAHTTPResponseCode(int arg0) {
        wc.assertTrue(wc.isElementPresent(ERROR_BUTTON), "ERROR_BUTTON");
        wc.clickObjectBy(ERROR_BUTTON, "ERROR_BUTTON");
        int responseCode = wc.responseCodeCheck();
        Assert.assertEquals(responseCode, arg0, responseCode+ " HTTP response code");
    }

	public void iClickOnTheUITestingButtonIShouldGetNavigatedToHomePageUrl(String arg0) {
        driver.get("http://uitest.duodecadits.com");
        wc.assertTrue(wc.isElementPresent(UI_TESTING_BUTTON), "UI_TESTING_BUTTON");
        wc.clickObjectBy(UI_TESTING_BUTTON, "UI_TESTING_BUTTON");
        wc.assertEquals(wc.getCurrentUrl(), arg0, "url");

	}

    public void theFollowingTextShouldBeVisibleOnTheHomePageIn(String arg0) {
        wc.assertEquals(wc.getTextElement(TEXT_H1), arg0, "text");
    }

    public void theFollowingTextShouldBeVisibleOnTheHomePageInPTag(String arg0) {
        wc.assertEquals(wc.getTextElement(TEXT_P), arg0, "text");
    }

    public void onTheFormPageAFormShouldBeVisibleWithOneAndOne(String arg0, String arg1) {
        wc.clickObjectBy(FORM_BUTTON, "FORM_BUTTON");
        wc.assertTrue(wc.isElementPresent(INPUT), "INPUT");
        wc.assertTrue(wc.isElementPresent(SUBMIT), "SUBMIT");
    }


    public void openTheFormPage(String arg0) {
        driver.get(arg0);
        wc.waitForCondition("PageLoad", "", 60);
    }

    public void enterTheValueAndResult(String arg0, String arg1) {
        wc.assertTrue(wc.isElementPresent(INPUT), "INPUT");
        wc.sendKeysBy(INPUT,arg0);
        wc.assertTrue(wc.isElementPresent(SUBMIT), "SUBMIT");
        wc.clickObjectBy(SUBMIT,"SUBMIT");
        wc.assertEquals(wc.getTextElement(HELLO_TEXT),arg1,"not ok");
    }
}