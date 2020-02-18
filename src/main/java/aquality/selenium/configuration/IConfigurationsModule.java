package aquality.selenium.configuration;

/**
 * Describes implementations of configurations to be registered in DI container.
 */
public interface IConfigurationsModule extends aquality.selenium.core.configurations.IConfigurationsModule {

    /**
     * @return class which implements {@link IBrowserProfile}
     */
    default Class<? extends IBrowserProfile> getBrowserProfileImplementation() {
        return BrowserProfile.class;
    }

    /**
     * @return class which implements {@link ITimeoutConfiguration}
     */
    @Override
    default Class<? extends ITimeoutConfiguration> getTimeoutConfigurationImplementation() {
        return TimeoutConfiguration.class;
    }
}
