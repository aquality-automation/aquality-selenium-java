package tests.usecases;

import aquality.selenium.browser.Browser;
import aquality.selenium.browser.BrowserManager;
import aquality.selenium.browser.IBrowserFactory;
import aquality.selenium.configuration.Configuration;
import aquality.selenium.configuration.ITimeoutConfiguration;
import aquality.selenium.configuration.driversettings.ChromeSettings;
import aquality.selenium.configuration.driversettings.FirefoxSettings;
import aquality.selenium.utils.JsonFile;
import aquality.selenium.waitings.ConditionalWait;
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
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;

public class BrowserFactoryTests {

    private final String downloadDirFactoryInitialized = "./target/downloads_custom/";
    private final JsonFile jsonProfile;

    public BrowserFactoryTests() {
        try{
            this.jsonProfile = new JsonFile("settings.json");
        }catch (IOException e){
            throw new UncheckedIOException(e);
        }
    }

    @Test
    public void testShouldBePossibleToUseCustomWebDriverFactory() {

        IBrowserFactory webDriverFactory = () -> {
            FirefoxSettings firefoxSettings = new FirefoxSettings(jsonProfile);
            WebDriverManager.firefoxdriver().setup();
            FirefoxDriver driver = new FirefoxDriver(firefoxSettings.getCapabilities());
            return new Browser(driver, firefoxSettings, Configuration.getInstance().getTimeoutConfiguration());
        };

        BrowserManager.setBrowser(webDriverFactory.getBrowser());
        BrowserManager.getBrowser().goTo(TheInternetPage.LOGIN.getAddress());
        Assert.assertEquals(BrowserManager.getBrowser().getDriver().getCapabilities().getBrowserName(), "firefox");
    }

    @Test
    public void testShouldBePossibleToUseCustomBrowser() {
        IBrowserFactory webDriverFactory = new ACustomLocalFactory();
        BrowserManager.setBrowser(webDriverFactory.getBrowser());
        Assert.assertEquals(BrowserManager.getBrowser().getDownloadDirectory(), new CustomChromeSettings(jsonProfile).getDownloadDir());
    }

    @Test
    public void testShouldBePossibleToUseCustomFactory() {
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
        boolean isFileDownloaded = ConditionalWait.waitFor(driver -> fileDownloaded.exists());

        Assert.assertTrue(isFileDownloaded, "Downloaded file exists");
    }

    class ACustomLocalFactory implements IBrowserFactory {

        @Override
        public Browser getBrowser() {
            WebDriverManager.chromedriver().setup();
            CustomChromeSettings chromeSettings = new CustomChromeSettings(jsonProfile);
            ChromeDriver driver = new ChromeDriver(chromeSettings.getCapabilities());
            return new Browser(driver, chromeSettings, new ITimeoutConfiguration() {
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
            });
        }
    }

    class CustomChromeSettings extends ChromeSettings {

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

    @AfterMethod
    public void afterMethod(){
        BrowserManager.getBrowser().quit();
        BrowserManager.setDefaultFactory();
    }
}
