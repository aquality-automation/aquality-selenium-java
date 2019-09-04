package aquality.selenium.elements;

import aquality.selenium.elements.interfaces.IElementFactory;
import aquality.selenium.elements.interfaces.IElementFinder;
import com.google.inject.AbstractModule;

public class ElementsModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IElementFinder.class).to(ElementFinder.class);
        bind(IElementFactory.class).to(ElementFactory.class);
    }
}
