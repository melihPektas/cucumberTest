package stepdefs;

import applicationPages.HomePage;
import io.cucumber.datatable.DataTable;
import webConnector.WebConnector;
import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;

public class HomePageSteps extends WebConnector {
    private HomePage homePage;
    private String scenDesc;

    public HomePageSteps() {
        this.homePage = new HomePage();
    }

    @Before
    public void before(Scenario scenario) {
        this.scenDesc = scenario.getName();
        setUpDriver();
    }

    @After
    public void after(Scenario scenario) {
        closeDriver(scenario);
    }

    @BeforeStep
    public void beforeStep() throws InterruptedException {
        Thread.sleep(2000);
    }

    @Given("User navigates to url {string}")
    public void userNavigatesToUrl(String url) {
        this.homePage.goToHomePage(url);
    }

    @And("Verify Page Title {string}")
    public void verifyPageTitle(String arg0)  {
        this.homePage.verifyPageTitle(arg0);
    }

    @Then("Verify Page Logo is visible")
    public void verifyPageLogoIsVisible() {
        this.homePage.verifyPageLogoIsVisible();
    }

    @When("I Click the home button get navigated to the Home page url {string}")
    public void iClickTheHomeButtonGetNavigatedToTheHomePageUrl(String arg0) {
    this.homePage.iClickTheHomeButtonGetNavigatedToTheHomePageUrl(arg0);
    }

    @When("I click on the Home button, it is active status")
    public void iClickOnTheHomeButtonItIsActiveStatus() {
        this.homePage.iClickOnTheHomeButtonItIsActiveStatus();
    }

    @When("I Click the Form button get navigated to the Form page url {string}")
    public void iClickTheFormButtonGetNavigatedToTheFormPageUrl(String arg0) {
        this.homePage.iClickTheFormButtonGetNavigatedToTheFormPageUrl(arg0);
    }

    @When("I click on the Form button, it is active status")
    public void iClickOnTheFormButtonItIsActiveStatus() {
        this.homePage.iClickOnTheFormButtonItIsActiveStatus();
    }

    @When("I click on the Error button, it take a {int} HTTP response code")
    public void iClickOnTheErrorButtonItTakeAHTTPResponseCode(int arg0) {
        this.homePage.iClickOnTheErrorButtonItTakeAHTTPResponseCode(arg0);
    }

    @When("I click on the UI Testing button, I should get navigated to Home page url {string}")
    public void iClickOnTheUITestingButtonIShouldGetNavigatedToHomePageUrl(String arg0) {
        this.homePage.iClickOnTheUITestingButtonIShouldGetNavigatedToHomePageUrl(arg0);
    }

    @And("The following text should be visible on the Home page in {string}")
    public void theFollowingTextShouldBeVisibleOnTheHomePageIn(String arg0) {
        this.homePage.theFollowingTextShouldBeVisibleOnTheHomePageIn(arg0);
    }

    @And("The following text should be visible on the Home page in <p> tag:{string}")
    public void theFollowingTextShouldBeVisibleOnTheHomePageInPTag(String arg0) {
        this.homePage.theFollowingTextShouldBeVisibleOnTheHomePageInPTag(arg0);
    }

    @Then("On the Form page, a form should be visible with one {string} and one {string}")
    public void onTheFormPageAFormShouldBeVisibleWithOneAndOne(String arg0, String arg1) {
        this.homePage.onTheFormPageAFormShouldBeVisibleWithOneAndOne(arg0,arg1);
    }


    @Given("Open the form page {string}")
    public void openTheFormPage(String arg0) {
        this.homePage.openTheFormPage(arg0);
    }

    @When("Enter the Value {string}and Result {string}")
    public void enterTheValueAndResult(String arg0,String arg1) {
        this.homePage.enterTheValueAndResult(arg0,arg1);
    }
}
