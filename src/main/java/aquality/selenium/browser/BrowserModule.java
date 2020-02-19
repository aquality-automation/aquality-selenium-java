package aquality.selenium.browser;

import aquality.selenium.configuration.*;
import aquality.selenium.core.applications.AqualityModule;
import aquality.selenium.elements.IElementsModule;
import aquality.selenium.elements.interfaces.IElementFactory;
import com.google.inject.Provider;
import com.google.inject.Singleton;

public class BrowserModule extends AqualityModule<Browser> implements IConfigurationsModule, IElementsModule {

    public BrowserModule(Provider<Browser> applicationProvider) {
        super(applicationProvider);
    }

    @Override
    protected void configure() {
        super.configure();
        bind(ITimeoutConfiguration.class).to(getTimeoutConfigurationImplementation()).in(Singleton.class);
        bind(IBrowserProfile.class).to(getBrowserProfileImplementation()).in(Singleton.class);
        bind(IElementFactory.class).to(getElementFactoryImplementation());
    }
}
