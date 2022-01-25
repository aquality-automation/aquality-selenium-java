package tests.usecases.devtools;

import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseTest;
import theinternet.forms.BasicAuthForm;

public class RegisterBasicAuthTest extends BaseTest {

    @Test
    public void setBasicAuth() {
        BasicAuthForm basicAuthForm = new BasicAuthForm();
        getBrowser().devTools().network().addBasicAuthentication(BasicAuthForm.getDomain(), BasicAuthForm.getUsername(), BasicAuthForm.getPassword());
        getBrowser().goTo(basicAuthForm.getUrl());
        Assert.assertTrue(basicAuthForm.isCongratulationsDisplayed());
    }

    @Test
    public void clearBasicAuth() {
        BasicAuthForm basicAuthForm = new BasicAuthForm();
        getBrowser().devTools().network().addBasicAuthentication(BasicAuthForm.getDomain(), BasicAuthForm.getUsername(), BasicAuthForm.getPassword());
        getBrowser().devTools().network().clearBasicAuthentication();
        getBrowser().goTo(basicAuthForm.getUrl());
        Assert.assertFalse(basicAuthForm.isCongratulationsDisplayed());
    }
}
