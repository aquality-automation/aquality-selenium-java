package aquality.selenium.browser;

import aquality.selenium.configuration.IConfiguration;
import aquality.selenium.configuration.driversettings.IDriverSettings;
import aquality.selenium.localization.LocalizationManager;
import aquality.selenium.logger.Logger;
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


public class RemoteBrowserFactory extends BrowserFactory {

    private final IConfiguration configuration;

    public RemoteBrowserFactory(IConfiguration configuration){
        this.configuration = configuration;
    }

    @Override
    public Browser getBrowser(){
        BrowserName browserName = getBrowserName();
        IDriverSettings driverSettings = configuration.getBrowserProfile().getDriverSettings();
        logBrowserIsReady(browserName);
        RemoteWebDriver driver = createRemoteDriver(driverSettings.getCapabilities());
        return new Browser(driver, configuration);
    }

    private BrowserName getBrowserName() {
        return configuration.getBrowserProfile().getBrowserName();
    }

    private RemoteWebDriver createRemoteDriver(Capabilities capabilities){
        Logger logger = Logger.getInstance();
        logger.info(LocalizationManager.getInstance().getValue("loc.browser.grid"));

        ClientFactory clientFactory = new ClientFactory();
        CommandExecutor commandExecutor = new HttpCommandExecutor(
                ImmutableMap.of(),
                configuration.getBrowserProfile().getRemoteConnectionUrl(),
                clientFactory);

        RemoteWebDriver driver = new RemoteWebDriver(commandExecutor, capabilities);

        driver.setFileDetector(new LocalFileDetector());
        return driver;
    }

    class ClientFactory implements Factory{
        private final Factory defaultClientFactory = Factory.createDefault();
        private final Duration timeoutCommand = configuration.getTimeoutConfiguration().getCommand();

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
