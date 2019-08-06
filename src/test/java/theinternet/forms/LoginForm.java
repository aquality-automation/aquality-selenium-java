package theinternet.forms;

import aquality.selenium.forms.PageInfo;
import aquality.selenium.elements.ElementState;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

@PageInfo(id = "login", pageName = "Login")
public class LoginForm extends Form {

    private static final long DEFAULT_LOADING_TIMEOUT = 5L;
    private static final String XPATH_FORM_LOGIN = "//form[@id='login']";

    private final ITextBox txbUsername = getElementFactory().getTextBox(By.id("username"), "username");
    private final ITextBox txbPassword = getElementFactory().getTextBox(By.id("password"), "password", ElementState.DISPLAYED);
    private final ILabel lblLogin = getElementFactory().getLabel(By.xpath(XPATH_FORM_LOGIN), "login");

    public String getXPathFormLogin(){
        return XPATH_FORM_LOGIN;
    }

    public ILabel getLblLogin() {
        return lblLogin;
    }

    public ITextBox getTxbUsername() {
        return txbUsername;
    }

    public ITextBox getTxbPassword() {
        return txbPassword;
    }

    public long getTimeout() {
        return DEFAULT_LOADING_TIMEOUT;
    }
}
