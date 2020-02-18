package aquality.selenium.configuration;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.configurations.ILoggerConfiguration;
import aquality.selenium.core.configurations.IRetryConfiguration;

public class Configuration implements IConfiguration{

    private static ThreadLocal<Configuration> instance = ThreadLocal.withInitial(Configuration::new);
    private final ITimeoutConfiguration timeoutConfiguration;
    private final IRetryConfiguration retryConfiguration;
    private final IBrowserProfile browserProfile;
    private final ILoggerConfiguration loggerConfiguration;

    private Configuration() {
        timeoutConfiguration = AqualityServices.get(ITimeoutConfiguration.class);
        retryConfiguration = AqualityServices.get(IRetryConfiguration.class);
        browserProfile = AqualityServices.get(IBrowserProfile.class);
        loggerConfiguration = AqualityServices.get(ILoggerConfiguration.class);
    }

    public static Configuration getInstance() {
        return instance.get();
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