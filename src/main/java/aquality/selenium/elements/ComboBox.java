package aquality.selenium.elements;

import aquality.selenium.core.elements.ElementState;
import aquality.selenium.elements.actions.ComboBoxJsActions;
import aquality.selenium.elements.interfaces.IComboBox;
import aquality.selenium.utils.ElementActionRetrier;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Class describing the ComboBox (dropdown list)
 */
public class ComboBox extends Element implements IComboBox {

    private static final String LOG_SELECTING_VALUE = "loc.selecting.value";

    protected ComboBox(final By locator, final String name, final ElementState state) {
        super(locator, name, state);
    }

    protected String getElementType() {
        return getLocalizationManager().getLocalizedMessage("loc.combobox");
    }

    @Override
    public void selectByIndex(int index) {
        logElementAction(LOG_SELECTING_VALUE);
        ElementActionRetrier.doWithRetry(() -> new Select(getElement()).selectByIndex(index));
    }

    @Override
    public void selectByText(String value) {
        logElementAction("loc.combobox.select.by.text", value);
        ElementActionRetrier.doWithRetry(() -> new Select(getElement()).selectByVisibleText(value));
    }

    @Override
    public void clickAndSelectByText(String value) {
        click();
        selectByText(value);
    }

    @Override
    public void selectByContainingText(String text) {
        logElementAction(LOG_SELECTING_VALUE);
        selectOptionThatContains(WebElement::getText,
                Select::selectByVisibleText,
                text);
    }

    @Override
    public void selectByContainingValue(String value) {
        logElementAction(LOG_SELECTING_VALUE);
        selectOptionThatContains(element -> element.getAttribute(Attributes.VALUE.toString()),
                Select::selectByValue,
                value);
    }

    private void selectOptionThatContains(Function<WebElement, String> getValueFunc, BiConsumer<Select, String> selectFunc, String value){
        ElementActionRetrier.doWithRetry(() -> {
            Select select = new Select(getElement());
            List<WebElement> elements = select.getOptions();
            boolean isSelected = false;
            for (WebElement el: elements) {
                String currentValue = getValueFunc.apply(el);
                getLogger().debug(currentValue);
                if(currentValue.toLowerCase().contains(value.toLowerCase())){
                    selectFunc.accept(select, currentValue);
                    isSelected = true;
                }
            }
            if (!isSelected){
                throw new InvalidElementStateException(String.format(getLocalizationManager().getLocalizedMessage(
                        "loc.combobox.impossible.to.select.contain.value.or.text"), value, getName()));
            }
        });
    }

    @Override
    public void selectByValue(String value) {
        logElementAction(LOG_SELECTING_VALUE);
        ElementActionRetrier.doWithRetry(() -> new Select(getElement()).selectByValue(value));
    }

    @Override
    public void clickAndSelectByValue(String value) {
        click();
        selectByValue(value);
    }

    @Override
    public String getSelectedValue() {
        return ElementActionRetrier.doWithRetry(
                () -> new Select(getElement()).getFirstSelectedOption().getAttribute(Attributes.VALUE.toString()));
    }

    @Override
    public String getSelectedText() {
        return ElementActionRetrier.doWithRetry(() -> new Select(getElement()).getFirstSelectedOption().getText());
    }

    @Override
    public List<String> getValues() {
        logElementAction("loc.combobox.get.values");
        return ElementActionRetrier.doWithRetry(() ->
                new Select(getElement()).getOptions()
                        .stream()
                        .map(option -> option.getAttribute(Attributes.VALUE.toString()))
                        .collect(Collectors.toList()));
    }

    @Override
    public List<String> getTexts() {
        logElementAction("loc.combobox.get.texts");
        return ElementActionRetrier.doWithRetry(() ->
                new Select(getElement()).getOptions()
                        .stream()
                        .map(WebElement::getText)
                        .collect(Collectors.toList()));
    }

    @Override
    public ComboBoxJsActions getJsActions() {
        return new ComboBoxJsActions(this, getElementType());
    }

}
