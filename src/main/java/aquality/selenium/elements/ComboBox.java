package aquality.selenium.elements;

import aquality.selenium.elements.actions.ComboBoxJsActions;
import aquality.selenium.elements.interfaces.IComboBox;
import aquality.selenium.localization.LocalizationManager;
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

    private static final String LOG_SELECTING_VALUE = LocalizationManager.getInstance().getValue("loc.selecting.value");

    protected ComboBox(final By locator, final String name, final ElementState state) {
        super(locator, name, state);
    }

    protected String getElementType() {
        return getLocManager().getValue("loc.combobox");
    }

    @Override
    public void selectByIndex(int index) {
        info(LOG_SELECTING_VALUE);
        ElementActionRetrier.doWithRetry(() -> new Select(getElement()).selectByIndex(index));
    }

    @Override
    public void selectByText(String value) {
        getLogger().info(getLocManager().getValue("loc.combobox.select.by.text"), value);
        ElementActionRetrier.doWithRetry(() -> new Select(getElement()).selectByVisibleText(value));
    }

    @Override
    public void clickAndSelectByText(String value) {
        click();
        selectByText(value);
    }

    @Override
    public void selectByContainingText(String text) {
        info(LOG_SELECTING_VALUE);
        selectOptionThatContains(WebElement::getText,
                Select::selectByVisibleText,
                text);
    }

    @Override
    public void selectByContainingValue(String value) {
        info(LOG_SELECTING_VALUE);
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
                throw new InvalidElementStateException(String.format(getLocManager().getValue("loc.combobox.impossible.to.select.contain.value.or.text"), value, getName()));
            }
        });
    }

    @Override
    public void selectByValue(String value) {
        info(LOG_SELECTING_VALUE);
        ElementActionRetrier.doWithRetry(() -> new Select(getElement()).selectByValue(value));
    }

    @Override
    public void clickAndSelectByValue(String value) {
        click();
        selectByValue(value);
    }

    @Override
    public String getSelectedText() {
        if(ElementFinder.getInstance().findElements(getLocator(), getDefaultTimeout(), getElementState()).isEmpty()) {
            throw new IllegalStateException(String.format(getLocManager().getValue("loc.element.wasnotfoundinstate"), getName(), getElementState(), getDefaultTimeout()));
        }
        return ElementActionRetrier.doWithRetry(() -> new Select(getElement()).getFirstSelectedOption().getText());
    }

    @Override
    public List<String> getValues() {
        getLogger().info(getLocManager().getValue("loc.combobox.get.values"));
        if(ElementFinder.getInstance().findElements(getLocator(), getDefaultTimeout(), getElementState()).isEmpty()){
            throw new IllegalStateException(String.format(getLocManager().getValue("loc.element.wasnotfoundinstate"), getName(), getElementState(), getDefaultTimeout()));
        }
        return ElementActionRetrier.doWithRetry(() ->
                new Select(getElement()).getOptions()
                        .stream()
                        .map(option -> option.getText().isEmpty() ?
                                option.getAttribute(Attributes.VALUE.toString()) : option.getText())
                        .collect(Collectors.toList()));
    }

    @Override
    public String getSelectedTextByJs() {
        getLogger().info(getLocManager().getValue("loc.get.text"));
        return getJsActions().getSelectedText();
    }

    @Override
    public ComboBoxJsActions getJsActions() {
        return new ComboBoxJsActions(this, getElementType());
    }

}
