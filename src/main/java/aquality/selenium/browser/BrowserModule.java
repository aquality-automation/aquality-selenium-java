package aquality.selenium.browser;

import aquality.selenium.configuration.*;
import aquality.selenium.core.applications.AqualityModule;
import com.google.inject.Provider;
import com.google.inject.Singleton;

public class BrowserModule extends AqualityModule<Browser> implements IConfigurationsModule {

    public BrowserModule(Provider<Browser> applicationProvider) {
        super(applicationProvider);
    }

    @Override
    protected void configure() {
        super.configure();
        bind(ITimeoutConfiguration.class).to(getTimeoutConfigurationImplementation()).in(Singleton.class);
        bind(IBrowserProfile.class).to(getBrowserProfileImplementation()).in(Singleton.class);
    }
}
