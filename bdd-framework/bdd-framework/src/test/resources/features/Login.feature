@login
Feature: Login
  As a Sauce Demo customer
  I want to log in with my credentials
  So that I can shop for products

  Background:
    Given the user is on the login page

  @smoke @positive
  Scenario: Successful login with valid credentials
    When the user logs in with username "standard_user" and password "secret_sauce"
    Then the user should land on the "Products" page

  @negative
  Scenario Outline: Login fails with invalid credentials
    When the user logs in with username "<username>" and password "<password>"
    Then an error message containing "<error>" should be displayed

    Examples:
      | username        | password     | error                                     |
      | locked_out_user | secret_sauce | Sorry, this user has been locked out      |
      | standard_user   | wrong_pass   | Username and password do not match        |
      |                 | secret_sauce | Username is required                      |
      | standard_user   |              | Password is required                      |
