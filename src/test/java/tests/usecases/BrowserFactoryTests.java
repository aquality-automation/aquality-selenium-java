package tests.usecases;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import aquality.selenium.browser.BrowserModule;
import aquality.selenium.browser.IBrowserFactory;
import aquality.selenium.configuration.IBrowserProfile;
import aquality.selenium.configuration.driversettings.FirefoxSettings;
import aquality.selenium.core.utilities.IActionRetrier;
import aquality.selenium.core.utilities.ISettingsFile;
import com.google.inject.Provider;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import theinternet.TheInternetPage;
import theinternet.forms.FileDownloaderForm;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

public class BrowserFactoryTests {

    @Test
    public void testShouldBePossibleToSetBrowser() {
        AqualityServices.setBrowser(getCustomFactory().getBrowser());
        AqualityServices.getBrowser().goTo(TheInternetPage.LOGIN.getAddress());
        Assert.assertEquals(AqualityServices.getBrowser().getDriver().getCapabilities().getBrowserName(), "firefox");
    }

    @Test
    public void testShouldBePossibleToSetFactory() {
        IBrowserFactory customFactory = getCustomFactory();
        AqualityServices.setBrowserFactory(customFactory);
        Assert.assertEquals(customFactory, AqualityServices.getBrowserFactory());
        Assert.assertEquals(AqualityServices.getBrowser().getDriver().getCapabilities().getBrowserName(), "firefox");
    }

    private IBrowserFactory getCustomFactory() {
        return () -> {
            FirefoxSettings firefoxSettings = new FirefoxSettings(AqualityServices.get(ISettingsFile.class));
            WebDriverManager.firefoxdriver().setup();
            FirefoxDriver driver = AqualityServices.get(IActionRetrier.class).doWithRetry(
                    () -> new FirefoxDriver(firefoxSettings.getCapabilities().setHeadless(true)),
                    Arrays.asList(
                            SessionNotCreatedException.class,
                            UnreachableBrowserException.class,
                            WebDriverException.class,
                            TimeoutException.class));

            return new Browser(driver);
        };
    }

    @Test
    public void testShouldBePossibleToOverrideDownloadDirectory() throws IOException {

        String downloadDirInitialized = CustomBrowserProfile.getDownloadDirInitialized();
        AqualityServices.initInjector(new CustomBrowserModule(AqualityServices::getBrowser));

        String downloadDir = AqualityServices.getBrowser().getDownloadDirectory();
        if (new File(downloadDirInitialized).exists()) {
            try (Stream<Path> walk = Files.walk(Paths.get(downloadDir))) {
                walk.sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            }
        }

        String fileName = new FileDownloaderForm().getFileName();
        String urlXlsSample = TheInternetPage.DOWNLOAD.getAddress() + "/" + fileName;

        AqualityServices.getBrowser().goTo(urlXlsSample);
        File fileDownloaded = new File(downloadDirInitialized + fileName);
        boolean isFileDownloaded = AqualityServices.getConditionalWait().waitFor(driver -> fileDownloaded.exists(),
                Duration.ofSeconds(120), Duration.ofMillis(300), "File should be downloaded");
        Assert.assertTrue(isFileDownloaded, "Downloaded file exists");
    }

    private class CustomBrowserModule extends BrowserModule {

        CustomBrowserModule(Provider<Browser> applicationProvider) {
            super(applicationProvider);
        }

        @Override
        public Class<? extends IBrowserProfile> getBrowserProfileImplementation() {
            return CustomBrowserProfile.class;
        }
    }

    @AfterMethod
    public void afterMethod() {
        AqualityServices.getBrowser().quit();
        AqualityServices.setDefaultBrowserFactory();
    }
}
