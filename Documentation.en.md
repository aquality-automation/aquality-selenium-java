# Aquality Selenium

Aquality Selenium is a library built over Selenium WebDriver tool that allows to automate work with web browsers. Selenium WebDriver requires some skill and experience. So, Aquality Selenium suggests simplified and most importantly the safer and more stable way to work with Selenium WebDriver.

The main benefits from using Aquality Selenium:

1. Simple configuration
2. Parallel runs
3. Flexible browser (web driver) construction
4. Stable and predictable interactions with elements
5. Smart conditional waits
6. Pack of JavaScripts for advanced usage

**CONTENT**
 - <a href="#1-platform-support">1. PLATFORM SUPPORT</a>
 - <a href='#2-configurations'>2. CONFIGURATIONS</a>
    - <a href='#21-settings'>2.1. SETTINGS</a>
    - <a href='#22-browser'>2.2. BROWSER</a>
    - <a href='#23-driver-settings'>2.3. DRIVER SETTINGS</a>
    - <a href='#24-timeouts'>2.4. TIMEOUTS</a>
    - <a href='#25-retry-policy'>2.5. RETRY POLICY</a>
    - <a href='#26-logging'>2.6. LOGGING</a>
    - <a href='#27-cloud-usage'>2.7. CLOUD USAGE</a>
    - <a href='#28-actions-highlighting'>2.8. ACTIONS HIGHLIGHTING</a>
    - <a href='#29-access-from-the-code'>2.9. ACCESS FROM THE CODE</a>
 - <a href='#3-browser'>3. BROWSER</a>
    - <a href='#31-parallel-runs'>3.1. PARALLEL RUNS</a>
    - <a href='#32-aquality-services'>3.2. AQUALITY SERVICES</a>
    - <a href='#33-browser-factory'>3.3. BROWSER FACTORY</a>
    - <a href='#34-driver-capabilities'>3.4. DRIVER CAPABILITIES</a>
    - <a href='#35-download-directory'>3.5. DOWNLOAD DIRECTORY</a>
    - <a href='#36-alerts'>3.6. ALERTS</a>
    - <a href='#37-screenshots'>3.7. SCREENSHOTS</a>
 - <a href='#4-elements'>4. ELEMENTS</a>
    - <a href='#41-element-factory'>4.1. ELEMENT FACTORY</a>
    - <a href='#42-custom-elements'>4.2. CUSTOM ELEMENTS</a>
    - <a href='#43-list-of-elements'>4.3. LIST OF ELEMENTS</a>
    - <a href='#44-states-of-elements'>4.4. STATES OF ELEMENTS</a>
 - <a href='#5-forms'>5. FORMS</a>
 - <a href='#6-javascript-execution'>6. JAVASCRIPT EXECUTION</a>
 - <a href='#7-json-file'>7. JSON FILE</a>
 - <a href='#8-conditional-wait'>8. CONDITIONAL WAIT</a>

### 1. PLATFORM SUPPORT
At the moment Aquality Selenium allows to automate web tests for Chrome, Firefox, Safari, IExplorer and Edge. Also you can implement support of new browsers that Selenium supports
(more [here](https://www.seleniumhq.org/about/platforms.jsp)).
Tests can be executed on any operating system with installed JDK of version 8 and higher.

### 2. CONFIGURATIONS

Aquality Selenium provides flexible configuration to run tests by editing [settings.json](./src/main/resources/settings.json)
Most of the settings are clear without further explanation but major points are highlighted below.
There is a possibility to implement your own configuration.

### 2.1. SETTINGS

Solution provides [settings.json](./src/main/resources/settings.json) or it's changed copies for configuration of test run.
Copy file [settings.json](./src/main/resources/settings.json) into directory `/src/main/resources` if you are going to place your code under `/src/main`.
If you use `/src/test` copy settings into `/src/test/resources`.

Users can keep several copies of settings file that are different by some parameters.
Copies should be saved in 'main/resources' or 'test/resources' depends on where you will use Aquality library.
Changed copies can be useful if it is necessary to run tests on different operation systems, virtual machines, browsers and etc.
For example, Aquality dev team uses 2 configurations [settings.json](./src/main/resources/settings.json) and [settings.local.json](./src/test/resources/settings.local.json)
to run tests on CI and on personal machines and internal infrastructure.

To use one of the copies user should set environment variable with name `profile` and value 'local' (local name is an example for file settings.local.json) 
To run with Maven execution command can look like: `clean test -Dprofile=local`.
If user have not passed any env variables the settings by default will be used (settings.json from the resource folder).

Any parameter of [settings.json](./src/main/resources/settings.json) can be overrided by environment variable.
To override user should create environment variable where name is a jsonPath and value - wished value. For example, below `driverSettings.chrome.webDriverVersion` is overrided:
`clean test -DdriverSettings.chrome.webDriverVersion=77.0.3865.10`

Settings file consists from several sections which described in details below.

#### 2.2. BROWSER
`browserName` parameter defines browser to run tests. For example, `browser=chrome` allow to run tests on Google Chrome.

`isRemote` parameter defines whether tests will be excecuted locally or on remote server reached by URL from `remoteConnectionUrl` parameter.

#### 2.3. DRIVER SETTINGS
`driverSettings` section [settings.json](./src/main/resources/settings.json) provides abilities to set capabilities, options and start arguments for web driver.

The full list of allowed options can be found on official sites of Selenium and Browser's producers. For example, for chrome: [run-chromium-with-flags](https://www.chromium.org/developers/how-tos/run-chromium-with-flags)

There are some niceties with using InternetExplorer browser. We tried to describe [here](./README_InternetExplorer.md) most of them to keep all this information in one place.

#### 2.4. TIMEOUTS

[settings.json](./src/main/resources/settings.json) contains section `timeouts`. It includes list of timeout parameters that are used in the Aquality.

All parameters are used to initialise [TimeoutConfiguration](./src/main/java/aquality/selenium/configuration/TimeoutConfiguration.java) class instance. After initialising parameters can be fetched by call `AqualityServices.getConfiguration().getTimeoutConfiguration()`.

Below is a description of timeouts:

- `timeoutImplicit` = 0 - implicit wait of web driver - [Selenium Implicit Wait](https://www.seleniumhq.org/docs/04_webdriver_advanced.jsp#implicit-waits).
- `timeoutCondition` = 15 - default timeout to wait conditions: waiting for elements, waiting for element's state.
- `timeoutScript` = 10 - timeout for execution async JavaScripts with WebDriver method **executeAsyncScript**
- `timeoutPageLoad` = 30 - time to wait until page is load
- `timeoutPollingInterval` = 300 - interval between checks if condition meets to expected
- `timeoutCommand` = 120 - timeout for connection to remote server (applied only for remote runs)

All waits in the Aquality Selenium use Excplicit Wait.
Each time before explicit wait value of implicit wait will be set into 0. And after wait will be restored to the default value.
It is not recommended to use implicit and explicit waits together.

#### 2.5 RETRY POLICY

`retry` section of [settings.json](./src/main/resources/settings.json) is responsible to configure number of attempts of manipulations with element (click, type, focus and etc.)
All the operations can be executed several times if they failed at first attempt.

This logic is implemented in the [ElementActionRetrier](https://github.com/aquality-automation/aquality-selenium-core-java/blob/master/src/main/java/aquality/selenium/core/utilities/ElementActionRetrier.java) class. This class is used for any manipulations with elements.
`number` parameter keeps value of number of attempts. An exception will be thrown if attempts are over.
`pollingInterval` keeps value of interval in **milliseconds** between attempts.
[ElementActionRetrier](https://github.com/aquality-automation/aquality-selenium-core-java/blob/master/src/main/java/aquality/selenium/core/utilities/ElementActionRetrier.java) catches StaleElementReferenceException and InvalidElementStateException by default. 

#### 2.6. LOGGING
Aquality logs operations that executes (interaction with browser, elements of the pages). Example of some log:

2019-07-18 10:14:08 INFO  - Label &#39;First product&#39; :: Moving mouse to element

Aquality provides logging on English and Russian languages

- [en](./src/main/resources/localization/en.json)
- [ru](./src/main/resources/localization/ru.json)

Logging language should be configured as parameter [logger.language](./src/main/resources/settings.json).

#### 2.7. CLOUD USAGE

URL to remote server should be set in parameter `remoteConnectionUrl` and parameter `isRemote` should be **true** to run tests on a remote Selenium Grid server (Selenoid, Zalenium) or cloud platforms like BrowserStack, Saucelabs and etc.
For example, to use BrowserStack `remoteConnectionUrl` can be look like [https://USERNAME:AUTOMATE_KEY@hub-cloud.browserstack.com/wd/hub](https://USERNAME:AUTOMATE_KEY@hub-cloud.browserstack.com/wd/hub).

#### 2.8. ACTIONS HIGHLIGHTING

`isElementHighlightEnabled` parameter is responsible for highlighting elements that web driver interact with.
If parameter has value `true` user will be able to see actions more explicit (red border will be added around web element that driver interact with)

#### 2.9. ACCESS FROM THE CODE

Sometimes you need access to values from settings file from the code. To get it you can use [Configuration](./src/main/java/aquality/selenium/configuration/IConfiguration.java) class instance.
Example of usage:  
```
AqualityServices.getConfiguration().getBrowserProfile().getBrowserName()
```
returns value of parameter "browser".

You can also resolve the needed configuration from the AqualityServices directly:
```java
AqualityServices.get(IRetryConfiguration.class).getNumber(); // returns number of retry attempts for element actions, e.g. clicking.
AqualityServices.getBrowserProfile(); // returns currently used set of browser settings.
```

### **3. BROWSER**

Class Browser is a facade around WebDriver and provides methods to work with Browser window and WebDriver itself (for example: navigate, maximize window and etc.).
Developing of test begins from creating instance of the Browser class. In the next chapters more details about that.

#### 3.1. PARALLEL RUNS

Aquality Selenium built around concept 'one browser instance per thread'.
In most cases tests interact with one instance of Browser and this approach looks enough optimal.

To use several browsers during the same test each browser should be created in independent thread.
Examples of using Aquality Selenium in multi-thread mode are here [BrowserConcurrencyTests.java](./src/test/java/tests/usecases/BrowserConcurrencyTests.java)

Test runners like TestNG, JUnit and etc. start each test in the separate thread from the box. So users do not need to do additional work if they are using such runners.

To use parallel streams that provided by Java from 8th version it is necessary to use AqualityServices to pass correct instance of Browser to stream function. Else parallel stream will create new Browser instances for each thread.
The example of usage parallel streams is here [testShouldBePossibleToUseParallelStreams](./src/test/java/tests/usecases/BrowserConcurrencyTests.java).

#### 3.2. AQUALITY SERVICES
Class [AqualityServices](https://github.com/aquality-automation/aquality-selenium-java/blob/Feature/Reference-aquality.selenium.core/src/main/java/aquality/selenium/browser/AqualityServices.java) provides static methods to access Browser and other utilities included in the library.

Usage of AqualityServices is thread-safe. Inside AqualityServices uses DI container Guice.

You can override any service implementation in the class extended from [BrowserModule](https://github.com/aquality-automation/aquality-selenium-java/blob/Feature/Reference-aquality.selenium.core/src/main/java/aquality/selenium/browser/BrowserModule.java), and then re-initialize AqualityServices in your code using the method ```AqualityServices.initInjector(customModule)```. 
Take a look at example of custom module implementation here: [BrowserFactoryTests](https://github.com/aquality-automation/aquality-selenium-java/blob/Feature/Reference-aquality.selenium.core/src/test/java/tests/usecases/BrowserFactoryTests.java)

#### 3.3. BROWSER FACTORY

There are several ways to create instance of class Browser.
The main approach based on the usage of `AqualityServices` class and it's static methods. Below are options of `AqualityServices` usage.

For most cases users need to get Browser instance based on data from the settings file. To get Browser in this case use following:

```
Browser browser = AqualityServices.getBrowser()
```

The first call of `getBrowser()` method will create instance of Browser with WebDriver inside (browser window will be opened).
The next calls of `getBrowser()` in the same thread will return the same instance.

Implicitly for users `AqualityServices` provides `Browser` through calls to browser factories.
Aquality Selenium implements following factories:

- [LocalBrowserFactory](./src/main/java/aquality/selenium/browser/LocalBrowserFactory.java) - to creating Browser on local machine (parameter `isRemote=false`)
- [RemoteBrowserFactory](./src/main/java/aquality/selenium/browser/RemoteBrowserFactory.java) - to creating Browser on remote server (parameter `isRemote=true`)

Each factory implementation implements interface `IBrowserFactory` with the single method `Browser` `getBrowser()`.
Users are allowed to create their on implementations if necessary.

To use custom factory implementation users should set it into `AqualityServices` before first call of `getBrowser()` method:
```
AqualityServices.setFactory(IBrowserFactory browserFactory)
```
The examples of usages custom factories can be found here [BrowserFactoryTests](./src/test/java/tests/usecases/BrowserFactoryTests.java)

If there are reasons to not to use factories user is able to create Browser instance using constructor and then put it into AqualityServices:
```
AqualityServices.setBrowser(Browser browser)
```
 
Browser class has public constructor with the signature: `Browser(RemoteWebDriver remoteWebDriver)`. Other needed services are resolved from the DI container. For example, user could implement his own implementation of `IConfiguration` - but existing IConfiguration implementation can be used, inherited, used partially(`IDriverSettings`, `ITimeoutConfiguration` and etc.)

#### 3.4. DRIVER CAPABILITIES

During the testing sometimes is needed to set additional options and capabilities to web driver.
During Browser instantiating and especially WebDriver the implementations of `IDriverSettings` interface are used.
List of capabilities and options is defined in the [settings.json](./src/main/resources/settings.json) file if user uses `BrowserFactory` by default.

To use custom capabilities user can follow the examples here [testShouldBePossibleToSetFactory](./src/test/java/tests/usecases/BrowserFactoryTests.java).



#### 3.5. DOWNLOAD DIRECTORY

Often users are needed to download files and then process them.
To get current download directory use `getDownloadDirectory()` of `Browser` instance.

Behind this call directory will be fetched from the [settings.json](./src/main/resources/settings.json).
For example: 

```
{
 "download.default_directory": ".//target//downloads//"
}
```

 Note: key `download.default_directory` is different for different browsers. To learn which keys should be used for browsers take a look at implementations of `IDriverSettings`:

[ChromeSettings.java](./src/main/java/aquality/selenium/configuration/driversettings/ChromeSettings.java)

[FirefoxSettings.java](./src/main/java/aquality/selenium/configuration/driversettings/FirefoxSettings.java)

At this moment Aquality Selenium supports downloading files only for browsers Chrome, Firefox, Safari.
But there are no restrictions to add your own implementation.

#### 3.6. ALERTS

`Browser` class provides methods to work with alerts and prompts dialogs:

```
AqualityServices.getBrowser().handleAlert(AlertActions.ACCEPT);
```

More examples here: [AlertTests.java](./src/test/java/tests/integration/AlertTests.java).

#### 3.7. SCREENSHOTS

To take a screenshot of the page there is the method:
```
AqualityServices.getBrowser().getScreenshot()
```

Example of taking screenshots here: [testShouldBePossibleToMakeScreenshot](./src/test/java/tests/integration/BrowserTests.java)

### **4. ELEMENTS**

When Browser instantiated and navigation to necessary page is done user can begin to interact with elements of the page.

#### 4.1. ELEMENT FACTORY

[ElementFactory](./src/main/java/aquality/selenium/elements/ElementFactory.java) is responsible to provide elements of necessary types.
Below is an example of getting ITextBox element:

```
IElementFactory elementFactory = AqualityServices.getElementFactory();
ITextBox txbUsername = elementFactory.getTextBox(By.id("username"), "Username");
```
`ElementFactory` is able to build elements of any types that implements `IElement` interface.
`ElementFactory` has list of methods that returns default(most useful) implementations of `IElement` (`IButton`, `ITextBox`, `ICheckbox` and etc.).

#### 4.2. CUSTOM ELEMENTS

User has abilities to create his own type of element by implementing interface or using inheritance.
For this goal `ElementFactory` provides method `<T extends IElement> T getCustomElement`. 
Example of creating custom element here [CustomElementTests](./src/test/java/tests/usecases/CustomElementTests.java).

#### 4.3. LIST OF ELEMENTS

To get list of elements `ElementFactory` provides method `findElements`:

```
List<ICheckBox> checkBoxes = elementFactory.findElements(By.xpath(checkboxLocator), ElementType.CHECKBOX);
```

More examples here: [ElementTests.java](./src/test/java/tests/integration/ElementTests.java).


#### 4.4. STATES OF ELEMENTS

For most cases before manipulating with web element user should wait util element is displayed on the page.
And `ElementFactory` provides you displayed elements by default.
But for cases if user wants to manipulate with hidden elements (that there are in the HTML DOM, but are not displayed) there are overrided methods.

```
elementFactory.getLink(By.id("redirect"), "Link", ElementState.EXISTS_IN_ANY_STATE);
```

Often users have to wait for some state of elements or just getting current state to next check.
This functionality is implemented in the class [ElementStateProvider](./src/main/java/aquality/selenium/elements/ElementStateProvider.java)
Access to the state of element user can get through the `state()` method:
```
getTxbInput().state().waitForEnabled();
getTxbInput().state().isDisplayed();
```
More examples here: [ElementStateTests](./src/test/java/tests/integration/ElementStateTests.java).

### **5. FORMS**

The main purpose of the Aquality Selenium is to help in test automation of web applications.
Page Object pattern that is widely used in test automation [Page Objects](https://github.com/SeleniumHQ/selenium/wiki/PageObjects).
To support this popular approach Aquality Selenium provides [Form](./src/main/java/aquality/selenium/forms/Form.java) class that can be used as the base class for page objects of testing solution.
The example of usage below:
```
public class SliderForm extends Form {
    public SliderForm() {
        super(By.id("slider_row"), "Slider");
    }
}
```
Here id = &quot;slider\_row&quot; sets the locator that will be used for verification whether the page displayed or not (method `isFormDisplayed()`).

The simple example of the test with page object here: [ShoppingCartTest.java](./src/test/java/tests/usecases/ShoppingCartTest.java).


### **6. JAVASCRIPT EXECUTION**

To execute an JavaScript on the page users can use methods like:
```
browser.executeScript(final String script, Object... arguments).
```

Also Aquality Selenium provides list of most usefull JavaScripts from the box.
Such scripts are placed here `/src/main/resources/js`.
The examples of usage here: [BrowserTests](./src/test/java/tests/integration/BrowserTests.java).
There are several overrided methods that takes scripts as String, File or JavaScript internal type.

### **7. JSON FILE**

Aquality Selenium uses [JsonSettingsFile](https://github.com/aquality-automation/aquality-selenium-core-java/blob/master/src/main/java/aquality/selenium/core/utilities/JsonSettingsFile.java) class to process the JSON files.
Users can use this class to process JSON files from their own project.
For example, if user wants to keep URL to web site that is automating he can put this URL in some JSON file and read value with mentioned class:
```
ISettingsFile environment = new JsonSettingsFile("settings.json")
String url = environment.getValue("/url").toString();
```
### **8. CONDITIONAL WAIT**

If you need to wait for any condition to be met, you can use the [ConditionalWait](https://github.com/aquality-automation/aquality-selenium-core-java/blob/master/src/main/java/aquality/selenium/core/waitings/ConditionalWait.java) class provided by Aquality Selenium.
```java 
File fileDownloaded = new File(downloadDirInitialized + fileName);
AqualityServices.getConditionalWait().waitForTrue(() -> fileDownloaded.exists(),
                Duration.ofSeconds(120), Duration.ofMillis(300), "File should be downloaded");
```
All class methods wait for the condition to be met, but return values and handle exceptions ​​differently: 
1. ```waitForTrue``` - throws an exception if the condition is not met, returns nothing.
2. ```boolean waitFor``` - returns true if the condition is fulfilled or false otherwise. Method does not throw any exception.
3. ```<T> T waitFor``` - uses the WebDriver's wait, returns a T object or an exception if the condition is not met.
