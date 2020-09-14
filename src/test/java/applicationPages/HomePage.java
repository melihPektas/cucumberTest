package applicationPages;

import org.testng.Assert;
import webConnector.WebConnector;

import static constantVariables.Constant.*;
import static webConnector.WebConnector.driver;

public class HomePage {
    WebConnector wc = new WebConnector();

    /**
     * @param url Method is navigate to given URL
     */

    public void goToHomePage(String url) {
        driver.get(url);
        wc.waitForCondition("PageLoad", "", 60);
    }


    /**
     * @param arg0 Method is check to given page source title
     */
    public void verifyPageTitle(String arg0) {
        wc.assertTrue(wc.isTextPresent(arg0), " not ok");
        wc.assertTrue(wc.isElementPresent(FORM_BUTTON), "Form button isn't present");
        wc.clickObjectBy(FORM_BUTTON, "Form button is clickable");
        wc.assertTrue(wc.isTextPresent(arg0), "Title isn't present");
    }


    /**
     * Method is check to page logo visibility
     */
    public void verifyPageLogoIsVisible() {
        wc.assertTrue(wc.isElementPresent(SITE_LOGO), "LOGO isn't present");
        wc.assertTrue(wc.isElementPresent(HOME_BUTTON), "Home button isn't present");
        wc.clickObjectBy(HOME_BUTTON, "Home button is clickable");
        wc.assertTrue(wc.isElementPresent(SITE_LOGO), "LOGO isn't present");
    }

    /**
     * @param arg0 Method is control to URL When I click home button
     */
    public void iClickTheHomeButtonGetNavigatedToTheHomePageUrl(String arg0) {
        wc.assertTrue(wc.isElementPresent(HOME_BUTTON), "Home button isn't present");
        wc.clickObjectBy(HOME_BUTTON, "Home button is clickable");
        wc.assertEquals(wc.getCurrentUrl(), arg0, "url not ok");
    }

    /**
     * Method is control to home button status
     */
    public void iClickOnTheHomeButtonItIsActiveStatus() {
        wc.assertTrue(wc.isElementPresent(HOME_BUTTON), "Home button isn't present");
        wc.clickObjectBy(HOME_BUTTON, "Home button is clickable");
        wc.activeButtonControl(HOME_BUTTON, "Home button is active");
    }

    /**
     * @param arg0 Method is control to URL When I click form button
     */
    public void iClickTheFormButtonGetNavigatedToTheFormPageUrl(String arg0) {
        wc.assertTrue(wc.isElementPresent(FORM_BUTTON), "Form button isn't present");
        wc.clickObjectBy(FORM_BUTTON, "Form button is clickable");
        wc.assertEquals(wc.getCurrentUrl(), arg0, "url not ok");
    }

    /**
     * Method is control to form button status
     */
    public void iClickOnTheFormButtonItIsActiveStatus() {
        wc.assertTrue(wc.isElementPresent(FORM_BUTTON), "Form button isn't present");
        wc.clickObjectBy(FORM_BUTTON, "FORM_BUTTON");
        wc.activeButtonControl(FORM_BUTTON, "active");
    }

    /**
     * @param arg0 Method is control to HTTP Response code
     */
    public void iClickOnTheErrorButtonItTakeAHTTPResponseCode(int arg0) {
        wc.assertTrue(wc.isElementPresent(ERROR_BUTTON), "ERROR_BUTTON");
        wc.clickObjectBy(ERROR_BUTTON, "Form button is clickable");
        int responseCode = wc.responseCodeCheck();
        Assert.assertEquals(responseCode, arg0, responseCode + " HTTP response code");
    }

    /**
     * @param arg0 Method is control to URL When I click UI Testing button
     */
    public void iClickOnTheUITestingButtonIShouldGetNavigatedToHomePageUrl(String arg0) {
        driver.get("http://uitest.duodecadits.com");
        wc.assertTrue(wc.isElementPresent(UI_TESTING_BUTTON), "UI TESTING button isn't present");
        wc.clickObjectBy(UI_TESTING_BUTTON, "UI_TESTING button is clickable");
        wc.assertEquals(wc.getCurrentUrl(), arg0, "url not ok");

    }

    /**
     * @param arg0 Method is check to visible on the home page in text
     */
    public void theFollowingTextShouldBeVisibleOnTheHomePageIn(String arg0) {
        wc.assertEquals(wc.getTextElement(TEXT_H1), arg0, "text not equal");
    }

    /**
     * @param arg0 Method is check to visible on the home page in text
     */
    public void theFollowingTextShouldBeVisibleOnTheHomePageInPTag(String arg0) {
        wc.assertEquals(wc.getTextElement(TEXT_P), arg0, "text not equal");
    }

    /**
     * @param arg0 Method is check to input text area on form page
     * @param arg1 Method is check to submit button on form page
     */
    public void onTheFormPageAFormShouldBeVisibleWithOneAndOne(String arg0, String arg1) {
        wc.clickObjectBy(FORM_BUTTON, "Form button is clickable");
        wc.assertTrue(wc.isElementPresent(INPUT), "Input text area isn't present");
        wc.assertTrue(wc.isElementPresent(SUBMIT), "Submit button isn't present");
    }


    /**
     * @param arg0 Method is navigate to given URL
     */
    public void openTheFormPage(String arg0) {
        driver.get(arg0);
        wc.waitForCondition("PageLoad", "", 60);
    }

    /**
     * @param arg0 Method is write to  value
     * @param arg1 Method is verify to result
     */
    public void enterTheValueAndResult(String arg0, String arg1) {
        wc.assertTrue(wc.isElementPresent(INPUT), "Input text area isn't present");
        wc.sendKeysBy(INPUT, arg0);
        wc.assertTrue(wc.isElementPresent(SUBMIT), "Submit button isn't present");
        wc.clickObjectBy(SUBMIT, "Submit button is clickable");
        wc.assertEquals(wc.getTextElement(HELLO_TEXT), arg1, "text not ok");
    }
}