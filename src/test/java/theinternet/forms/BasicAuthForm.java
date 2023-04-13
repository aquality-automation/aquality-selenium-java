package theinternet.forms;

import aquality.selenium.elements.interfaces.ILabel;
import org.openqa.selenium.By;

public class BasicAuthForm extends TheInternetForm {

    private static final String USER_AND_PASS= "admin";
    private static final String DOMAIN = "the-internet.herokuapp.com";

    private final ILabel lblCongratulationsLabel = getElementFactory().getLabel(By.xpath("//p[contains(., 'Congratulations')]"), "Congratulations");

    public BasicAuthForm() {
        super(By.xpath("//h3[contains(text(),'Basic Auth')]"), "Basic Auth");
    }

    public boolean isCongratulationsDisplayed() {
        return lblCongratulationsLabel.state().isDisplayed();
    }

    @Override
    protected String getUri() {
        return "/basic_auth";
    }

    public static String getUsername() {
        return USER_AND_PASS;
    }

    public static String getPassword() {
        return USER_AND_PASS;
    }

    public static String getDomain() {
        return DOMAIN;
    }
}
