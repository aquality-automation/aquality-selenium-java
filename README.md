# Selenium 

## Package description:
This package is a library designed to simplify your work with Selenium WebDriver.<br>
You've got to use this set of methods, related to most common actions performed with web elements. <br>
Most of performed methods are logged using LOG4J, so you can easily see a history of performed actions in your log.<br>
We use interfaces where is possible, so you can implement your own version of target interface with no need to rewrite other classes.<br>
The solution constructed to be used with PageObjects pattern, but it is not necessary requirement. You can read more about PageObjects approach on various sources, e.g. here: http://www.assertselenium.com/automation-design-practices/page-object-pattern/
<br>
To start work with this package, simply add the dependency to your pom.xml:  
```
<dependency>
    <groupId>aquality-automation</groupId>
    <artifactId>aquality.selenium</artifactId>
    <version>0.0.1</version>
</dependency>
```

## How to use:
You can take a look at our integration test "ShoppingCartTest" and related classes as an example of typical usage of the package.<br>
Step-by-step guide:<br>
1) Get instance of Browser:
```java
Browser browser = Browser.getInstance();
```
Use Browser's methods directly for general actions, such as navigation, window resize, scrolling and alerts handling:
```java
  browser.maximize();
  browser.goTo("https://wikipedia.org");
  browser.waitForPageToLoad();
  browser.handleAlert(AlertActions.ACCEPT);
  browser.scrollWindowBy(x: 0, y: 1000);
  browser.goBack();
  browser.refresh();
  browser.executeScript(JavaScript.AUTO_ACCEPT_ALERTS)
  browser.quit();
```
2) Create separate form classes for each page, modal, dialog and popup you're working with.
- Inherit your form classes from Form class to use predefined methods; 
- always add PageInfo annotation to your forms, with unique selenium locator and name to each specific form.
```java
@PageInfo(id = "hplogo", pageName = "Main page")
public class MainPage extends BaseForm {
```
```java
@PageInfo(xpath = "//ul[@id='homefeatured']//div[@class='product-container']", pageName = "Product list")
public class ProductListForm extends BaseForm {
```
3) Add elements you want to interact within the current form as private final fields. Use getElementFactory() methods to get an instance of each element.
```java
    private final ITextBox txbEmail = getElementFactory().getTextBox(By.id("email_create"), "Email Create");
    private final IButton btnProducts = getElementFactory().getButton(By.xpath("//span[@class='ajax_cart_product_txt_s']"), "Products");
    private final ICheckBox chbNews = getElementFactory().getCheckBox(By.id("newsletter"), "newsletter", ElementState.HIDDEN);
```
- As you can see, an instance of ElementFactory is created for each form inherited from Form; you can create your own instance of ElementFactory whether you need it; you can extend ElementFactory class for your purposes, e.g. for your custom elements.

If an element have locator dependent on parameter, you can store locator template as string (See javadocs <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/String.html#format(java.lang.String,%20java.lang.Object...)">String.format(String format, Object... args)</a>) in private constant at form class. 

Do the same if you aim to find multiple elements: 
```java
    private static final String TMP_RB_GENDER = "//input[@name='id_gender'][@value='%1$s']";
    private static final String XPATH_SELECT_DAYS = "//select[@id='days']/option";
```
4) Create methods related to business actions performed on the page, or to get information from the page: 
```java
public void setFirstName(String firstName){
    txbFirstName.type(firstName);
}

public void openCartMenu(){
    btnProducts.getMouseActions().moveMouseToElement();
}

public Integer getNumOfDays(){
    List<ILabel> lblDays = getElementFactory().findElements(By.xpath(XPATH_SELECT_DAYS), ElementType.LABEL, ElementState.HIDDEN, ExpectedCount.MORE_THEN_ZERO);
    return lblDays.size();
}

public boolean isNewsCheckboxChecked(){
    return chbNews.isChecked();
}

```

If element's locator depend on external param, you can create it inside the method:
```java
public void selectGender(Integer genderId){
    IRadioButton rbGender = getElementFactory().getRadioButton(By.xpath(String.format(TMP_RB_GENDER, genderId)), "Gender Id " + genderId);
    rbGender.click();
}
```
5) Finally, create an instance of your form and perform required actions:
```java
  AuthenticationForm authenticationForm = new AuthenticationForm();
  authenticationForm.setEmail(USER_EMAIL);
  authenticationForm.clickCreateAccount();
  YourPersonalInfoForm yourPersonalInfoForm = new YourPersonalInfoForm();
  yourPersonalInfoForm.selectGender(GENDER_ID);
  yourPersonalInfoForm.setFirstName(USER_FIRST_NAME);
```

## Use several browser instances in parallel (multithreading):
In our library instances of some core classes (Browser, BrowserFactoryHolder, Logger, Configuration) are stored in thread-local containers.
You may want to interact with more than one instance of Browser. For this purpose, you will need to create browser instances in separate threads, to make sure that their work does not interrupt each other.<br>
You can take a look at our test class BrowserConcurrencyTests for example:
```java
import aquality.selenium.browser.Browser;
import org.testng.Assert;
import org.testng.annotations.Test;
import theinternet.TheInternetPage;

public class BrowserConcurrencyTests {

    @Test
    public void testBrowserShouldBePossibleToHaveTwoBrowsersForTwoThreads() throws InterruptedException {

        Thread thread01 = new Thread(new BrowserThread(TheInternetPage.CHECKBOXES.getAddress()));
        Thread thread02 = new Thread(new BrowserThread(TheInternetPage.LOGIN.getAddress()));

        thread01.start();
        thread02.start();

        thread01.join();
        thread02.join();
    }

    class BrowserThread implements Runnable
    {
        private final String url;
        BrowserThread(String url){
            this.url = url;
        }
        @Override
        public void run() {
            Browser.getInstance().goTo(url);
            Assert.assertEquals(url, Browser.getInstance().getCurrentUrl());
            Browser.getInstance().quit();
        }
    }
}
```


## Library structure: 
- **aquality.selenium**:
    - **browser**: the package with classes and methods to setup and interract with browser
        - **factory**: the package related to browser setup
          *BrowserFactoryHolder* allows you to setup a browser instance.  <br>
            It is implemented using [WebDriverManager](https://github.com/bonigarcia/webdrivermanager). This manager allows us to get required version of target browser's webdriver.
          - In the package we have an interface *IBrowserFactory* and two default implementations: LocalBrowserFactory and RemoteBrowserFactory*.
          - Static method *BrowserFactoryHolder.getFactory()* returns you one of these implementations basing on current configuration's property browser.remote in config.properties.
            It is used in *Browser* class.
          - If you need to specify a concrete version of WebDriver to be used, you can implement your own implementation of *IBrowserFactory*. Please use static method *BrowserFactoryHolder.setFactory(IBrowserFactory webDriverFactory)* so set usage of your implementation.
        - *Browser* is a wrapper to interact with WebDriver. It contains the most common methods to interact with browser: setup, navigate, resize, scroll, handle alerts etc.
    - **configuration**: package to read configuration from .properties and .json files. Please see <a href='#configuration'>Configuration</a> section for detailed desctiption.
    - **elements**: package with functionality designed to simplify work with webelements
      - **actions** functionality related to javascript and mouse actions performed under webelements
      - **interfaces** common interfaces to easily interact with webelements via specific wrappers: *IButton, ICheckBox, IComboBox, ILabel, ILink, IRadioButton* and *ITextBox*.        
      - *ElementFactory*: class designed to create instances of element wrappers
      You can implement your own type of element by extending *Element* class. If you prefer to create your own interface, it should implement *IElement* interface.      
    - **forms**: contains *Form* class and related *PageInfo* annotation
    - **logger**: contains Logger designed to log performed actions
    - **waitings**: contains ConditionalWait class with functionality for conditional waitings.
- **resources**
    - **js**: javascript functions used within this package 

## Configuration
We store configuration parameters in resource files.<br>
You can find default configuration files inside the library in the resources folder <br>
To change some parameter, you will need to add related resource file into your project and change the required parameter.<br>
Make sure to add it to the same path where it is presented in library.<br>
Alternatively, you can override some class from the ``` package aquality.selenium.configuration;  ```

#### Configuration resources structure
- **resourses**
    - **selenium.configuration**: local and remote browser configuration files: capabilities, options, start arguments and timeout properties
        - **local**
            - <a href='#browserproperties'>browser.properties</a>
            - <a href='#browsercapabilitiesjson'>browserCapabilities.json</a>
        - **remote**
            - <a href='#browserremoteproperties'>browser.remote.properties</a>
            - <a href='#browsercapabilitiesjson'>browserCapabilities.json</a>
        - **<a href='#timeoutproperties'>timeout.properties</a>**
    - **log**: resources related to logging
        - **localization**: messages and templates used for logging in specific language
            - en.properties
            - ru.properties
            - **loglang.properties** logging language. default value is "EN" - this means that en.properties will be used. You can add your own language if needed.
    - **<a href='#configproperties'>config.properties</a>**: general browser settings, such as browser.remote and element.highlight
    - **log4j.properties**: default log4j parameters

#### Configuration files content description

###### config.properties
- browser.remote = false // flag defines if the remote WebDriver instance will be created. Set "true" to use Selenium Grid. If set to "true", configuration resources from "remote" folder will be used, otherwise resources from "local" folder will be used.
- element.highlight = true // do highlight elements during actions or not. "Highlight" is made of red border, which is added to each interacted element using specific javascript. Set "false" if you want to switch off this feature.

###### timeout.properties
- timeout.implicit = 0 // implicit wait timeout. we do not recommend to set non-zero value to it. instead of implicit wait you can take advantage of ConditionalWait:
```java
        String selectedText = comboBox.getSelectedText();
        comboBox.selectByText("Option 2");
        SmartWait.waitForTrue(y -> !selectedText.equals(comboBox.getSelectedText()));
```
- timeout.condition = 30 // timeout for waiting actions
- timeout.script = 10 // timeout for script execution
- timeout.pageload = 15 // page loading timeout
- timeout.poolinterval = 300 // retry interval for waiting actions

###### browser.properties
- browser.name=chrome // name of browser to be used. Acceptable names are defined in BrowserName enum. Currently they are: chrome, edge, firefox, iexplorer, safari.
- browser.opts.file=/src/test/resources/configuration/browser/local/browserOptions.json // path to <a href='#browseroptionsjson'>browserOptions.json</a> file
- browser.caps.file=/src/test/resources/configuration/browser/local/browserCapabilities.json // path to <a href='#browsercapabilitiesjson'>browserCapabilities.json</a> file
- browser.args.file=/src/test/resources/configuration/browser/local/browserStartArguments.json // path to <a href='#browserstartargumentsjson'>browserStartArguments.json</a> file

###### browser.remote.properties
- remote.connection.url=http://remotemachine:4444/wd/hub // URL address to remote connection for RemoteWebDriver
- browser.name=chrome // name of browser to be used. Acceptable names are defined in BrowserName enum. Currently they are: chrome, edge, firefox, iexplorer, safari.
- browser.opts.file=/src/test/resources/configuration/browser/local/browserOptions.json // path to <a href='#browseroptionsjson'>browserOptions.json</a> file
- browser.caps.file=/src/test/resources/configuration/browser/local/browserCapabilities.json // path to <a href='#browsercapabilitiesjson'>browserCapabilities.json</a> file
- browser.args.file=/src/test/resources/configuration/browser/local/browserStartArguments.json // path to <a href='#browserstartargumentsjson'>browserStartArguments.json</a> file

Browser configuration consist of three parts: options, capabilities and start arguments. <br>
Each of this configurations is located in a separate file.<br>
You can see an example of these configuration files below.<br>

###### browserCapabilities.json
You can read more about desired capabilities at SeleniumHQ wiki: https://github.com/SeleniumHQ/selenium/wiki/DesiredCapabilities <br>
Values from this file are set to specific Options object(e.g. ChromeOptions, FirefoxOptions etc.) with command options.setCapability(key, value);<br>
Afterwards this Options object is passed as parameter to WebDriver constructor.
```json
    {
      "chrome": [
        {"enableVNC": true}
      ],
      "firefox": [
        {"enableVNC": true}
      ],
      "iexplorer": [
        {"ignoreProtectedModeSettings": true}
      ]
    }
```

###### browserStartArguments.json
Values from this file are set to specific Options object(e.g. ChromeOptions, FirefoxOptions etc.) with command options.addArguments(arg);<br>
Afterwards this Options object is passed as parameter to WebDriver constructor.
```json
    {
      "chrome": [
        "--headless",
        "--ignore-certificate-errors"
      ],
      "firefox": [
        "-headless"
      ]
    }
```
###### browserOptions.json
Values from this file are set to specific Options object(e.g. ChromeOptions, FirefoxOptions etc.) with command options.setExperimentalOption("prefs", chromePrefs); <br>
Here chromePrefs is a HashMap<String, Object> with key-value pairs from json file. <br>
- Please note that you can use absolute path as well as relative path for **"download.default_directory"** option. <br>
  To set **relative** path, start it with ".", e.g. **.\\target\\downloads\\**. It will build up as concatenation with prefix gained from **user.dir** system property (if it is set) or **project.basedir** system property . <br>
  To set **absolute** path you should use full path value, e.g. **C:\\Users\\Public\\Downloads\\** .

Afterwards this Options object is passed as parameter to WebDriver constructor.
```json
    {
      "chrome": [
        {"intl.accept_languages": "en"},
        {"safebrowsing.enabled": "true"},
      ],
      "firefox": [
        {"intl.accept_languages": "en"},
        {"browser.download.folderList": 2},
      ]
    }
```
