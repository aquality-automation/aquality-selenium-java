package aquality.selenium.elements;

import aquality.selenium.core.elements.ElementState;
import aquality.selenium.core.utilities.IElementActionRetrier;
import aquality.selenium.elements.interfaces.ITextBox;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.Keys;

import java.util.ArrayList;

/**
 * The class that describes an input field
 */
public class TextBox extends Element implements ITextBox {

    private static final String LOG_TYPING = "loc.text.typing";
    private static final String LOG_CLEARING = "loc.text.clearing";
    private final String logMaskedValue = getLocalizationManager().getLocalizedMessage("loc.text.masked_value");

    protected TextBox(final By locator, final String name, final ElementState state) {
        super(locator, name, state);
    }

    protected String getElementType() {
        return getLocalizationManager().getLocalizedMessage("loc.text.field");
    }

    @Override
    public void type(final String value) {
        type(value, false);
    }

    @Override
    public void typeSecret(final String value) {
        type(value, true);
    }

    @Override
    public void clearAndType(final String value) {
        clearAndType(value, false);
    }

    @Override
    public void clear() {
        logElementAction(LOG_CLEARING);
        getJsActions().highlightElement();
        doWithRetry(() -> {
            getElement().clear();
        });
    }

    @Override
    public void clearAndTypeSecret(final String value) {
        clearAndType(value, true);
    }

    @Override
    public void submit() {
        doWithRetry(() -> getElement().submit());
    }

    @Override
    public String getValue() {
        return getAttribute(Attributes.VALUE.toString(), HighlightState.DEFAULT);
    }

    @Override
    public void focus() {
        doWithRetry(() -> getElement().sendKeys(""));
    }

    @Override
    public void unfocus() {
        doWithRetry(() -> getElement().sendKeys(Keys.TAB));
    }

    @Override
    protected void doWithRetry(Runnable action) {
        IElementActionRetrier retrier = getElementActionRetrier();
        ArrayList<Class<? extends Throwable>> handledExceptions = new ArrayList<>(retrier.getHandledExceptions());
        handledExceptions.add(JavascriptException.class);
        retrier.doWithRetry(action, handledExceptions);
    }

    private void type(final String value, final boolean maskValueInLog)
    {
        logElementAction(LOG_TYPING, maskValueInLog ? logMaskedValue : value);
        getJsActions().highlightElement();
        doWithRetry(() -> getElement().sendKeys(value));
    }

    private void clearAndType(final String value, final boolean maskValueInLog) {
        logElementAction(LOG_CLEARING);
        logElementAction(LOG_TYPING, maskValueInLog ? logMaskedValue : value);
        getJsActions().highlightElement();
        doWithRetry(() -> {
            getElement().clear();
            getElement().sendKeys(value);
        });
    }
}
