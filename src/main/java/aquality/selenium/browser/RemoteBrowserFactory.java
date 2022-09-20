package aquality.selenium.browser;

import aquality.selenium.configuration.IBrowserProfile;
import aquality.selenium.configuration.ITimeoutConfiguration;
import aquality.selenium.core.localization.ILocalizedLogger;
import aquality.selenium.core.utilities.IActionRetrier;
import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.http.ClientConfig;
import org.openqa.selenium.remote.http.HttpClient;
import org.openqa.selenium.remote.http.HttpClient.Factory;

import java.net.URL;
import java.time.Duration;


public class RemoteBrowserFactory extends BrowserFactory {

    private final IBrowserProfile browserProfile;
    private final ITimeoutConfiguration timeoutConfiguration;
    private final ILocalizedLogger localizedLogger;

    public RemoteBrowserFactory(IActionRetrier actionRetrier, IBrowserProfile browserProfile,
                                ITimeoutConfiguration timeoutConfiguration, ILocalizedLogger localizedLogger) {
        super(actionRetrier, browserProfile, localizedLogger);
        this.browserProfile = browserProfile;
        this.timeoutConfiguration = timeoutConfiguration;
        this.localizedLogger = localizedLogger;
    }

    @Override
    protected RemoteWebDriver getDriver() {
        Capabilities capabilities = browserProfile.getDriverSettings().getDriverOptions();
        localizedLogger.info("loc.browser.grid");

        ClientFactory clientFactory = new ClientFactory();
        CommandExecutor commandExecutor = new HttpCommandExecutor(
                ImmutableMap.of(),
                browserProfile.getRemoteConnectionUrl(),
                clientFactory);

        try {
            RemoteWebDriver driver = new RemoteWebDriver(commandExecutor, capabilities);
            driver.setFileDetector(new LocalFileDetector());
            return driver;
        }
        catch (WebDriverException e) {
            localizedLogger.fatal("loc.browser.grid.fail", e);
            throw e;
        }

    }

    class ClientFactory implements Factory {
        private final Factory defaultClientFactory = Factory.createDefault();
        private final Duration timeoutCommand = timeoutConfiguration.getCommand();

        public HttpClient createClient(URL url) {
            return defaultClientFactory.createClient(ClientConfig.defaultConfig().baseUrl(url).readTimeout(timeoutCommand));
        }

        @Override
        public HttpClient createClient(ClientConfig clientConfig) {
            clientConfig.readTimeout(timeoutCommand);
            return defaultClientFactory.createClient(clientConfig);
        }

        @Override
        public void cleanupIdleClients() {
            defaultClientFactory.cleanupIdleClients();
        }
    }
}
