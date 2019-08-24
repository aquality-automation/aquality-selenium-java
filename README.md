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
    <version>1.0</version>
</dependency>
```

2. Create instance of Browser in your test method:
```java
Browser browser = BrowserManager.getBrowser();
```

3. Use Browser's methods directly for general actions, such as navigation, window resize, scrolling and alerts handling:
```java
browser.maximize();
browser.goTo("https://wikipedia.org");
browser.waitForPageToLoad()
```

4. Use ElementFactory class's methods to get an instance of each element.
```java
ITextBox txbEmail = new ElementFactory().getTextBox(By.id("email_create"), "Email");
```

5. Call element's methods to perform action with element: 
```java
txbEmail.type("email@domain.com");
```

6. Quit browser at the end
```
browser.quit();
```

### Documentation
To get more details please look at documentation:
- [In English](./Documentation.en.md)
- [In Russian](./Documentation.ru.md)
