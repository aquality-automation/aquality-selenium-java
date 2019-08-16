package theinternet.forms;

import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public abstract class TheInternetForm extends Form {

    TheInternetForm(By locator, String name){
        super(locator, name);
    }

    private final String theInternetFormUrl = "http://the-internet.herokuapp.com";

    public String getUrl(){
        return theInternetFormUrl + getUri();
    }

    protected abstract String getUri();
}
