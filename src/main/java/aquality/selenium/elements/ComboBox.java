package aquality.selenium.elements;

import aquality.selenium.elements.actions.ComboBoxJsActions;
import aquality.selenium.elements.interfaces.IComboBox;
import aquality.selenium.localization.LocalizationManager;
import aquality.selenium.utils.ElementActionRetrier;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
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
    public void selectOptionThatContainsText(String text) {
        info(LOG_SELECTING_VALUE);
        ElementActionRetrier.doWithRetry(() -> {
            Select select = new Select(getElement());
            List<WebElement> elements = select.getOptions();
            for (WebElement el: elements) {
                String currentText = el.getText();
                getLogger().debug(currentText);
                if(currentText.toLowerCase().contains(text.toLowerCase())){
                    select.selectByVisibleText(currentText);
                }
            }
        });
    }

    @Override
    public void selectOptionThatContainsValue(String value) {
        info(LOG_SELECTING_VALUE);
        ElementActionRetrier.doWithRetry(() -> {
            Select select = new Select(getElement());
            List<WebElement> elements = select.getOptions();
            for (WebElement el: elements) {
                String currentValue = el.getAttribute(Attributes.VALUE.toString());
                getLogger().debug(currentValue);
                if(currentValue.toLowerCase().contains(value.toLowerCase())){
                    select.selectByValue(currentValue);
                }
            }
        });
    }

    @Override
    public void selectByValue(String value) {
        info(LOG_SELECTING_VALUE);
        ElementActionRetrier.doWithRetry(() -> new Select(getElement()).selectByValue(value));
    }

    @Override
    public String getSelectedText() {
        if(ElementFinder.getInstance().findElements(getLocator(), getDefaultTimeout(), getElementState()).isEmpty()) {
            throw new IllegalStateException(String.format(getLocManager().getValue("loc.element.wasnotfoundinstate"), getName(), getElementState(), getDefaultTimeout()));
        }
        return ElementActionRetrier.doWithRetry(() -> new Select(getElement()).getFirstSelectedOption().getText());
    }

    @Override
    public List<String> getValuesList() {
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
        return new ComboBoxJsActions(this, getElementType(), getName());
    }

}
