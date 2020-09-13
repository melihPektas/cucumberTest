Feature: UITest Scenarios

  @REQ-UI-1_11
  Scenario: UITest
    Given User navigates to url "http://uitest.duodecadits.com"
    Then  Verify Page Title "UI Testing Site"
    Then  Verify Page Logo is visible
    When  I Click the home button get navigated to the Home page url "http://uitest.duodecadits.com/"
    When  I click on the Home button, it is active status
    When  I Click the Form button get navigated to the Form page url "http://uitest.duodecadits.com/form.html"
    When  I click on the Form button, it is active status
    When  I click on the Error button, it take a 404 HTTP response code
    When  I click on the UI Testing button, I should get navigated to Home page url "http://uitest.duodecadits.com/"
    And  The following text should be visible on the Home page in "Welcome to the Docler Holding QA Department"
    And The following text should be visible on the Home page in <p> tag:"This site is dedicated to perform some exercises and demonstrate automated web testing."
    Then On the Form page, a form should be visible with one "input box" and one "submit button"

  @REQ-UI-12
  Scenario Outline: Verification of value of result
    Given Open the form page "http://uitest.duodecadits.com/form.html"
    When Enter the Value "<value>"and Result "<result>"


    Examples:
      | value | result |
      | John    | Hello John!    |
      | Sophia  | Hello Sophia!  |
      | Charlie | Hello Charlie! |
      | Emily   | Hello Emily!   |






