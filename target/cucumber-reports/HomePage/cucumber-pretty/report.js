$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("file:src/test/resources/features/TestCase.feature");
formatter.feature({
  "name": "UITest Scenarios",
  "description": "",
  "keyword": "Feature"
});
formatter.scenario({
  "name": "UITest",
  "description": "",
  "keyword": "Scenario",
  "tags": [
    {
      "name": "@home_page"
    }
  ]
});
formatter.before({
  "status": "passed"
});
formatter.beforestep({
  "status": "passed"
});
formatter.step({
  "name": "User navigates to url \"http://uitest.duodecadits.com\"",
  "keyword": "Given "
});
formatter.match({
  "location": "HomePageSteps.userNavigatesToUrl(String)"
});
formatter.result({
  "status": "passed"
});
formatter.beforestep({
  "status": "passed"
});
formatter.step({
  "name": "Verify Page Title \"UI Testing Site\"",
  "keyword": "Then "
});
formatter.match({
  "location": "HomePageSteps.verifyPageTitle(String)"
});
formatter.result({
  "status": "passed"
});
formatter.beforestep({
  "status": "passed"
});
formatter.step({
  "name": "Verify Page Logo is visible",
  "keyword": "Then "
});
formatter.match({
  "location": "HomePageSteps.verifyPageLogoIsVisible()"
});
formatter.result({
  "status": "passed"
});
formatter.beforestep({
  "status": "passed"
});
formatter.step({
  "name": "I Click the home button get navigated to the Home page url \"http://uitest.duodecadits.com/\"",
  "keyword": "When "
});
formatter.match({
  "location": "HomePageSteps.iClickTheHomeButtonGetNavigatedToTheHomePageUrl(String)"
});
formatter.result({
  "error_message": "java.lang.AssertionError: url  assert not equal expected [http://uitest.duodecadits.com/] but found [http://uitest.duodecadits.com/form.html]\r\n\tat org.testng.Assert.fail(Assert.java:96)\r\n\tat org.testng.Assert.failNotEquals(Assert.java:776)\r\n\tat org.testng.Assert.assertEqualsImpl(Assert.java:137)\r\n\tat org.testng.Assert.assertEquals(Assert.java:118)\r\n\tat org.testng.Assert.assertEquals(Assert.java:453)\r\n\tat WebConnector.webconnector.assertEquals(webconnector.java:400)\r\n\tat ApplicationPages.Homepage.iClickTheHomeButtonGetNavigatedToTheHomePageUrl(Homepage.java:42)\r\n\tat stepdefs.HomePageSteps.iClickTheHomeButtonGetNavigatedToTheHomePageUrl(HomePageSteps.java:55)\r\n\tat ✽.I Click the home button get navigated to the Home page url \"http://uitest.duodecadits.com/\"(file:src/test/resources/features/TestCase.feature:8)\r\n",
  "status": "failed"
});
formatter.beforestep({
  "status": "skipped"
});
formatter.step({
  "name": "I click on the Home button, it is active status",
  "keyword": "When "
});
formatter.match({
  "location": "HomePageSteps.iClickOnTheHomeButtonItIsActiveStatus()"
});
formatter.result({
  "status": "skipped"
});
formatter.beforestep({
  "status": "skipped"
});
formatter.step({
  "name": "I Click the Form button get navigated to the Form page url \"http://uitest.duodecadits.com/form.html\"",
  "keyword": "When "
});
formatter.match({
  "location": "HomePageSteps.iClickTheFormButtonGetNavigatedToTheFormPageUrl(String)"
});
formatter.result({
  "status": "skipped"
});
formatter.beforestep({
  "status": "skipped"
});
formatter.step({
  "name": "I click on the Form button, it is active status",
  "keyword": "When "
});
formatter.match({
  "location": "HomePageSteps.iClickOnTheFormButtonItIsActiveStatus()"
});
formatter.result({
  "status": "skipped"
});
formatter.beforestep({
  "status": "skipped"
});
formatter.step({
  "name": "I click on the Error button, it take a 404 HTTP response code",
  "keyword": "When "
});
formatter.match({
  "location": "HomePageSteps.iClickOnTheErrorButtonItTakeAHTTPResponseCode(int)"
});
formatter.result({
  "status": "skipped"
});
formatter.beforestep({
  "status": "skipped"
});
formatter.step({
  "name": "I click on the UI Testing button, I should get navigated to Home page url \"http://uitest.duodecadits.com/\"",
  "keyword": "When "
});
formatter.match({
  "location": "HomePageSteps.iClickOnTheUITestingButtonIShouldGetNavigatedToHomePageUrl(String)"
});
formatter.result({
  "status": "skipped"
});
formatter.beforestep({
  "status": "skipped"
});
formatter.step({
  "name": "The following text should be visible on the Home page in \"Welcome to the Docler Holding QA Department\"",
  "keyword": "And "
});
formatter.match({
  "location": "HomePageSteps.theFollowingTextShouldBeVisibleOnTheHomePageIn(String)"
});
formatter.result({
  "status": "skipped"
});
formatter.beforestep({
  "status": "skipped"
});
formatter.step({
  "name": "The following text should be visible on the Home page in \u003cp\u003e tag:\"This site is dedicated to perform some exercises and demonstrate automated web testing.\"",
  "keyword": "And "
});
formatter.match({
  "location": "HomePageSteps.theFollowingTextShouldBeVisibleOnTheHomePageInPTag(String)"
});
formatter.result({
  "status": "skipped"
});
formatter.beforestep({
  "status": "skipped"
});
formatter.step({
  "name": "On the Form page, a form should be visible with one \"input box\" and one \"submit button\"",
  "keyword": "Then "
});
formatter.match({
  "location": "HomePageSteps.onTheFormPageAFormShouldBeVisibleWithOneAndOne(String,String)"
});
formatter.result({
  "status": "skipped"
});
formatter.embedding("image/png", "embedded0.png", null);
formatter.after({
  "status": "passed"
});
});