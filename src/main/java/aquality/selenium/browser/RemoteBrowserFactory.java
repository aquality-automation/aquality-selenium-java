package aquality.selenium.browser;

import aquality.selenium.configuration.IBrowserProfile;
import aquality.selenium.configuration.ITimeoutConfiguration;
import aquality.selenium.configuration.driversettings.IDriverSettings;
import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.http.HttpClient;
import org.openqa.selenium.remote.http.HttpClient.Builder;
import org.openqa.selenium.remote.http.HttpClient.Factory;

import java.net.URL;
import java.time.Duration;


public class RemoteBrowserFactory implements BrowserFactory {

    private final IBrowserProfile browserProfile;
    private final ITimeoutConfiguration timeoutConfiguration;

    public RemoteBrowserFactory() {
        browserProfile = AqualityServices.getBrowserProfile();
        timeoutConfiguration = AqualityServices.get(ITimeoutConfiguration.class);
    }

    @Override
    public Browser getBrowser() {
        BrowserName browserName = browserProfile.getBrowserName();
        IDriverSettings driverSettings = browserProfile.getDriverSettings();
        logBrowserIsReady(browserName);
        RemoteWebDriver driver = createRemoteDriver(driverSettings.getCapabilities());
        return new Browser(driver);
    }

    private RemoteWebDriver createRemoteDriver(Capabilities capabilities) {
        AqualityServices.getLocalizedLogger().info("loc.browser.grid");

        ClientFactory clientFactory = new ClientFactory();
        CommandExecutor commandExecutor = new HttpCommandExecutor(
                ImmutableMap.of(),
                browserProfile.getRemoteConnectionUrl(),
                clientFactory);

        RemoteWebDriver driver = new RemoteWebDriver(commandExecutor, capabilities);

        driver.setFileDetector(new LocalFileDetector());
        return driver;
    }

    class ClientFactory implements Factory {
        private final Factory defaultClientFactory = Factory.createDefault();
        private final Duration timeoutCommand = timeoutConfiguration.getCommand();

        @Override
        public Builder builder() {
            return defaultClientFactory.builder().readTimeout(timeoutCommand);
        }

        @Override
        public HttpClient createClient(URL url) {
            return this.builder().createClient(url);
        }

        @Override
        public void cleanupIdleClients() {
            defaultClientFactory.cleanupIdleClients();
        }
    }
}
