package aquality.selenium.browser.devtools;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.localization.ILocalizedLogger;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v96.emulation.Emulation;

import java.util.Optional;

public class EmulationTools {

    private final DevTools tools;
    private final ILocalizedLogger localizedLogger = AqualityServices.getLocalizedLogger();

    public EmulationTools(DevTools tools) {
        this.tools = tools;
    }


    public void setGeolocationOverride(double lat, double lng, int accuracy) {
        localizedLogger.info("loc.browser.emulation.geolocation.set");
        tools.send(Emulation.setGeolocationOverride(Optional.of(lat), Optional.of(lng), Optional.of(accuracy)));
    }

    public void setGeolocationOverride(double lat, double lng) {
        localizedLogger.info("loc.browser.emulation.geolocation.set");
        setGeolocationOverride(lat, lng, 0);
    }

    public void clearGeolocationOverride() {
        localizedLogger.info("loc.browser.emulation.geolocation.clear");
        tools.send(Emulation.clearGeolocationOverride());
    }
}
