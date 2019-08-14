package aquality.selenium.browser;

import aquality.selenium.configuration.driversettings.IDriverSettings;
import aquality.selenium.configuration.ITimeoutConfiguration;
import aquality.selenium.localization.LocalizationManager;
import aquality.selenium.logger.Logger;
import aquality.selenium.waitings.ConditionalWait;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

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

    public void quit() {
        logger.info(getLocManager().getValue("loc.browser.driver.quit"));
        if (getDriver() != null) {
            getDriver().quit();
        }
    }

    public RemoteWebDriver getDriver() {
        return webDriver;
    }

    public Navigation navigate(){
        return new BrowserNavigation(getDriver());
    }

    public void goBack() {
        navigate().back();
    }

    public void goForward() {
        navigate().forward();
    }

    public void maximize() {
        logger.info(getLocManager().getValue("loc.browser.maximize"));
        getDriver().manage().window().maximize();
    }

    public String getCurrentUrl() {
        logger.info(getLocManager().getValue("loc.browser.getUrl"));
        return getDriver().getCurrentUrl();
    }

    public void refresh() {
        navigate().refresh();
    }

    public void refreshPageWithAlert(AlertActions alertAction) {
        refresh();
        handleAlert(alertAction);
    }

    public void goTo(String url) {
        navigate().to(url);
    }

    /**
     * Set Page Load timeout (Will be ignored for Safari https://github.com/SeleniumHQ/selenium-google-code-issue-archive/issues/687)
     * @param timeout seconds to wait
     */
    public void setPageLoadTimeout(Long timeout) {
        if(!getBrowserName().equals(BrowserName.SAFARI)){
            getDriver().manage().timeouts().pageLoadTimeout(timeout, TimeUnit.SECONDS);
        }
    }

    public void setImplicitWaitTimeout(Long timeout) {
        if(!timeout.equals(getImplicitWaitTimeout())){
            getDriver().manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
            timeoutImpl = timeout;
        }
    }

    public void setScriptTimeout(Long timeout) {
        getDriver().manage().timeouts().setScriptTimeout(timeout, TimeUnit.SECONDS);
    }

    public void waitForPageToLoad() {
        ExpectedCondition<Boolean> condition = d -> {
            Object result = executeScript(JavaScript.IS_PAGE_LOADED.getScript());
            return result instanceof Boolean && (Boolean) result;
        };
        boolean isLoaded = ConditionalWait.waitFor(condition, timeouts.getImplicit());
        if (!isLoaded) {
            logger.warn(getLocManager().getValue("loc.browser.page.timeout"));
        }
    }

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
        AtomicBoolean isBooleanResult = new AtomicBoolean(false);
        Object scriptResult = ConditionalWait.waitFor(driver ->
                {
                    JavascriptExecutor executor = ((JavascriptExecutor) driver);
                    Object result = executor != null ? executor.executeScript(script, arguments) : null;
                    if(result != null && Boolean.class == result.getClass()){
                        isBooleanResult.set(true);
                        return result.toString();
                    }else {
                        return result == null ? new Object() : result;
                    }
                }
        );

        return isBooleanResult.get() ? Boolean.valueOf(scriptResult.toString()) : scriptResult;
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

    public void scrollWindowBy(int x, int y) {
        executeScript(JavaScript.SCROLL_WINDOW_BY.getScript(), x, y);
    }

    public void setWindowSize(int width, int height){
        getDriver().manage().window().setSize(new Dimension(width, height));
    }

    private Long getImplicitWaitTimeout() {
        return timeoutImpl;
    }

    public String getDownloadDirectory() {
        return driverSettings.getDownloadDir();
    }

    public final BrowserName getBrowserName() {
        return browserName;
    }

    private LocalizationManager getLocManager(){
        return LocalizationManager.getInstance();
    }
}

