package theinternet.forms;

import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public abstract class TheInternetForm extends Form {

    TheInternetForm(By locator, String name) {
        super(locator, name);
    }

    private final String theInternetFormUrl = "http://the-internet.herokuapp.com";
    private ILink elementalSeleniumLink = getElementFactory().getLink(By.xpath("//a[contains(@href,'elementalselenium')]"), "Elemental Selenium");

    public String getUrl() {
        return theInternetFormUrl + getUri();
    }

    public void clickElementalSelenium() {
        elementalSeleniumLink.click();
    }

    protected abstract String getUri();
}
