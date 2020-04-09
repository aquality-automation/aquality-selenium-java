[![Build Status](https://dev.azure.com/aquality-automation/aquality-automation/_apis/build/status/aquality-automation.aquality-selenium-java?branchName=master)](https://dev.azure.com/aquality-automation/aquality-automation/_build/latest?definitionId=2&branchName=master)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=aquality-automation_aquality-selenium-java&metric=alert_status)](https://sonarcloud.io/dashboard?id=aquality-automation_aquality-selenium-java)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.aquality-automation/aquality-selenium/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.aquality-automation/aquality-selenium)

### Overview

This package is a library designed to simplify your work with Selenium WebDriver.

You've got to use this set of methods, related to most common actions performed with web elements.

Most of performed methods are logged using LOG4J, so you can easily see a history of performed actions in your log.

We use interfaces where is possible, so you can implement your own version of target interface with no need to rewrite other classes.

### Quick start

1. To start work with this package, simply add the dependency to your pom.xml:
```
<dependency>
    <groupId>com.github.aquality-automation</groupId>
    <artifactId>aquality-selenium</artifactId>
    <version>LATEST</version>
</dependency>
```

2. Create instance of Browser in your test method:
```java
Browser browser = AqualityServices.getBrowser();
```

3. Use Browser's methods directly for general actions, such as navigation, window resize, scrolling and alerts handling:
```java
browser.maximize();
browser.goTo("https://wikipedia.org");
browser.waitForPageToLoad();
```

4. Use ElementFactory class's methods to get an instance of each element.
```java
ITextBox txbSearch = AqualityServices.getElementFactory().getTextBox(By.id("searchInput"), "Search");
```

5. Call element's methods to perform action with element: 
```java
txbSearch.type("Selenium WebDriver");
txbSearch.submit();
browser.waitForPageToLoad();
```

6. Quit browser at the end
```
browser.quit();
```

See full example [here](./src/test/java/tests/usecases/QuickStartExample.java)

### Documentation
To get more details please look at documentation:
- [In English](https://github.com/aquality-automation/aquality-selenium-java/wiki/Overview-(English))
- [In Russian](https://github.com/aquality-automation/aquality-selenium-java/wiki/Overview-(Russian))
