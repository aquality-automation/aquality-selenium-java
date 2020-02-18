package aquality.selenium.browser;

import aquality.selenium.core.applications.AqualityModule;
import com.google.inject.Provider;

public class BrowserModule extends AqualityModule<Browser> {
    public BrowserModule(Provider<Browser> applicationProvider) {
        super(applicationProvider);
    }
}
