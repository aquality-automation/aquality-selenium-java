package aquality.selenium.browser;

import aquality.selenium.browser.devtools.DevToolsHandling;
import aquality.selenium.browser.devtools.JavaScriptHandling;
import aquality.selenium.browser.devtools.NetworkHandling;
import aquality.selenium.configuration.IBrowserProfile;
import aquality.selenium.configuration.ITimeoutConfiguration;
import aquality.selenium.core.applications.IApplication;
import aquality.selenium.core.localization.ILocalizationManager;
import aquality.selenium.core.localization.ILocalizedLogger;
import aquality.selenium.core.waitings.IConditionalWait;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.function.Supplier;

public class Browser implements IApplication {

    private final RemoteWebDriver webDriver;
    private final ITimeoutConfiguration timeouts;
    private final IBrowserProfile browserProfile;
    private final IConditionalWait conditionalWait;
    private final ILocalizationManager localizationManager;
    private final ILocalizedLogger localizedLogger;

    private DevToolsHandling devTools;
    private Duration implicitTimeout;

    public Browser(RemoteWebDriver remoteWebDriver) {
        conditionalWait = AqualityServices.getConditionalWait();
        localizationManager = AqualityServices.get(ILocalizationManager.class);
        localizedLogger = AqualityServices.getLocalizedLogger();
        this.browserProfile = AqualityServices.getBrowserProfile();
        this.timeouts = AqualityServices.get(ITimeoutConfiguration.class);
        webDriver = remoteWebDriver;
        this.implicitTimeout = timeouts.getImplicit();
        getDriver().manage().timeouts().implicitlyWait(implicitTimeout);
        setPageLoadTimeout(timeouts.getPageLoad());
        setScriptTimeout(timeouts.getScript());
    }

    /**
     * Executes browser quit, closes all windows and dispose session
     */
    public void quit() {
        localizedLogger.info("loc.browser.driver.quit");
        if (getDriver() != null) {
            getDriver().quit();
        }
    }

    /**
     * Provides Selenium WebDriver instance for current browser session
     *
     * @return web driver instance
     */
    public RemoteWebDriver getDriver() {
        return webDriver;
    }

    @Override
    public boolean isStarted() {
        return webDriver.getSessionId() != null;
    }

    /**
     * Executes navigating by passed URL
     *
     * @param url URL where you wish to navigate
     */
    public void goTo(String url) {
        navigate().to(url);
    }

    /**
     * Executes navigating back
     */
    public void goBack() {
        navigate().back();
    }

    /**
     * Executes navigating forward
     */
    public void goForward() {
        navigate().forward();
    }

    /**
     * Executes browser window maximizing
     */
    public void maximize() {
        localizedLogger.info("loc.browser.maximize");
        getDriver().manage().window().maximize();
    }

    /**
     * Returns current page's URL
     *
     * @return current page's URL
     */
    public String getCurrentUrl() {
        localizedLogger.info("loc.browser.getUrl");
        String value = getDriver().getCurrentUrl();
        localizedLogger.info("loc.browser.url.value", value);
        return value;
    }

    /**
     * Executes refreshing of current page
     */
    public void refresh() {
        navigate().refresh();
    }

    /**
     * Refreshes the page and process alert that apears after refreshing
     *
     * @param alertAction accept or decline alert
     */
    public void refreshPageWithAlert(AlertActions alertAction) {
        refresh();
        handleAlert(alertAction);
    }

    private Navigation navigate() {
        return new BrowserNavigation(getDriver());
    }

    /**
     * Provides interface to manage of browser tabs.
     * @return Instance of IBrowserTabNavigation.
     */
    public IBrowserTabNavigation tabs() {
        return new BrowserTabNavigation(getDriver());
    }

    /**
     * Sets page load timeout (Will be ignored for Safari https://github.com/SeleniumHQ/selenium-google-code-issue-archive/issues/687)
     *
     * @param timeout seconds to wait
     */
    public void setPageLoadTimeout(Duration timeout) {
        if (!getBrowserName().equals(BrowserName.SAFARI)) {
            localizedLogger.debug("loc.browser.page.load.timeout", timeout.getSeconds());
            getDriver().manage().timeouts().pageLoadTimeout(timeout);
        }
    }

    /**
     * Sets web driver implicit wait timeout
     * Be careful with using this method. Implicit timeout can affect to duration of driver operations
     *
     * @param timeout duration of time to wait
     */
    public void setImplicitWaitTimeout(Duration timeout) {
        if (!timeout.equals(getImplicitWaitTimeout())) {
            localizedLogger.debug("loc.browser.implicit.timeout", timeout.getSeconds());
            getDriver().manage().timeouts().implicitlyWait(timeout);
            implicitTimeout = timeout;
        }
    }

    /**
     * Sets timeout to async javascript executions
     *
     * @param timeout timeout in seconds
     */
    public void setScriptTimeout(Duration timeout) {
        localizedLogger.debug("loc.browser.script.timeout", timeout.getSeconds());
        getDriver().manage().timeouts().scriptTimeout(timeout);
    }

    /**
     * Waits until page is loaded
     * TimeoutException will be thrown if page is not loaded during timeout
     * Default timeout is fetched from settings file.
     * Use setPageLoadTimeout to configure your own timeout from code if it is necessary
     */
    public void waitForPageToLoad() {
        localizedLogger.info("loc.browser.page.wait");
        ExpectedCondition<Boolean> condition = d -> {
            Object result = executeScript(JavaScript.IS_PAGE_LOADED.getScript());
            return result instanceof Boolean && (Boolean) result;
        };
        conditionalWait.waitFor(condition,
                timeouts.getPageLoad(),
                timeouts.getPollingInterval(),
                localizationManager.getLocalizedMessage("loc.browser.page.timeout"));
    }

    /**
     * Makes screenshot of the current page
     *
     * @return screenshot as array of bytes
     */
    public byte[] getScreenshot() {
        return getDriver().getScreenshotAs(OutputType.BYTES);
    }

    /**
     * Gets logs from WebDriver.
     * @param logKind Type of logs {@link org.openqa.selenium.logging.LogType}
     * @return Storage of LogEntries.
     */
    public LogEntries getLogs(final String logKind) {
        return getDriver().manage().logs().get(logKind);
    }

    /**
     * Executes JS (jQuery) script asynchronous.
     *
     * @param script    Java Script
     * @param arguments Arguments for the script (web elements, values etc.
     * @return Result object of script execution
     */
    public Object executeAsyncScript(final String script, Object... arguments) {
        return executeJavaScript(() -> getDriver().executeAsyncScript(script, arguments));
    }

    /**
     * Executes JS (jQuery) script from the resource file asynchronous.
     * To see the list of scripts see {@link JavaScript}
     * JS files can be found in ~/resources/js/
     *
     * @param scriptName {@link JavaScript}
     * @param args       List of script arguments. This list is unique for each script.
     * @return Result object of script execution
     */
    public Object executeAsyncScript(JavaScript scriptName, Object... args) {
        return executeAsyncScript(scriptName.getScript(), args);
    }

    /**
     * Executes JS (jQuery) script from the File asynchronous.
     *
     * @param file      Java Script file
     * @param arguments Arguments for the script (web elements, values etc.
     * @return Result object of script execution
     * @throws IOException in case of problems with the File
     */
    public Object executeAsyncScript(final File file, Object... arguments) throws IOException {
        return executeAsyncScript(IOUtils.toString(file.toURI(), StandardCharsets.UTF_8.name()), arguments);
    }

    /**
     * Executes JS (jQuery) script.
     *
     * @param script    Java Script
     * @param arguments Arguments for the script (web elements, values etc.
     * @return Result object of script execution
     */
    public Object executeScript(final String script, Object... arguments) {
        return executeJavaScript(() -> getDriver().executeScript(script, arguments));
    }

    /**
     * Executes JS (jQuery) script.
     *
     * @param script Script pinned with {@link this#javaScriptEngine()}.
     * @param arguments Arguments for the script (web elements, values etc.
     * @return Result object of script execution
     */
    public Object executeScript(final ScriptKey script, Object... arguments) {
        return executeJavaScript(() -> getDriver().executeScript(script, arguments));
    }

    private Object executeJavaScript(Supplier<Object> executeScriptFunc) {
        Object result = executeScriptFunc.get();
        return result instanceof Boolean ? Boolean.parseBoolean(result.toString()) : result;
    }

    /**
     * Executes JS (jQuery) script from the resource file.
     * To see the list of scripts see {@link JavaScript}
     * JS files can be found in ~/resources/js/
     *
     * @param scriptName {@link JavaScript}
     * @param args       List of script arguments. This list is unique for each script.
     * @return Result object of script execution
     */
    public Object executeScript(JavaScript scriptName, Object... args) {
        return executeScript(scriptName.getScript(), args);
    }

    /**
     * Executes JS (jQuery) script from the File.
     *
     * @param file      Java Script file
     * @param arguments Arguments for the script (web elements, values etc.
     * @return Result object of script execution
     * @throws IOException in case of problems with the File
     */
    public Object executeScript(final File file, Object... arguments) throws IOException {
        return executeScript(IOUtils.toString(file.toURI(), StandardCharsets.UTF_8.name()), arguments);
    }

    /**
     * Accepts or declines appeared alert
     *
     * @param alertAction accept or decline
     */
    public void handleAlert(AlertActions alertAction) {
        handlePromptAlert(alertAction, null);
    }

    /**
     * Accepts or declines prompt with sending message
     *
     * @param alertAction accept or decline
     * @param text        message to send
     */
    public void handlePromptAlert(AlertActions alertAction, String text) {
        localizedLogger.info(String.format("loc.browser.alert.%s", alertAction.name().toLowerCase()));
        try {
            Alert alert = getDriver().switchTo().alert();
            if (text != null && !text.isEmpty()) {
                localizedLogger.info("loc.send.text", text);
                getDriver().switchTo().alert().sendKeys(text);
            }
            if (alertAction.equals(AlertActions.ACCEPT)) {
                alert.accept();
            } else {
                alert.dismiss();
            }
        } catch (NoAlertPresentException exception) {
            localizedLogger.fatal("loc.browser.alert.fail", exception);
            throw exception;
        }
    }

    /**
     * Executes scrolling of the page to given coordinates x and y
     *
     * @param x coordinate x
     * @param y coordinate y
     */
    public void scrollWindowBy(int x, int y) {
        executeScript(JavaScript.SCROLL_WINDOW_BY.getScript(), x, y);
    }

    /**
     * Sets given window size
     *
     * @param width  desired window width
     * @param height desired window height
     */
    public void setWindowSize(int width, int height) {
        getDriver().manage().window().setSize(new Dimension(width, height));
    }

    /**
     * Returns path to download directory
     * Path is configured during web driver setup by value from settings.json
     *
     * @return path to download directory
     */
    public String getDownloadDirectory() {
        return browserProfile.getDriverSettings().getDownloadDir();
    }

    /**
     * Returns name of current browser
     *
     * @return name
     */
    public final BrowserName getBrowserName() {
        return browserProfile.getBrowserName();
    }

    private Duration getImplicitWaitTimeout() {
        return implicitTimeout;
    }

    /**
     * Provides interface to handle DevTools for Chromium-based and Firefox drivers.
     * @return an instance of {@link DevToolsHandling}
     */
    public DevToolsHandling devTools() {
        if (devTools != null) {
            return devTools;
        }
        WebDriver driver = getDriver();
        if (!(driver instanceof HasDevTools)) {
            driver = new Augmenter().augment(driver);
        }
        if (driver instanceof HasDevTools) {
            devTools = new DevToolsHandling((HasDevTools) driver);
            return devTools;
        }
        else {
            throw new NotImplementedException("DevTools protocol is not supported for current browser.");
        }
    }

    /**
     * Provides Network Handling functionality
     * @return an instance of {@link NetworkHandling}
     */
    public NetworkHandling network() {
        return devTools().network();
    }

    /**
     * Provides JavaScript Monitoring functionality.
     * @return an instance of {@link JavaScriptHandling}
     */
    public JavaScriptHandling javaScriptEngine() {
        return devTools().javaScript();
    }
}
