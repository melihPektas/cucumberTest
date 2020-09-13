Feature: Reset functionality on login page of Application


  Scenario Outline: Verification of value of result

    Given Open the form page "http://uitest.duodecadits.com/form.html"

    When Enter the Value "<value>"and Result "<result>"


    Examples:
      | value | result |
      | John    | Hello John!    |
      | Sophia  | Hello Sophia!  |
      | Charlie | Hello Charlie! |
      | Emily   | Hello Emily!   |


