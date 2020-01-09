package tests.usecases;

import aquality.selenium.browser.Browser;
import aquality.selenium.browser.BrowserManager;
import aquality.selenium.browser.BrowserName;
import aquality.selenium.browser.IBrowserFactory;
import aquality.selenium.configuration.*;
import aquality.selenium.configuration.driversettings.ChromeSettings;
import aquality.selenium.configuration.driversettings.FirefoxSettings;
import aquality.selenium.configuration.driversettings.IDriverSettings;
import aquality.selenium.utils.JsonFile;
import aquality.selenium.waitings.ConditionalWait;
import com.google.common.util.concurrent.UncheckedExecutionException;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import theinternet.TheInternetPage;
import theinternet.forms.FileDownloaderForm;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Stream;

public class BrowserFactoryTests {

    private final String downloadDirFactoryInitialized = "./target/downloads_custom/";
    private final JsonFile jsonProfile;

    public BrowserFactoryTests() {
        String settingsProfile = System.getProperty("profile") == null ? "settings.json" : "settings." + System.getProperty("profile") + ".json";
        this.jsonProfile = new JsonFile(settingsProfile);
    }

    @Test
    public void testShouldBePossibleToSetBrowser() {

        IBrowserFactory webDriverFactory = () -> {
            FirefoxSettings firefoxSettings = new FirefoxSettings(jsonProfile);
            WebDriverManager.firefoxdriver().setup();
            FirefoxDriver driver = new FirefoxDriver(firefoxSettings.getCapabilities());
            return new Browser(driver, new CustomConfiguration(BrowserName.FIREFOX, firefoxSettings, Configuration.getInstance().getTimeoutConfiguration()));
        };

        BrowserManager.setBrowser(webDriverFactory.getBrowser());
        BrowserManager.getBrowser().goTo(TheInternetPage.LOGIN.getAddress());
        Assert.assertEquals(BrowserManager.getBrowser().getDriver().getCapabilities().getBrowserName(), "firefox");
    }

    @Test
    public void testShouldBePossibleToSetFactory() {
        IBrowserFactory webDriverFactory = new ACustomLocalFactory();
        BrowserManager.setFactory(webDriverFactory);
        Assert.assertEquals(BrowserManager.getBrowser().getDownloadDirectory(), new CustomChromeSettings(jsonProfile).getDownloadDir());
    }

    @Test
    public void testShouldBePossibleToOverrideDownloadDirectory() throws IOException {
        IBrowserFactory webDriverFactory = new ACustomLocalFactory();
        BrowserManager.setFactory(webDriverFactory);

        String downloadDir = BrowserManager.getBrowser().getDownloadDirectory();
        if (new File(downloadDirFactoryInitialized).exists()){
            try (Stream<Path> walk = Files.walk(Paths.get(downloadDir))) {
                walk.sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            }
        }

        String fileName = new FileDownloaderForm().getFileName();
        String urlXlsSample = TheInternetPage.DOWNLOAD.getAddress() + "/" + fileName;

        BrowserManager.getBrowser().goTo(urlXlsSample);
        File fileDownloaded = new File(downloadDirFactoryInitialized + fileName);
        boolean isFileDownloaded = ConditionalWait.waitFor(driver -> fileDownloaded.exists(), 120, 300, "File should be downloaded");
        Assert.assertTrue(isFileDownloaded, "Downloaded file exists");
    }

    private class ACustomLocalFactory implements IBrowserFactory {

        @Override
        public Browser getBrowser() {
            WebDriverManager.chromedriver().setup();
            CustomChromeSettings chromeSettings = new CustomChromeSettings(jsonProfile);
            ChromeDriver driver = new ChromeDriver(chromeSettings.getCapabilities());
            return new Browser(driver, new CustomConfiguration(BrowserName.CHROME, chromeSettings, new ITimeoutConfiguration() {
                @Override
                public long getImplicit() {
                    return 0;
                }

                @Override
                public long getCondition() {
                    return 30;
                }

                @Override
                public long getScript() {
                    return 10;
                }

                @Override
                public long getPageLoad() {
                    return 10;
                }

                @Override
                public long getPollingInterval() {
                    return 300;
                }

                @Override
                public long getCommand() {
                    return 120;
                }
            }));
        }
    }

    private class CustomChromeSettings extends ChromeSettings {

        CustomChromeSettings(JsonFile jsonFile) {
            super(jsonFile);
        }

        @Override
        public String getDownloadDir() {
            try {
                return new File(downloadDirFactoryInitialized).getCanonicalPath();
            }
            catch (IOException e) {
                throw new InvalidArgumentException(e.getMessage());
            }
        }
    }

    private class CustomConfiguration implements IConfiguration{

        private final IDriverSettings driverSettings;
        private final ITimeoutConfiguration timeoutConfiguration;
        private final BrowserName browserName;

        CustomConfiguration(BrowserName browserName, IDriverSettings driverSettings, ITimeoutConfiguration timeoutConfiguration){
            this.driverSettings = driverSettings;
            this.timeoutConfiguration = timeoutConfiguration;
            this.browserName = browserName;
        }

        @Override
        public IBrowserProfile getBrowserProfile() {
            return new IBrowserProfile() {
                @Override
                public BrowserName getBrowserName() {
                    return browserName;
                }

                @Override
                public boolean isRemote() {
                    return false;
                }

                @Override
                public boolean isElementHighlightEnabled() {
                    return false;
                }

                @Override
                public IDriverSettings getDriverSettings() {
                    return driverSettings;
                }

                @Override
                public URL getRemoteConnectionUrl() {
                    try {
                        return new URL(jsonProfile.getValue("/remoteConnectionUrl").toString());
                    } catch (MalformedURLException e) {
                        throw new UncheckedExecutionException(e);
                    }
                }
            };
        }

        @Override
        public ITimeoutConfiguration getTimeoutConfiguration() {
            return timeoutConfiguration;
        }

        @Override
        public IRetryConfiguration getRetryConfiguration() {
            return null;
        }

        @Override
        public ILoggerConfiguration getLoggerConfiguration() {
            return null;
        }
    }

    @AfterMethod
    public void afterMethod(){
        BrowserManager.getBrowser().quit();
        BrowserManager.setDefaultFactory();
    }
}
