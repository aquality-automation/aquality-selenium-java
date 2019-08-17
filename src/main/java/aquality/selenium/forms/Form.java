package aquality.selenium.forms;

import aquality.selenium.elements.ElementFactory;
import aquality.selenium.elements.interfaces.IElementFactory;
import aquality.selenium.logger.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;


public abstract class Form {

    private static final Logger logger = Logger.getInstance();
    /**
     * Locator for specified form
     */
    protected final By locator;
    /**
     * Name of specified form
     */
    protected final String name;

    private final IElementFactory elementFactory;

    /**
     * Contructor with parameters
     */
    protected Form(By locator, String name) {
        this.locator = locator;
        this.name = name;
        this.elementFactory = new ElementFactory();
    }

    /**
     * Return form state for form locator
     *
     * @return True - form is opened,
     * False - form is not opened
     */
    public boolean isFormDisplayed() {
        return getElementFactory().getLabel(locator, name).state().waitForDisplayed();
    }

    /**
     * Return form state for form locator
     *
     * @param timeout timeout for action
     * @return True - form is opened,
     * False - form is not opened
     */
    public boolean isFormDisplayed(Long timeout) {
        return getElementFactory().getLabel(locator, name).state().waitForDisplayed(timeout);
    }

    /**
     * Scroll form without scrolling entire page
     * @param x horizontal coordinate
     * @param y vertical coordinate
     */
    public void scrollBy(int x, int y) {
        getElementFactory().getLabel(locator, name).getJsActions().scrollBy(x, y);
    }

    public Dimension getFormSize() {
        return getElementFactory().getLabel(locator, name).getElement().getSize();
    }

    protected IElementFactory getElementFactory(){
        return elementFactory;
    }
}
