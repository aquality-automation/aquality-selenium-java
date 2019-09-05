package aquality.selenium.configuration;

import aquality.selenium.configuration.guice.ConfigurationModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Configuration implements IConfiguration{

    private static ThreadLocal<Configuration> instance = new ThreadLocal<>();
    private static final ThreadLocal<Injector> injectorContainer = new ThreadLocal<>();
    private final ITimeoutConfiguration timeoutConfiguration;
    private final IRetryConfiguration retryConfiguration;
    private final IBrowserProfile browserProfile;
    private final ILoggerConfiguration loggerConfiguration;

    private Configuration() {
        Injector injector = getInjector();
        timeoutConfiguration = injector.getInstance(ITimeoutConfiguration.class);
        retryConfiguration = injector.getInstance(IRetryConfiguration.class);
        browserProfile = injector.getInstance(IBrowserProfile.class);
        loggerConfiguration = injector.getInstance(ILoggerConfiguration.class);
    }

    public static Configuration getInstance() {
        if(instance.get() == null){
            instance.set(new Configuration());
        }
        return instance.get();
    }

    public static void injectDependencies(Injector injector) {
        if(injectorContainer.get() != null) {
            injectorContainer.remove();
        }
        injectorContainer.set(injector);
        instance.remove();
    }

    private static Injector getInjector() {
        if(injectorContainer.get() == null) {
            injectorContainer.set(Guice.createInjector(new ConfigurationModule()));
        }
        return injectorContainer.get();
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