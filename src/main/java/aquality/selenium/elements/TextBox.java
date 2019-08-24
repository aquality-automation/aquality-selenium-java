package aquality.selenium.elements;

import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.localization.LocalizationManager;
import aquality.selenium.utils.ElementActionRetrier;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

/**
 * The class that describes an input field
 */
public class TextBox extends Element implements ITextBox {

    private final String logTyping = getLocManager().getValue("loc.text.typing");
    private final String logClearing = getLocManager().getValue("loc.text.clearing");
    private final String logSendingKeys = getLocManager().getValue("loc.text.sending.keys");
    private final String logMaskedValue = getLocManager().getValue("loc.text.masked_value");

    protected TextBox(final By locator, final String name, final ElementState state) {
        super(locator, name, state);
    }

    protected String getElementType() {
        return LocalizationManager.getInstance().getValue("loc.text.field");
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
    public void sendKeys(final Keys keys) {
        info(String.format(logSendingKeys, keys.toString()));
        super.sendKeys(keys);
    }

    @Override
    public void clearAndType(final String value) {
        clearAndType(value, false);
    }

    @Override
    public void clearAndTypeSecret(final String value) {
        clearAndType(value, true);
    }

    @Override
    public void submit() {
        ElementActionRetrier.doWithRetry(() -> getElement().submit());
    }

    @Override
    public String getValue() {
        return getAttribute(Attributes.VALUE.toString());
    }

    @Override
    public void focus() {
        ElementActionRetrier.doWithRetry(() -> getElement().sendKeys(""));
    }

    @Override
    public void unfocus() {
        ElementActionRetrier.doWithRetry(() -> getElement().sendKeys(Keys.TAB));
    }

    private void type(final String value, final boolean maskValueInLog) {
        info(String.format(logTyping, maskValueInLog ? logMaskedValue : value));
        getJsActions().highlightElement();
        ElementActionRetrier.doWithRetry(() -> getElement().sendKeys(value));
    }

    private void clearAndType(final String value, final boolean maskValueInLog) {
        getJsActions().highlightElement();
        info(logClearing);
        info(String.format(logTyping, maskValueInLog ? logMaskedValue : value));
        getJsActions().highlightElement();
        ElementActionRetrier.doWithRetry(() -> {
            getElement().clear();
            getElement().sendKeys(value);
        });
    }
}
