package aquality.selenium.elements;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import aquality.selenium.browser.JavaScript;
import aquality.selenium.core.configurations.IElementCacheConfiguration;
import aquality.selenium.core.configurations.ITimeoutConfiguration;
import aquality.selenium.core.elements.CachedElementStateProvider;
import aquality.selenium.core.elements.ElementState;
import aquality.selenium.core.elements.interfaces.IElementFinder;
import aquality.selenium.core.elements.interfaces.IElementStateProvider;
import aquality.selenium.core.localization.ILocalizationManager;
import aquality.selenium.core.localization.ILocalizedLogger;
import aquality.selenium.core.utilities.IElementActionRetrier;
import aquality.selenium.core.waitings.IConditionalWait;
import aquality.selenium.elements.actions.JsActions;
import aquality.selenium.elements.actions.MouseActions;
import aquality.selenium.elements.interfaces.IElement;
import aquality.selenium.elements.interfaces.IElementFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.RemoteWebElement;

import java.time.Duration;

/**
 * Abstract class, describing wrapper of WebElement.
 */
public abstract class Element extends aquality.selenium.core.elements.Element implements IElement {
    /**
     * The main constructor
     *
     * @param loc     By Locator
     * @param nameOf  Output in logs
     * @param stateOf desired ElementState
     */
    protected Element(final By loc, final String nameOf, final ElementState stateOf) {
        super(loc, nameOf, stateOf);
    }

    @Override
    protected Browser getApplication() {
        return AqualityServices.getBrowser();
    }

    @Override
    protected IElementFactory getElementFactory() {
        return AqualityServices.getElementFactory();
    }

    @Override
    protected IElementFinder getElementFinder() {
        return AqualityServices.get(IElementFinder.class);
    }

    @Override
    protected IElementCacheConfiguration getElementCacheConfiguration() {
        return AqualityServices.get(IElementCacheConfiguration.class);
    }

    @Override
    protected IElementActionRetrier getElementActionRetrier() {
        return AqualityServices.get(IElementActionRetrier.class);
    }

    @Override
    protected ILocalizedLogger getLocalizedLogger() {
        return AqualityServices.getLocalizedLogger();
    }

    protected ILocalizationManager getLocalizationManager() {
        return AqualityServices.get(ILocalizationManager.class);
    }

    @Override
    protected IConditionalWait getConditionalWait() {
        return AqualityServices.getConditionalWait();
    }

    @Override
    public RemoteWebElement getElement(Duration timeout) {
        try {
            return super.getElement(timeout);
        } catch (NoSuchElementException e) {
            getLogger().error(e.getMessage());
            long timeoutInSeconds = timeout == null
                    ? AqualityServices.get(ITimeoutConfiguration.class).getCondition().getSeconds()
                    : timeout.getSeconds();
            throw new NoSuchElementException(
                    String.format("element %s was not found in %d seconds in state %s by locator %s",
                            getName(), timeoutInSeconds, getElementState(), getLocator()));
        }
    }

    @Override
    public void click() {
        logElementAction("loc.clicking");
        getJsActions().highlightElement();
        doWithRetry(() -> getElement().click());
    }

    @Override
    public void clickAndWait() {
        click();
        getBrowser().waitForPageToLoad();
    }

    @Override
    public String getText() {
        return getText(HighlightState.DEFAULT);
    }

    @Override
    public String getText(HighlightState highlightState) {
        logElementAction("loc.get.text");
        getJsActions().highlightElement();
        String value = doWithRetry(() -> getElement().getText());
        logElementAction("loc.text.value", value);
        return value;
    }

    @Override
    public IElementStateProvider state() {
        return getElementCacheConfiguration().isEnabled()
                ? new CachedElementStateProvider(getLocator(), getConditionalWait(), getCache(), logElementState())
                : new ElementStateProvider(getLocator(), getConditionalWait(), getElementFinder(), logElementState());
    }

    @Override
    public String getAttribute(final String attr, HighlightState highlightState) {
        logElementAction("loc.el.getattr", attr);
        getJsActions().highlightElement();
        String value = doWithRetry(() -> getElement().getAttribute(attr));
        logElementAction("loc.el.attr.value", attr, value);
        return value;
    }

    @Override
    public String getCssValue(final String propertyName, HighlightState highlightState) {
        logElementAction("loc.el.cssvalue", propertyName);
        getJsActions().highlightElement();
        String value = doWithRetry(() -> getElement().getCssValue(propertyName));
        logElementAction("loc.el.attr.value", propertyName, value);
        return value;
    }

    @Override
    public void setInnerHtml(final String value) {
        click();
        logElementAction("loc.send.text", value);
        getBrowser().executeScript(JavaScript.SET_INNER_HTML, getElement(), value);
    }

    private Browser getBrowser() {
        return AqualityServices.getBrowser();
    }

    @Override
    public JsActions getJsActions() {
        return new JsActions(this, getElementType());
    }

    @Override
    public MouseActions getMouseActions() {
        return new MouseActions(this, getElementType());
    }

    @Override
    public <T extends IElement> T findChildElement(By childLoc, String name, ElementType elementType, ElementState state) {
        return getElementFactory().findChildElement(this, childLoc, name, elementType, state);
    }

    @Override
    public void sendKeys(Keys key) {
        logElementAction("loc.text.sending.keys", Keys.class.getSimpleName().concat(".").concat(key.name()));
        doWithRetry(() -> getElement().sendKeys(key));
    }
}
