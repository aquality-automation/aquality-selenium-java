package aquality.selenium.elements;

import aquality.selenium.core.elements.ElementState;
import aquality.selenium.elements.interfaces.IMultiChoiceBox;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Class describing the Multi-choice ComboBox (dropdown list), i.e. the one having attribute {@code multiple} set to {@code true}
 */
public class MultiChoiceBox extends ComboBox implements IMultiChoiceBox {

    private static final String LOG_DESELECTING_VALUE = "loc.deselecting.value";

    protected MultiChoiceBox(By locator, String name, ElementState state) {
        super(locator, name, state);
    }

    protected String getElementType() {
        return getLocalizationManager().getLocalizedMessage("loc.multichoicebox");
    }

    @Override
    public List<String> getSelectedValues() {
        logElementAction("loc.combobox.getting.selected.value");
        return collectSelectedOptions(option -> option.getAttribute(Attributes.VALUE.toString()), "value");
    }

    @Override
    public List<String> getSelectedTexts() {
        logElementAction("loc.combobox.getting.selected.text");
        return collectSelectedOptions(WebElement::getText, "text");
    }

    private List<String> collectSelectedOptions(Function<WebElement, String> valueGetter, String valueType) {
        List<String> texts = doWithRetry(() ->
                new Select(getElement()).getAllSelectedOptions()
                        .stream()
                        .map(valueGetter)
                        .collect(Collectors.toList()));
        String logValue = texts.stream().map(value -> String.format("'%s'", value)).collect(Collectors.joining(", "));
        logElementAction(String.format("loc.combobox.selected.%s", valueType), logValue);
        return texts;
    }

    @Override
    public void selectAll() {
        logElementAction("loc.multichoicebox.select.all");
        applyFunctionToOptionsThatContain(element -> element.getAttribute(Attributes.VALUE.toString()),
                Select::selectByValue,
                StringUtils.EMPTY);
    }

    @Override
    public void deselectAll() {
        logElementAction("loc.multichoicebox.deselect.all");
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
        applyFunctionToOptionsThatContain(element -> element.getAttribute(Attributes.VALUE.toString()),
                Select::deselectByValue,
                value);
    }

    @Override
    public void deselectByText(String text) {
        logElementAction("loc.multichoicebox.deselect.by.text", text);
        doWithRetry(() -> new Select(getElement()).deselectByVisibleText(text));
    }

    @Override
    public void deselectByContainingText(String text) {
        logElementAction("loc.multichoicebox.deselect.by.text", text);
        applyFunctionToOptionsThatContain(WebElement::getText,
                Select::deselectByVisibleText,
                text);
    }
}
