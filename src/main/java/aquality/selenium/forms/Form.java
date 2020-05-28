package aquality.selenium.forms;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.localization.ILocalizedLogger;
import aquality.selenium.elements.interfaces.IElementFactory;
import aquality.selenium.elements.interfaces.ILabel;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;

/**
 * Defines base class for any UI form.
 */
public abstract class Form extends FormParent {
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
        this.locator = locator;
        this.name = name;
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
     * Return form state for form locator
     *
     * @return True - form is opened,
     * False - form is not opened
     */
    public boolean isDisplayed() {
        return getFormLabel().state().waitForDisplayed();
    }

    /**
     * Scroll form without scrolling entire page
     *
     * @param x horizontal coordinate
     * @param y vertical coordinate
     */
    public void scrollBy(int x, int y) {
        getFormLabel().getJsActions().scrollBy(x, y);
    }

    /**
     * Gets size of form element defined by its locator.
     *
     * @return Size of the form element.
     */
    public Dimension getSize() {
        return getFormLabel().getElement().getSize();
    }


    /**
     * Gets Label of form element defined by its locator and name.
     *
     * @return Label of form element.
     */
    @Override
    protected ILabel getFormLabel() {
        return getElementFactory().getLabel(locator, name);
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
    protected ILocalizedLogger getLogger() {
        return AqualityServices.getLocalizedLogger();
    }
}
