package aquality.selenium.configuration;

import aquality.selenium.core.configurations.IElementCacheConfiguration;
import aquality.selenium.core.configurations.ILoggerConfiguration;
import aquality.selenium.core.configurations.IRetryConfiguration;
import aquality.selenium.core.configurations.IVisualizationConfiguration;
import com.google.inject.Inject;

public class Configuration implements IConfiguration {

    private final ITimeoutConfiguration timeoutConfiguration;
    private final IRetryConfiguration retryConfiguration;
    private final IBrowserProfile browserProfile;
    private final ILoggerConfiguration loggerConfiguration;
    private final IElementCacheConfiguration elementCacheConfiguration;
    private final IVisualizationConfiguration visualizationConfiguration;

    @Inject
    public Configuration(ITimeoutConfiguration timeoutConfiguration, IRetryConfiguration retryConfiguration,
                         IBrowserProfile browserProfile, ILoggerConfiguration loggerConfiguration,
                         IElementCacheConfiguration elementCacheConfiguration, IVisualizationConfiguration visualizationConfiguration) {
        this.timeoutConfiguration = timeoutConfiguration;
        this.retryConfiguration = retryConfiguration;
        this.browserProfile = browserProfile;
        this.loggerConfiguration = loggerConfiguration;
        this.elementCacheConfiguration = elementCacheConfiguration;
        this.visualizationConfiguration = visualizationConfiguration;
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

    @Override
    public IElementCacheConfiguration getElementCacheConfiguration() {
        return elementCacheConfiguration;
    }

    @Override
    public IVisualizationConfiguration getVisualizationConfiguration() {
        return visualizationConfiguration;
    }
}
