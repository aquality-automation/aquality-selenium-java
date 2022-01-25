package tests.usecases.devtools;

import aquality.selenium.browser.AqualityServices;
import forms.MyLocationForm;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseTest;

import static automationpractice.Constants.URL_MYLOCATIONORG;

public class OverrideGeolocationTest extends BaseTest {

    private static final double LAT_FOR_OVERRIDE = 53.90772672521578;
    private static final double LNG_FOR_OVERRIDE = 27.458060411865375;

    @Test
    public void overrideGeolocationTest() {

        AqualityServices.getBrowser().goTo(URL_MYLOCATIONORG);
        MyLocationForm form = new MyLocationForm();
        Assert.assertTrue(form.state().waitForDisplayed());
        double latDefault = form.getLatitude();
        double lngDefault = form.getLongitude();

        AqualityServices.getBrowser().devTools().emulation().setGeolocationOverride(LAT_FOR_OVERRIDE, LNG_FOR_OVERRIDE);
        AqualityServices.getBrowser().refresh();

        Assert.assertEquals(form.getLatitude(), LAT_FOR_OVERRIDE);
        Assert.assertEquals(form.getLongitude(), LNG_FOR_OVERRIDE);

        AqualityServices.getBrowser().devTools().emulation().clearGeolocationOverride();
        AqualityServices.getBrowser().refresh();

        Assert.assertEquals(form.getLatitude(), latDefault);
        Assert.assertEquals(form.getLongitude(), lngDefault);
    }
}
