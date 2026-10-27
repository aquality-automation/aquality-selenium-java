package theinternet.forms;

import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public abstract class TheInternetForm extends Form {

    TheInternetForm(By locator, String name) {
        super(locator, name);
    }

    private static final String THE_INTERNET_FORM_URL = "https://the-internet.herokuapp.com";
    public String getUrl() {
        return THE_INTERNET_FORM_URL + getUri();
    }

    protected abstract String getUri();
}
