package tests.usecases.devtools;

import aquality.selenium.browser.AlertActions;
import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.devtools.EmulationHandling;
import org.openqa.selenium.NoAlertPresentException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.BaseTest;
import theinternet.TheInternetPage;
import theinternet.forms.JavaScriptAlertsForm;

import java.util.Collections;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public class EmulationTests extends BaseTest {

    private static EmulationHandling emulation() {
        return AqualityServices.getBrowser().devTools().emulation();
    }

    @Override
    @BeforeMethod
    protected void beforeMethod() {
    }

    @Test
    public void setScriptExecutionDisabledAndEnableAgainTest() {
        AqualityServices.getBrowser().goTo(TheInternetPage.JAVASCRIPT_ALERTS.getAddress());
        JavaScriptAlertsForm alertsForm = new JavaScriptAlertsForm();
        alertsForm.getBtnJsAlert().click();
        AqualityServices.getBrowser().handleAlert(AlertActions.ACCEPT);

        emulation().setScriptExecutionDisabled();
        alertsForm.getBtnJsAlert().click();
        Assert.assertThrows(NoAlertPresentException.class, () -> AqualityServices.getBrowser().handleAlert(AlertActions.ACCEPT));

        emulation().setScriptExecutionDisabled(false);
        alertsForm.getBtnJsAlert().click();
        AqualityServices.getBrowser().handleAlert(AlertActions.ACCEPT);
    }

    @Test
    public void setTouchEmulationEnabledAndDisabledTest() {
        BooleanSupplier isTouchEnabled = () -> getScriptResultOrDefault("isTouchEnabled.js", false);
        if (isTouchEnabled.getAsBoolean()) {
            throw new IllegalStateException("Touch should be initially disabled");
        }
        emulation().setTouchEmulationEnabled();
        Assert.assertTrue(isTouchEnabled.getAsBoolean(), "Touch should be enabled");
        emulation().setTouchEmulationEnabled(false);
        Assert.assertFalse(isTouchEnabled.getAsBoolean(), "Touch should be disabled");
    }

    @Test
    public void setEmulatedMediaTest() {
        final String emulatedMedia = "projection";
        final String width = "600";
        Supplier<String> getMediaType = () -> getScriptResultOrDefault("getMediaType.js", "");
        String initialValue = getMediaType.get();
        if (initialValue.contains(emulatedMedia)) {
            throw new IllegalStateException("Initial media type should differ from value to be set");
        }
        emulation().setEmulatedMedia(emulatedMedia, Collections.singletonMap("width", width));
        Assert.assertEquals(getMediaType.get(), emulatedMedia, "Media type should equal to emulated");
        emulation().disableEmulatedMediaOverride();
        Assert.assertEquals(getMediaType.get(), initialValue, "Media type should equal to initial after disabling the override");
        emulation().setEmulatedMedia(Collections.singletonMap("media", emulatedMedia));
        Assert.assertEquals(getMediaType.get(), emulatedMedia, "Media type should equal to emulated");
    }

    @Test
    public void settingDefaultBackgroundColorOverrideDoesNotThrowTest() {
        emulation().setDefaultBackgroundColorOverride(0, 255, 38);
        emulation().clearDefaultBackgroundColorOverride();
    }
}
