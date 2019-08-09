package aquality.selenium.elements;

import aquality.selenium.browser.Browser;
import aquality.selenium.browser.BrowserManager;
import aquality.selenium.browser.JavaScript;
import aquality.selenium.configuration.Configuration;
import aquality.selenium.elements.actions.JsActions;
import aquality.selenium.elements.actions.MouseActions;
import aquality.selenium.elements.interfaces.IElement;
import aquality.selenium.elements.interfaces.IElementStateProvider;
import aquality.selenium.elements.interfaces.IElementSupplier;
import aquality.selenium.localization.LocalizationManager;
import aquality.selenium.logger.Logger;
import aquality.selenium.waitings.ConditionalWait;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;

/**
 * Abstract class, describing wrapper of WebElement.
 */
public abstract class Element implements IElement {
    private static final String LOG_DELIMITER = "::";
    private static final String LOG_CLICKING = "loc.clicking";

    /**
     * Name of element
     */
    private final String name;

    /**
     * Element state (DISPLAYED by default)
     */
    private final ElementState state;

    /**
     * Element locator
     */
    private final By locator;

    /**
     * Element state provider
     */
    private final ElementStateProvider elementStateProvider;

    /**
     * The main constructor
     *
     * @param loc    By Locator
     * @param nameOf Output in logs
     * @param stateOf desired ElementState
     */
    protected Element(final By loc, final String nameOf, final ElementState stateOf) {
        locator = loc;
        name = nameOf;
        state = stateOf;
        elementStateProvider = new ElementStateProvider(locator);
    }

    @Override
    public RemoteWebElement getElement() {
        return getElement(getDefaultTimeout());
    }

    @Override
    public RemoteWebElement getElement(Long timeout) {
        try {
            return (RemoteWebElement) ElementFinder.getInstance().findElement(locator, timeout, getElementState());
        } catch (NoSuchElementException e) {
            getLogger().error(e.getMessage());
            getLogger().debug("Page Source:\r\n" + getBrowser().getDriver().getPageSource());
            throw new NoSuchElementException(
                    String.format("element %s was not found in %d seconds in state %s by locator %s",
                            getName(), timeout, getElementState(), getLocator()));
        }
    }

    @Override
    public By getLocator() {
        return locator;
    }

    @Override
    public ElementState getElementState() {
        return state;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * The method returns the element type (used for logging)
     *
     * @return Type of element
     */
    protected abstract String getElementType();

    @Override
    public void sendKeys(Keys key) {
        ConditionalWait.waitFor(y -> {
            getElement().sendKeys(key);
            return true;
        });
    }

    @Override
    public void waitAndClick() {
        state().waitForEnabled(getDefaultTimeout());
        info(getLocManager().getValue(LOG_CLICKING));
        click();
    }


    @Override
    public void click() {
        getJsActions().highlightElement();
        ConditionalWait.waitFor(y -> {
            WebElement el = getElement();
            info(getLocManager().getValue(LOG_CLICKING));
            el.click();
            return true;
        });
    }

    @Override
    public void clickAndWait() {
        click();
        getBrowser().waitForPageToLoad();
    }

    @Override
    public String getText() {
        return getText(HighlightState.NOT_HIGHLIGHT);
    }

    @Override
    public String getText(HighlightState highlightState) {
        Logger.getInstance().debug(getLocManager().getValue("loc.get.text"));
        if(highlightState.equals(HighlightState.HIGHLIGHT)){
            getJsActions().highlightElement();
        }
        if(ElementFinder.getInstance().findElements(locator, getDefaultTimeout(), state).isEmpty()){
            throw new IllegalStateException(String.format(getLocManager().getValue("loc.element.wasnotfoundinstate"), getName(), state, getDefaultTimeout()));
        }
        return ConditionalWait.waitFor(y -> getElement().getText());
    }

    @Override
    public IElementStateProvider state() {
        return elementStateProvider;
    }

    @Override
    public String getAttribute(final String attr, HighlightState highlightState, long timeout) {
        getLogger().debug(getLocManager().getValue("loc.el.getattr"), attr);
        if (highlightState.equals(HighlightState.HIGHLIGHT)) {
            getJsActions().highlightElement();
        }
        return String.valueOf(ConditionalWait.<String>waitFor(y -> getElement(timeout).getAttribute(attr), timeout + 5L));
    }

    @Override
    public String getAttribute(final String attr, long timeout) {
        return getAttribute(attr, HighlightState.NOT_HIGHLIGHT, timeout);
    }

    @Override
    public String getAttribute(final String attr) {
        return getAttribute(attr, HighlightState.NOT_HIGHLIGHT, getDefaultTimeout());
    }

    @Override
    public String getAttribute(final String attr, HighlightState highlightState) {
        return getAttribute(attr, highlightState, getDefaultTimeout());
    }

    @Override
    public void setInnerHtml(final String value) {
        click();
        getLogger().info(getLocManager().getValue("loc.send.text"), value);
        getBrowser().executeScript(JavaScript.SET_INNER_HTML.getScript(), getElement(), value);
    }

    @Override
    public void clickRight() {
        getLogger().info(getLocManager().getValue("loc.clicking.right"));
        ConditionalWait.waitFor(y -> {
            Actions actions = new Actions(getBrowser().getDriver());
            actions.moveToElement(getElement());
            actions.contextClick(getElement()).build().perform();
            return true;
        });
    }

    @Override
    public void focus() {
        ConditionalWait.waitFor(y -> {
            getBrowser().getDriver().getMouse().mouseMove(getElement().getCoordinates());
            return true;
        });
    }

    private Browser getBrowser(){
        return BrowserManager.getBrowser();
    }

    /**
     * Format message for logging of current element
     *
     * @param message Message to display in the log
     * @return Formatted message (containing the name and type of item)
     */
    private String formatLogMsg(final String message) {
        return String.format("%1$s '%2$s' %3$s %4$s", getElementType(), getName(), LOG_DELIMITER, message);
    }

    protected void info(final String message) {
        getLogger().info(formatLogMsg(message));
    }

    @Override
    public JsActions getJsActions() {
        return new JsActions(this, getElementType(), getName());
    }

    @Override
    public MouseActions getMouseActions() {
        return new MouseActions(this, getElementType(), getName());
    }

    @Override
    public <T extends IElement> T findChildElement(By childLoc, ElementType type, ElementState state) {
        return new ElementFactory().findChildElement(this, childLoc, type, state);
    }

    @Override
    public <T extends IElement> T findChildElement(By childLoc, Class<? extends IElement> clazz, ElementState state) {
        return new ElementFactory().findChildElement(this, childLoc, clazz, state);
    }

    @Override
    public <T extends IElement> T findChildElement(By childLoc, IElementSupplier<T> supplier, ElementState state) {
        return new ElementFactory().findChildElement(this, childLoc, supplier, state);
    }

    protected Logger getLogger(){
        return Logger.getInstance();
    }

    protected LocalizationManager getLocManager(){
        return LocalizationManager.getInstance();
    }

    long getDefaultTimeout(){
        return Configuration.getInstance().getTimeoutConfiguration().getCondition();
    }
}