package tests.integration;

import aquality.selenium.browser.AlertActions;
import aquality.selenium.browser.BrowserManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.BaseTest;
import theinternet.TheInternetPage;
import theinternet.forms.JavaScriptAlertsForm;

public class AlertTests extends BaseTest {

    @BeforeMethod
    @Override
    public void beforeMethod() {
        BrowserManager.getBrowser().goTo(TheInternetPage.JAVASCRIPT_ALERTS.getAddress());
    }

    @Test
    public void testAcceptAlert() {
        JavaScriptAlertsForm alertsForm = new JavaScriptAlertsForm();
        alertsForm.clickJsAlertBtn();
        BrowserManager.getBrowser().handleAlert(AlertActions.ACCEPT);
        Assert.assertEquals(alertsForm.getResultText(), "You successfuly clicked an alert");
    }

    @Test
    public void testAcceptConfirmationAlert() {
        JavaScriptAlertsForm alertsForm = new JavaScriptAlertsForm();
        alertsForm.clickJsConfirmBtn();
        BrowserManager.getBrowser().handleAlert(AlertActions.ACCEPT);
        Assert.assertEquals(alertsForm.getResultText(), "You clicked: Ok");
    }

    @Test
    public void testDeclineConfirmationAlert() {
        JavaScriptAlertsForm alertsForm = new JavaScriptAlertsForm();
        alertsForm.clickJsConfirmBtn();
        BrowserManager.getBrowser().handleAlert(AlertActions.DECLINE);
        Assert.assertEquals(alertsForm.getResultText(), "You clicked: Cancel");
    }

    @Test
    public void testAcceptPromptAlertWithText() {
        JavaScriptAlertsForm alertsForm = new JavaScriptAlertsForm();
        alertsForm.clickJsPromptBtn();
        String text = String.valueOf(System.currentTimeMillis());
        BrowserManager.getBrowser().handlePromptAlert(AlertActions.ACCEPT, text);
        Assert.assertEquals(alertsForm.getResultText(), String.format("You entered: %s", text));
    }

    @Test
    public void testDeclinePromptAlertWithText() {
        JavaScriptAlertsForm alertsForm = new JavaScriptAlertsForm();
        alertsForm.clickJsPromptBtn();
        BrowserManager.getBrowser().handlePromptAlert(AlertActions.DECLINE, "Hello");
        Assert.assertEquals(alertsForm.getResultText(), String.format("You entered: %s", "null"));
    }
}
