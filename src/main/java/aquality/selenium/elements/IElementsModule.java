package aquality.selenium.elements;

import aquality.selenium.elements.interfaces.IElementFactory;

/**
 * Describes implementations of elements services to be registered in DI container.
 */
public interface IElementsModule extends aquality.selenium.core.elements.IElementsModule {
    /**
     * @return class which implements {@link IElementFactory}
     */
    @Override
    default Class<? extends IElementFactory> getElementFactoryImplementation() {
        return ElementFactory.class;
    }
}
