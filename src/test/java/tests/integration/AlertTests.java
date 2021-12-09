package tests.integration;

import aquality.selenium.browser.AlertActions;
import aquality.selenium.browser.AqualityServices;
import org.openqa.selenium.NoAlertPresentException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.BaseTest;
import theinternet.TheInternetPage;
import theinternet.forms.JavaScriptAlertsForm;

public class AlertTests extends BaseTest {

    private final JavaScriptAlertsForm alertsForm = new JavaScriptAlertsForm();

    @BeforeMethod
    @Override
    public void beforeMethod() {
        AqualityServices.getBrowser().goTo(TheInternetPage.JAVASCRIPT_ALERTS.getAddress());
    }

    @Test
    public void testShouldBePossibleToAcceptAlert() {
        alertsForm.getBtnJsAlert().click();
        getBrowser().handleAlert(AlertActions.ACCEPT);
        Assert.assertEquals(alertsForm.getLblResult().getText(), "You successfully clicked an alert");
    }

    @Test
    public void testShouldBePossibleToAcceptConfirmationAlert() {
        alertsForm.getBtnJsConfirm().click();
        getBrowser().handleAlert(AlertActions.ACCEPT);
        Assert.assertEquals(alertsForm.getLblResult().getText(), "You clicked: Ok");
    }

    @Test
    public void testShouldBePossibleToDeclineConfirmationAlert() {
        alertsForm.getBtnJsConfirm().click();
        getBrowser().handleAlert(AlertActions.DECLINE);
        Assert.assertEquals(alertsForm.getLblResult().getText(), "You clicked: Cancel");
    }

    @Test
    public void testShouldBePossibleToAcceptPromptAlertWithText() {
        alertsForm.getBtnJsPrompt().click();
        String text = String.valueOf(System.currentTimeMillis());
        getBrowser().handlePromptAlert(AlertActions.ACCEPT, text);
        Assert.assertEquals(alertsForm.getLblResult().getText(), String.format("You entered: %s", text));
    }

    @Test
    public void testShouldBePossibleToDeclinePromptAlertWithText() {
        alertsForm.getBtnJsPrompt().click();
        getBrowser().handlePromptAlert(AlertActions.DECLINE, "Hello");
        Assert.assertEquals(alertsForm.getLblResult().getText(), String.format("You entered: %s", "null"));
    }

    @Test(expectedExceptions = NoAlertPresentException.class)
    public void testNoAlertExceptionShouldBeThrownIfNotAlertPresent(){
        getBrowser().handleAlert(AlertActions.DECLINE);
    }

    @Test(expectedExceptions = NoAlertPresentException.class)
    public void testNoAlertExceptionShouldBeThrownIfNotPromptsPresent(){
        getBrowser().handlePromptAlert(AlertActions.DECLINE, "Hello");
    }
}
