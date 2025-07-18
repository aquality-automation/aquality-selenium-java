package manytools;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.utilities.IActionRetrier;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

import java.util.Collections;

public abstract class ManyToolsForm<T extends ManyToolsForm<T>> extends Form {
    private static final String BASE_URL = "https://manytools.org/";
    private final ILabel lblValue = getFormLabel().findChildElement(By.xpath(".//code"), getName(), ILabel.class);
    private final IButton btnAgree = getElementFactory().getButton(By.xpath("//button[@mode='primary']"), "Agree");

    protected ManyToolsForm(String name) {
        super(By.id("maincontent"), name);
    }

    protected abstract String getUrlPart();

    @SuppressWarnings("unchecked")
    public T open() {
        AqualityServices.get(IActionRetrier.class).doWithRetry(() -> {
            AqualityServices.getBrowser().goTo(BASE_URL + getUrlPart());
        }, Collections.singletonList(TimeoutException.class));
        return (T) this;
    }

    public String getValue() {
        return lblValue.getText();
    }
}
