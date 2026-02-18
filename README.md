[![Build Status](https://dev.azure.com/aquality-automation/aquality-automation/_apis/build/status/aquality-automation.aquality-selenium-java?branchName=master)](https://dev.azure.com/aquality-automation/aquality-automation/_build/latest?definitionId=2&branchName=master)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=aquality-automation_aquality-selenium-java&metric=alert_status)](https://sonarcloud.io/dashboard?id=aquality-automation_aquality-selenium-java)
[![Sonatype Central](https://maven-badges.sml.io/sonatype-central/com.github.aquality-automation/aquality-selenium/badge.svg)](https://maven-badges.sml.io/sonatype-central/com.github.aquality-automation/aquality-selenium)

### Overview

This package is a library designed to simplify your work with Selenium WebDriver.

You've got to use this set of methods, related to most common actions performed with web elements.

Most of performed methods are logged using LOG4J, so you can easily see a history of performed actions in your log.

We use interfaces where is possible, so you can implement your own version of target interface with no need to rewrite other classes.


> ### Breaking news! 
> Starting from v4.4.0 onwards, this package requires Java 11 or higher, as Selenium has stopped support of Java 8.
> The last version available for Java 8 is [v4.3.3](https://github.com/aquality-automation/aquality-selenium-java/releases/tag/v4.3.3)

### Quick start
To start the project using aquality.selenium framework, you can [download our template BDD project by this link.](https://github.com/aquality-automation/aquality-selenium-java-template)

Alternatively, you can follow the steps below:

1. Add the dependency to your pom.xml:
```
<dependency>
    <groupId>com.github.aquality-automation</groupId>
    <artifactId>aquality-selenium</artifactId>
    <version>4.x.x</version>
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

6. Use BiDi functionality to handle basic authentication:
```java
browser.network().addBasicAuthentication("domain.com", "username", "password");
```
or intercept network requests/responses:
```java
browser.network().startNetworkInterceptor((HttpHandler) request -> new HttpResponse()
        .setStatus(HttpStatus.SC_OK)
        .addHeader("Content-Type", MediaType.HTML_UTF_8.toString())
        .setContent(utf8String("Some phrase")));
```
7. Emulate GeoLocation, Device, Touch, Media, UserAgent overrides, Disable script execution, log HTTP exchange, track Performance metrics, add initialization scripts, and more using browser.devTools() interfaces:
```java
final double latitude = 53.90772672521578;
final double longitude = 27.458060411865375;
final double accuracy = 0.97;
browser.devTools().emulation().setGeolocationOverride(latitude, longitude, accuracy);
```
See more DevTools use cases [here](./src/test/java/tests/usecases/devtools)

It is also possible to set mobile emulation capabilities (for chromium-based browsers) in resources/settings.json file, as well as to configure other arguments and options there:
```json
{
  "browserName": "chrome",
  "isRemote": false,
  "remoteConnectionUrl": "http://qa-auto-nexus:4444/wd/hub",
  "isElementHighlightEnabled": true,

  "driverSettings": {
    "chrome": {
      "capabilities": {
        "selenoid:options": 
        { 
            "enableVNC": true 
        },
        "mobileEmulation": {
          "userAgent": "Mozilla/5.0 (Linux; Android 4.2.1; en-us; Nexus 5 Build/JOP40D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19",
          "deviceMetrics": {
            "width": 660,
            "height": 1040,
            "pixelRatio": 3.0
          },
          "clientHints": {
            "platform": "Android",
            "mobile": true
          }
        },
        "unhandledPromptBehavior": "ignore"
      },
      "options": {
        "download.prompt_for_download": "false",
        "download.default_directory": "./downloads"
      },
      "loggingPreferences": {
          "Performance": "All"
      },
      "excludedArguments": [ "enable-automation" ],
      "startArguments": [ "--disable-search-engine-choice-screen" ],
      "pageLoadStrategy": "normal"
    },
    "safari": {
      "options": {
        "safari.options.dataDir": "/Users/username/Downloads"
      }
    }
  },
  "timeouts": {
    "timeoutImplicit": 0,
    "timeoutCondition": 30,
    "timeoutScript": 10,
    "timeoutPageLoad": 60,
    "timeoutPollingInterval": 300,
    "timeoutCommand": 60
  },
  "retry": {
    "number": 2,
    "pollingInterval": 300
  },
  "logger": {
    "language": "en",
    "logPageSource": true
  },
  "elementCache": {
    "isEnabled": false
  },
  "visualization": {
    "imageExtension": "png",
    "maxFullFileNameLength": 255,
    "defaultThreshold": 0.012,
    "comparisonWidth": 16,
    "comparisonHeight": 16,
    "pathToDumps": "./src/test/resources/visualDumps/"
  }
}
```


8. Quit browser at the end
```java
browser.quit();
```

See quick start example [here](./src/test/java/tests/usecases/QuickStartExample.java)

### Documentation
To get more details please look at documentation:
- [In English](https://github.com/aquality-automation/aquality-selenium-java/wiki/Overview-(English))
- [In Russian](https://github.com/aquality-automation/aquality-selenium-java/wiki/Overview-(Russian))

### License
Library's source code is made available under the [Apache 2.0 license](LICENSE).
