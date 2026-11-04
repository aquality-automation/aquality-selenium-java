package aquality.selenium.browser;

import aquality.selenium.configuration.IBrowserProfile;
import aquality.selenium.configuration.ITimeoutConfiguration;
import aquality.selenium.core.localization.ILocalizedLogger;
import aquality.selenium.core.utilities.IActionRetrier;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.http.ClientConfig;
import org.openqa.selenium.remote.http.HttpClient;

import java.net.URL;


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
        URL url = browserProfile.getRemoteConnectionUrl();
        localizedLogger.info("loc.browser.grid");
        ClientConfig config = ClientConfig.defaultConfig().baseUrl(url).readTimeout(timeoutConfiguration.getCommand());

        HttpClient.Factory clientFactory = HttpClient.Factory.createDefault();
        CommandExecutor commandExecutor = new HttpCommandExecutor(clientFactory.createClient(config), url);

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
}
