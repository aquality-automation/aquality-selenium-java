package aquality.selenium.browser;

import aquality.selenium.configuration.ITimeoutConfiguration;
import aquality.selenium.configuration.driversettings.IDriverSettings;
import aquality.selenium.localization.LocalizationManager;
import aquality.selenium.logger.Logger;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class Browser {
    private final Logger logger = Logger.getInstance();
    private final RemoteWebDriver webDriver;
    private final ITimeoutConfiguration timeouts;
    private Long timeoutImpl;
    private BrowserName browserName;
    private IDriverSettings driverSettings;


    public Browser(RemoteWebDriver remoteWebDriver, IDriverSettings driverSettings, ITimeoutConfiguration timeouts) {
        webDriver = remoteWebDriver;
        this.driverSettings = driverSettings;
        this.browserName = driverSettings.getBrowserName();
        this.timeouts = timeouts;
        this.timeoutImpl = timeouts.getImplicit();
        getDriver().manage().timeouts().implicitlyWait(timeoutImpl, TimeUnit.SECONDS);
        setPageLoadTimeout(timeouts.getPageLoad());
        setScriptTimeout(timeouts.getScript());
    }

    /**
     * Executes browser quit, closes all windows and dispose session
     */
    public void quit() {
        logger.info(getLocManager().getValue("loc.browser.driver.quit"));
        if (getDriver() != null) {
            getDriver().quit();
        }
    }

    /**
     * Provides Selenium WebDriver instance for current browser session
     * @return web driver instance
     */
    public RemoteWebDriver getDriver() {
        return webDriver;
    }

    /**
     * Executes navigating by passed URL
     * @param url URL where you wish to navigate
     */
    public void goTo(String url) {
        logger.info(String.format(getLocManager().getValue("loc.browser.goTo"), url));
        navigate().to(url);
    }

    /**
     * Executes navigating back
     */
    public void goBack() {
        logger.info(getLocManager().getValue("loc.browser.back"));
        navigate().back();
    }

    /**
     * Executes navigating forward
     */
    public void goForward() {
        logger.info(getLocManager().getValue("loc.browser.forward"));
        navigate().forward();
    }

    /**
     * Executes browser window maximizing
     */
    public void maximize() {
        logger.info(getLocManager().getValue("loc.browser.maximize"));
        getDriver().manage().window().maximize();
    }

    /**
     * Returns current page's URL
     * @return current page's URL
     */
    public String getCurrentUrl() {
        logger.info(getLocManager().getValue("loc.browser.getUrl"));
        return getDriver().getCurrentUrl();
    }

    /**
     * Executes refreshing of current page
     */
    public void refresh() {
        logger.info(getLocManager().getValue("loc.browser.refresh"));
        navigate().refresh();
    }

    /**
     * Refreshes the page and process alert that apears after refreshing
     * @param alertAction accept or decline alert
     */
    public void refreshPageWithAlert(AlertActions alertAction) {
        refresh();
        handleAlert(alertAction);
    }


    private Navigation navigate(){
        return new BrowserNavigation(getDriver());
    }

    /**
     * Sets page load timeout (Will be ignored for Safari https://github.com/SeleniumHQ/selenium-google-code-issue-archive/issues/687)
     * @param timeout seconds to wait
     */
    public void setPageLoadTimeout(Long timeout) {
        logger.debug(String.format(getLocManager().getValue("loc.browser.page.load.timeout"), timeout));
        if(!getBrowserName().equals(BrowserName.SAFARI)){
            getDriver().manage().timeouts().pageLoadTimeout(timeout, TimeUnit.SECONDS);
        }
    }

    /**
     * Sets web driver implicit wait timeout
     * Be careful with using this method. Implicit timeout can affect to duration of driver operations
     * @param timeout seconds to wait
     */
    public void setImplicitWaitTimeout(Long timeout) {
        logger.debug(String.format(getLocManager().getValue("loc.browser.implicit.timeout"), timeout));
        if(!timeout.equals(getImplicitWaitTimeout())){
            getDriver().manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
            timeoutImpl = timeout;
        }
    }

    /**
     * Sets timeout to async javascript executions
     * @param timeout timeout in seconds
     */
    public void setScriptTimeout(Long timeout) {
        logger.debug(String.format(getLocManager().getValue("loc.browser.script.timeout"), timeout));
        getDriver().manage().timeouts().setScriptTimeout(timeout, TimeUnit.SECONDS);
    }


    /**
     * Waits until page is loaded
     * TimeoutException will be thrown if page is not loaded during timeout
     * Default timeout is fetched from settings file.
     * Use setPageLoadTimeout to configure your own timeout from code if it is necessary
     */
    public void waitForPageToLoad() {
        ExpectedCondition<Boolean> condition = d -> {
            Object result = executeScript(JavaScript.IS_PAGE_LOADED.getScript());
            return result instanceof Boolean && (Boolean) result;
        };
        getWebDriverWait(timeouts.getPageLoad()).until(condition);
    }

    /**
     * Makes screenshot of the current page
     * @return screenshot as array of bytes
     */
    public byte[] getScreenshot() {
        return getDriver().getScreenshotAs(OutputType.BYTES);
    }

    /**
     * Execute JS (jQuery) script.
     *
     * @param script    Java Script
     * @param arguments Arguments for the script (web elements, values etc.
     * @return Result object of script execution
     */
    public Object executeScript(final String script, Object... arguments) {
        Object result = getDriver().executeScript(script, arguments);
        return result instanceof Boolean ? Boolean.parseBoolean(result.toString()) : result;
    }

    /**
     * Execute JS (jQuery) script from the resource file.
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
     * Execute JS (jQuery) script from the File.
     *
     * @param file      Java Script file
     * @param arguments Arguments for the script (web elements, values etc.
     * @throws IOException in case of problems with the File
     * @return Result object of script execution
     */
    public Object executeScript(final File file, Object... arguments) throws IOException {
        return executeScript(IOUtils.toString(file.toURI(), StandardCharsets.UTF_8.name()), arguments);
    }

    /**
     * Accepts or declines appeared alert
     * @param alertAction accept or decline
     */
    public void handleAlert(AlertActions alertAction) {
        try {
            Alert alert = getDriver().switchTo().alert();
            if (alertAction.equals(AlertActions.ACCEPT))
                alert.accept();
            else alert.dismiss();
        } catch (NoAlertPresentException exception) {
            logger.fatal(getLocManager().getValue("loc.browser.alert.fail"), exception);
            throw exception;
        }
    }

    /**
     * Accepts or declines prompt with sending message
     * @param alertAction accept or decline
     * @param text message to send
     */
    public void handlePromptAlert(AlertActions alertAction, String text) {
        try {
            Alert alert = getDriver().switchTo().alert();
            getDriver().switchTo().alert().sendKeys(text);
            if (alertAction.equals(AlertActions.ACCEPT))
                alert.accept();
            else alert.dismiss();
        } catch (NoAlertPresentException exception) {
            logger.fatal(getLocManager().getValue("loc.browser.alert.fail"), exception);
            throw exception;
        }
    }

    /**
     * Executes scrolling of the page to given coordinates x and y
     * @param x coordinate x
     * @param y coordinate y
     */
    public void scrollWindowBy(int x, int y) {
        executeScript(JavaScript.SCROLL_WINDOW_BY.getScript(), x, y);
    }

    /**
     * Sets given window size
     * @param width desired window width
     * @param height desired window height
     */
    public void setWindowSize(int width, int height){
        getDriver().manage().window().setSize(new Dimension(width, height));
    }

    /**
     * Returns path to download directory
     * Path is configured during web driver setup by value from settings.json
     * @return path to download directory
     */
    public String getDownloadDirectory() {
        return driverSettings.getDownloadDir();
    }

    /**
     * Returns name of current browser
     * @return name
     */
    public final BrowserName getBrowserName() {
        return browserName;
    }

    private Long getImplicitWaitTimeout() {
        return timeoutImpl;
    }

    private LocalizationManager getLocManager(){
        return LocalizationManager.getInstance();
    }

    private WebDriverWait getWebDriverWait(long timeout){
        return new WebDriverWait(getDriver(), timeout);
    }
}

