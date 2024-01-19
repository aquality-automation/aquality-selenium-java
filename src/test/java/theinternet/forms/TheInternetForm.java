package theinternet.forms;

import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public abstract class TheInternetForm extends Form {

    TheInternetForm(By locator, String name) {
        super(locator, name);
    }

    private static final String THE_INTERNET_FORM_URL = "https://the-internet.herokuapp.com";
    private final ILink elementalSeleniumLink = getElementFactory().getLink(By.xpath("//a[contains(@href,'elementalselenium')]"), "Elemental Selenium");

    public String getUrl() {
        return THE_INTERNET_FORM_URL + getUri();
    }

    public void clickElementalSelenium() {
        elementalSeleniumLink.click();
    }

    protected abstract String getUri();
}
