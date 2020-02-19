package theinternet.forms;

import aquality.selenium.core.elements.ElementState;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.elements.interfaces.ITextBox;
import org.openqa.selenium.By;

import java.time.Duration;

public class FormAuthenticationForm extends TheInternetForm {

    public FormAuthenticationForm() {
        super(By.id("login"), "login");
    }

    private static final Duration DEFAULT_LOADING_TIMEOUT = Duration.ofSeconds(5L);
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

    public Duration getTimeout() {
        return DEFAULT_LOADING_TIMEOUT;
    }

    @Override
    protected String getUri() {
        return "/login";
    }
}
