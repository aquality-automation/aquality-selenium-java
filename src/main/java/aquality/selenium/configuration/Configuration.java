package aquality.selenium.configuration;

import aquality.selenium.core.configurations.ILoggerConfiguration;
import aquality.selenium.core.configurations.IRetryConfiguration;
import com.google.inject.Inject;

public class Configuration implements IConfiguration {

    private final ITimeoutConfiguration timeoutConfiguration;
    private final IRetryConfiguration retryConfiguration;
    private final IBrowserProfile browserProfile;
    private final ILoggerConfiguration loggerConfiguration;

    @Inject
    public Configuration(ITimeoutConfiguration timeoutConfiguration, IRetryConfiguration retryConfiguration,
                         IBrowserProfile browserProfile, ILoggerConfiguration loggerConfiguration) {
        this.timeoutConfiguration = timeoutConfiguration;
        this.retryConfiguration = retryConfiguration;
        this.browserProfile = browserProfile;
        this.loggerConfiguration = loggerConfiguration;
    }

    @Override
    public IBrowserProfile getBrowserProfile() {
        return browserProfile;
    }

    @Override
    public ITimeoutConfiguration getTimeoutConfiguration() {
        return timeoutConfiguration;
    }

    @Override
    public IRetryConfiguration getRetryConfiguration() {
        return retryConfiguration;
    }

    @Override
    public ILoggerConfiguration getLoggerConfiguration() {
        return loggerConfiguration;
    }
}
