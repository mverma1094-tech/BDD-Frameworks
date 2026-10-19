# BDD Automation Framework

Selenium 4 + Cucumber 7 (Gherkin/BDD) + TestNG + Page Object Model, built with Maven.
Tests run against the public demo shop https://www.saucedemo.com.

## Requirements
- Java 17+
- Maven 3.8+
- Chrome, Firefox, or Edge installed (Selenium Manager downloads the driver for you)

## Project structure
```
bdd-framework/
├── pom.xml                         Dependencies + Surefire config
├── testng.xml                      TestNG suite → runs runners.TestRunner
└── src/test/
    ├── java/
    │   ├── pages/                  Page Object Model (locators + actions)
    │   │   ├── BasePage.java       Shared explicit-wait helpers
    │   │   ├── LoginPage.java
    │   │   ├── InventoryPage.java
    │   │   └── CartPage.java
    │   ├── stepdefinitions/        Glue: Gherkin steps → page object calls
    │   │   ├── LoginSteps.java
    │   │   └── CartSteps.java
    │   ├── hooks/Hooks.java        @Before/@After: browser start/quit, failure screenshots
    │   ├── runners/TestRunner.java Cucumber + TestNG runner (@CucumberOptions)
    │   └── utils/
    │       ├── ConfigReader.java   Reads config.properties (-D overrides)
    │       ├── DriverFactory.java  Chrome / Firefox / Edge, headless option
    │       └── TestContext.java    Shared per-scenario state (PicoContainer DI)
    └── resources/
        ├── config.properties
        └── features/
            ├── Login.feature       Background, Scenario Outline + Examples
            └── Cart.feature        Data tables, tags
```

## How the pieces connect
1. `mvn test` → Surefire runs `testng.xml` → `TestRunner`
2. `TestRunner` finds `.feature` files and matches each Gherkin step to a method in `stepdefinitions`
3. Step methods never touch Selenium directly; they call page objects (`LoginPage`, `InventoryPage`…)
4. PicoContainer gives Hooks and every step class the same `TestContext`, so they share one browser per scenario

## Running tests
```bash
mvn test                                         # everything, Chrome
mvn test -Dbrowser=firefox                       # different browser
mvn test -Dheadless=true                         # no visible window (CI)
mvn test -Dcucumber.filter.tags="@smoke"         # only smoke tests
mvn test -Dcucumber.filter.tags="@login and not @negative"
```

## Reports
- HTML: `target/cucumber-reports/cucumber.html` (failure screenshots embedded)
- JSON: `target/cucumber-reports/cucumber.json`
- TestNG: `target/surefire-reports/index.html`
- Failed scenarios list: `target/rerun.txt`

## Adding a new test
1. Write the scenario in a `.feature` file under `features/`
2. Run it once — Cucumber prints snippet methods for any undefined steps
3. Add locators/actions to a page object in `pages/`
4. Paste the snippets into a step class and call the page object
