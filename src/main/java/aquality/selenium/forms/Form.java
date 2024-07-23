package aquality.selenium.forms;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.configurations.IVisualizationConfiguration;
import aquality.selenium.core.elements.interfaces.IElementStateProvider;
import aquality.selenium.core.localization.ILocalizedLogger;
import aquality.selenium.elements.interfaces.IElement;
import aquality.selenium.elements.interfaces.IElementFactory;
import aquality.selenium.elements.interfaces.ILabel;
import org.openqa.selenium.By;

import java.awt.*;

/**
 * Defines base class for any UI form.
 */
public abstract class Form extends aquality.selenium.core.forms.Form<IElement> {
    /**
     * Label of form element defined by its locator and name.
     */
    private final ILabel formLabel;
    /**
     * Locator for specified form
     */
    private final By locator;
    /**
     * Name of specified form
     */
    private final String name;

    /**
     * Constructor with parameters
     */
    protected Form(By locator, String name) {
        super(IElement.class);
        this.locator = locator;
        this.name = name;
        formLabel = getElementFactory().getLabel(locator, name);
    }

    /**
     * Locator for specified form.
     *
     * @return locator.
     */
    public By getLocator() {
        return locator;
    }

    /**
     * Name of specified form.
     *
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * Provides ability to get form's state (whether it is displayed, exists or not) and respective waiting functions.
     * @return state provider of the current form.
     */
    public IElementStateProvider state() {
        return getFormLabel().state();
    }

    /**
     * Scroll form without scrolling entire page
     *
     * @param x horizontal coordinate
     * @param y vertical coordinate
     */
    public void scrollBy(int x, int y) {
        getFormLabel().getMouseActions().scrollFromOrigin(x, y);
    }

    /**
     * Scroll form via JavaScript without scrolling entire page
     *
     * @param x horizontal coordinate
     * @param y vertical coordinate
     */
    public void scrollByJs(int x, int y) {
        getFormLabel().getJsActions().scrollBy(x, y);
    }

    /**
     * Gets size of form element defined by its locator.
     *
     * @return Size of the form element.
     */
    public Dimension getSize() {
        return getFormLabel().visual().getSize();
    }


    /**
     * Gets Label of form element defined by its locator and name.
     * Could be used to find child elements relative to form element.
     *
     * @return Label of form element.
     */
    protected ILabel getFormLabel() {
        return formLabel;
    }

    /**
     * Element factory {@link IElementFactory}
     *
     * @return instance of ElementFactory.
     */
    protected IElementFactory getElementFactory() {
        return AqualityServices.getElementFactory();
    }

    /**
     * Localized logger {@link ILocalizedLogger}
     *
     * @return instance of localized logger.
     */
    @Override
    protected ILocalizedLogger getLocalizedLogger() {
        return AqualityServices.getLocalizedLogger();
    }

    /**
     * Visualization configuration used by dump().
     *
     * @return instance of visualization configuration.
     */
    @Override
    protected IVisualizationConfiguration getVisualizationConfiguration() {
        return AqualityServices.getConfiguration().getVisualizationConfiguration();
    }
}
