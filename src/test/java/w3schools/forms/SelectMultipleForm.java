package w3schools.forms;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.IMultiChoiceBox;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SelectMultipleForm extends Form {

    private final IMultiChoiceBox cbxCars = getElementFactory().getMultiChoiceBox(By.id("cars"), "Cars");
    private final IButton btnSubmit = getElementFactory().getButton(By.cssSelector("input[type='submit']"), "Submit");
    private final ITextBox txbResult = getElementFactory().getTextBox(By.cssSelector(".w3-large"), "Result");
    private final IButton btnAcceptCookies = getElementFactory().getButton(By.id("accept-choices"), "Accept cookies");

    public SelectMultipleForm() {
        super(By.id("iframe"), "Select Multiple");
    }

    public void acceptCookies() {
        if (btnAcceptCookies.state().isDisplayed()) {
            btnAcceptCookies.click();
            btnAcceptCookies.state().waitForNotDisplayed();
        }
    }

    public void switchToResultFrame() {
        AqualityServices.getBrowser().getDriver().switchTo().frame("iframeResult");
    }

    public void submit() {
        btnSubmit.click();
    }

    public List<String> getValuesFromResult() {
        String result = txbResult.getText().trim();

        List<String> results = new ArrayList<>();
        Pattern pattern = Pattern.compile(".*?=(.*?)(&|$)");
        Matcher matcher = pattern.matcher(result);
        while (matcher.find()) {
            results.add(matcher.group(1));
        }
        return results;
    }

    public void selectAll() {
        cbxCars.selectAll();
    }

    public List<String> getAllTexts() {
        return cbxCars.getTexts();
    }

    public List<String> getSelectedTexts() {
        return cbxCars.getSelectedTexts();
    }

    public List<String> getSelectedValues() {
        return cbxCars.getSelectedValues();
    }

    public List<String> deselectByValue(List<String> valuesToDeselect) {
        return deselectBy(cbxCars::deselectByValue, valuesToDeselect);
    }

    public List<String> deselectByContainingValue(List<java.lang.String> valuesToDeselect) {
        return deselectBy(cbxCars::deselectByContainingValue, valuesToDeselect);
    }

    public List<String> deselectByText(List<String> textToDeselect) {
        return deselectBy(cbxCars::deselectByText, textToDeselect);
    }

    public List<String> deselectByContainingText(List<java.lang.String> textToDeselect) {
        return deselectBy(cbxCars::deselectByContainingText, textToDeselect);
    }

    public List<String> deselectByIndex(List<Integer> indicesToDeselect) {
        List<String> values = cbxCars.getValues();
        indicesToDeselect.forEach(cbxCars::deselectByIndex);
        return IntStream.range(0, values.size())
                .filter(i -> !indicesToDeselect.contains(i))
                .mapToObj(values::get)
                .collect(Collectors.toList());
    }

    public void deselectAll() {
        cbxCars.deselectAll();
    }

    private List<String> deselectBy(Consumer<String> deselectFunc, List<String> valuesToDeselect) {
        valuesToDeselect.forEach(deselectFunc);
        return collectRemaining(valuesToDeselect);
    }

    private List<String> collectRemaining(List<String> valuesToDeselect) {
        return cbxCars.getValues().stream()
                .filter(v -> valuesToDeselect.stream()
                        .noneMatch(deselected -> v.contains(deselected.toLowerCase())))
                .collect(Collectors.toList());
    }
}
