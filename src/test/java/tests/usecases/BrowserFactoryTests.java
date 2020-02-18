package tests.usecases;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import aquality.selenium.browser.BrowserName;
import aquality.selenium.browser.IBrowserFactory;
import aquality.selenium.configuration.Configuration;
import aquality.selenium.configuration.IBrowserProfile;
import aquality.selenium.configuration.IConfiguration;
import aquality.selenium.configuration.ITimeoutConfiguration;
import aquality.selenium.configuration.driversettings.ChromeSettings;
import aquality.selenium.configuration.driversettings.FirefoxSettings;
import aquality.selenium.configuration.driversettings.IDriverSettings;
import aquality.selenium.core.configurations.ILoggerConfiguration;
import aquality.selenium.core.configurations.IRetryConfiguration;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import aquality.selenium.waitings.ConditionalWait;
import com.google.common.util.concurrent.UncheckedExecutionException;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.InvalidArgumentException;
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
import java.time.Duration;
import java.util.Comparator;
import java.util.stream.Stream;

public class BrowserFactoryTests {

    private final String downloadDirFactoryInitialized = "./target/downloads_custom/";
    private final ISettingsFile settingsFile;

    public BrowserFactoryTests() {
        String settingsProfile = System.getProperty("profile") == null ? "settings.json" : "settings." + System.getProperty("profile") + ".json";
        this.settingsFile = new JsonSettingsFile(settingsProfile);
    }

    @Test
    public void testShouldBePossibleToSetBrowser() {

        IBrowserFactory webDriverFactory = () -> {
            FirefoxSettings firefoxSettings = new FirefoxSettings(settingsFile);
            WebDriverManager.firefoxdriver().setup();
            FirefoxDriver driver = new FirefoxDriver(firefoxSettings.getCapabilities());
            return new Browser(driver, new CustomConfiguration(BrowserName.FIREFOX, firefoxSettings, Configuration.getInstance().getTimeoutConfiguration()));
        };

        AqualityServices.setBrowser(webDriverFactory.getBrowser());
        AqualityServices.getBrowser().goTo(TheInternetPage.LOGIN.getAddress());
        Assert.assertEquals(AqualityServices.getBrowser().getDriver().getCapabilities().getBrowserName(), "firefox");
    }

    @Test
    public void testShouldBePossibleToSetFactory() {
        IBrowserFactory webDriverFactory = new ACustomLocalFactory();
        AqualityServices.setBrowserFactory(webDriverFactory);
        Assert.assertEquals(AqualityServices.getBrowser().getDownloadDirectory(), new CustomChromeSettings(settingsFile).getDownloadDir());
    }

    @Test
    public void testShouldBePossibleToOverrideDownloadDirectory() throws IOException {
        IBrowserFactory webDriverFactory = new ACustomLocalFactory();
        AqualityServices.setBrowserFactory(webDriverFactory);

        String downloadDir = AqualityServices.getBrowser().getDownloadDirectory();
        if (new File(downloadDirFactoryInitialized).exists()){
            try (Stream<Path> walk = Files.walk(Paths.get(downloadDir))) {
                walk.sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            }
        }

        String fileName = new FileDownloaderForm().getFileName();
        String urlXlsSample = TheInternetPage.DOWNLOAD.getAddress() + "/" + fileName;

        AqualityServices.getBrowser().goTo(urlXlsSample);
        File fileDownloaded = new File(downloadDirFactoryInitialized + fileName);
        boolean isFileDownloaded = ConditionalWait.waitFor(driver -> fileDownloaded.exists(), 120, 300, "File should be downloaded");
        Assert.assertTrue(isFileDownloaded, "Downloaded file exists");
    }

    private class ACustomLocalFactory implements IBrowserFactory {

        @Override
        public Browser getBrowser() {
            WebDriverManager.chromedriver().setup();
            CustomChromeSettings chromeSettings = new CustomChromeSettings(settingsFile);
            ChromeDriver driver = new ChromeDriver(chromeSettings.getCapabilities());
            return new Browser(driver, new CustomConfiguration(BrowserName.CHROME, chromeSettings, new ITimeoutConfiguration() {
                @Override
                public Duration getImplicit() {
                    return Duration.ZERO;
                }

                @Override
                public Duration getCondition() {
                    return Duration.ofSeconds(30);
                }

                @Override
                public Duration getScript() {
                    return Duration.ofSeconds(10);
                }

                @Override
                public Duration getPageLoad() {
                    return Duration.ofSeconds(10);
                }

                @Override
                public Duration getPollingInterval() {
                    return Duration.ofMillis(300);
                }

                @Override
                public Duration getCommand() {
                    return Duration.ofSeconds(120);
                }
            }));
        }
    }

    private class CustomChromeSettings extends ChromeSettings {

        CustomChromeSettings(ISettingsFile jsonFile) {
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
                        return new URL(settingsFile.getValue("/remoteConnectionUrl").toString());
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
        AqualityServices.getBrowser().quit();
        AqualityServices.setDefaultBrowserFactory();
    }
}
