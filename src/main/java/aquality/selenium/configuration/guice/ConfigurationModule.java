package aquality.selenium.configuration.guice;

import aquality.selenium.configuration.*;
import aquality.selenium.utils.JsonFile;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class ConfigurationModule extends AbstractModule {
    @Override
    protected void configure() {
        bindJsonConstructor(IBrowserProfile.class, BrowserProfile.class);
        bindJsonConstructor(ILoggerConfiguration.class, LoggerConfiguration.class);
        bindJsonConstructor(IRetryConfiguration.class, RetryConfiguration.class);
        bindJsonConstructor(ITimeoutConfiguration.class, TimeoutConfiguration.class);
    }

    private <T, S extends T> void bindJsonConstructor(Class<T> interf, Class<S> implementation) {
        try {
            bind(interf).toConstructor(implementation.getConstructor(JsonFile.class));
        } catch (NoSuchMethodException e) {
            addError(e);
        }
    }

    @Provides
    JsonFile provideSettings() {
        String settingsProfile = System.getProperty("profile") == null ? "settings.json" : "settings." + System.getProperty("profile") + ".json";
        return new JsonFile(settingsProfile);
    }
}
