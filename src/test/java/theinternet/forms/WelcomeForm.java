package theinternet.forms;

import org.openqa.selenium.By;

public class WelcomeForm extends TheInternetForm {

    public WelcomeForm() {
        super(By.xpath("//h1[contains(.,'Welcome to the-internet')]"), "Welcome to the-internet");
    }

    @Override
    protected String getUri() {
        return "";
    }
}
