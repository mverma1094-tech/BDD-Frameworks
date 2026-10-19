@cart
Feature: Shopping cart and product sorting
  As a logged-in customer
  I want to manage items in my cart and sort products
  So that I can find and buy what I need

  Background:
    Given the user is logged in as a standard user

  @smoke
  Scenario: Add a single product to the cart
    When the user adds "Sauce Labs Backpack" to the cart
    Then the cart badge should show 1
    And the cart should contain "Sauce Labs Backpack"

  Scenario: Add multiple products using a data table
    When the user adds the following products to the cart:
      | Sauce Labs Backpack     |
      | Sauce Labs Bike Light   |
      | Sauce Labs Bolt T-Shirt |
    Then the cart badge should show 3

  Scenario: Remove a product from the cart
    When the user adds "Sauce Labs Backpack" to the cart
    And the user removes "Sauce Labs Backpack" from the cart
    Then the cart badge should show 0

  @regression
  Scenario: Sort products by price low to high
    When the user sorts products by "Price (low to high)"
    Then the product prices should be in ascending order

  @regression
  Scenario: Sort products by name Z to A
    When the user sorts products by "Name (Z to A)"
    Then the product names should be in descending order
