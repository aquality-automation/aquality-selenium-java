package aquality.selenium.elements;

import aquality.selenium.core.elements.ElementState;
import aquality.selenium.elements.interfaces.IMultiChoiceComboBox;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * Class describing the Multi-choice ComboBox (dropdown list), i.e. the one having attribute {@code multiple} set to {@code true}
 */
public class MultiChoiceComboBox extends ComboBox implements IMultiChoiceComboBox {

    private static final String LOG_DESELECTING_VALUE = "loc.deselecting.value";

    protected MultiChoiceComboBox(By locator, String name, ElementState state) {
        super(locator, name, state);
    }

    protected String getElementType() {
        return getLocalizationManager().getLocalizedMessage("loc.multichoicecombobox");
    }

    @Override
    public void deselectAll() {
        logElementAction("loc.multichoicecombobox.deselect.all");
        doWithRetry(() -> new Select(getElement()).deselectAll());
    }

    @Override
    public void deselectByIndex(int index) {
        logElementAction(LOG_DESELECTING_VALUE, String.format("#%s", index));
        doWithRetry(() -> new Select(getElement()).deselectByIndex(index));
    }

    @Override
    public void deselectByValue(String value) {
        logElementAction(LOG_DESELECTING_VALUE, value);
        doWithRetry(() -> new Select(getElement()).deselectByValue(value));
    }

    @Override
    public void deselectByContainingValue(String value) {
        logElementAction(LOG_DESELECTING_VALUE, value);
        applySelectFuncToOptionThatContains(element -> element.getAttribute(Attributes.VALUE.toString()),
                Select::deselectByValue,
                value);
    }

    @Override
    public void deselectByText(String text) {
        logElementAction("loc.multichoicecombobox.deselect.by.text", text);
        doWithRetry(() -> new Select(getElement()).deselectByVisibleText(text));
    }

    @Override
    public void deselectByContainingText(String text) {
        logElementAction("loc.multichoicecombobox.deselect.by.text", text);
        applySelectFuncToOptionThatContains(WebElement::getText,
                Select::deselectByVisibleText,
                text);
    }
}
