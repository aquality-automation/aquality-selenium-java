package aquality.selenium.forms;

import aquality.selenium.elements.ElementFactory;
import aquality.selenium.elements.interfaces.IElementFactory;
import aquality.selenium.localization.LocalizationManager;
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
     * Contructor
     */
    protected Form() {
        PageInfo pageInfo = this.getClass().getAnnotation(PageInfo.class);
        name = pageInfo.pageName();
        locator = getLocatorFromPageInfo(pageInfo);
        elementFactory = new ElementFactory();
    }

    /**
     * Contructor with parameters
     */
    protected Form(By locator, String name) {
        this.locator = locator;
        this.name = name;
        this.elementFactory = new ElementFactory();
    }

    /**
     * Get locator for form from pageInfo annotation
     *
     * @param pageInfo form pageInfo
     * @return Form locator
     */
    protected By getLocatorFromPageInfo(PageInfo pageInfo) {
        if (!pageInfo.xpath().isEmpty()) {
            return By.xpath(pageInfo.xpath());
        } else if (!pageInfo.id().isEmpty()) {
            return By.id(pageInfo.id());
        } else if (!pageInfo.css().isEmpty()) {
            return By.cssSelector(pageInfo.css());
        }
        String msg = String.format(LocalizationManager.getInstance().getValue("loc.baseform.unknown.type"), name);
        IllegalArgumentException ex = new IllegalArgumentException(msg);
        logger.fatal(msg, ex);
        throw ex;
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
