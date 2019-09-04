package aquality.selenium.configuration;

import aquality.selenium.configuration.guice.ConfigurationModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Configuration implements IConfiguration{

    private static ThreadLocal<Configuration> instance = ThreadLocal.withInitial(Configuration::new);
    private final ITimeoutConfiguration timeoutConfiguration;
    private final IRetryConfiguration retryConfiguration;
    private final IBrowserProfile browserProfile;
    private final ILoggerConfiguration loggerConfiguration;

    private Configuration() {
        Injector injector = Guice.createInjector(new ConfigurationModule());
        timeoutConfiguration = injector.getInstance(ITimeoutConfiguration.class);
        retryConfiguration = injector.getInstance(IRetryConfiguration.class);
        browserProfile = injector.getInstance(IBrowserProfile.class);
        loggerConfiguration = injector.getInstance(ILoggerConfiguration.class);
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