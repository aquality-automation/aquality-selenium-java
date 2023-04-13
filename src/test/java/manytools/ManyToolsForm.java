package manytools;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public abstract class ManyToolsForm<T extends ManyToolsForm<T>> extends Form {
    private static final String BASE_URL = "https://manytools.org/";
    private final ILabel lblValue = getFormLabel().findChildElement(By.xpath(".//code"), getName(), ILabel.class);

    protected ManyToolsForm(String name) {
        super(By.id("maincontent"), name);
    }

    protected abstract String getUrlPart();

    @SuppressWarnings("unchecked")
    public T open() {
        AqualityServices.getBrowser().goTo(BASE_URL + getUrlPart());
        AqualityServices.getBrowser().waitForPageToLoad();
        return (T) this;
    }

    public String getValue() {
        return lblValue.getText();
    }
}
