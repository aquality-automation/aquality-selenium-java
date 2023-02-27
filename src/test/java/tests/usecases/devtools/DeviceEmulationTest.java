package tests.usecases.devtools;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.devtools.EmulationHandling;
import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.devtools.v110.emulation.Emulation;
import org.openqa.selenium.devtools.v110.emulation.model.DisplayFeature;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.BaseTest;
import theinternet.TheInternetPage;

import java.util.Optional;
import java.util.function.Supplier;

public class DeviceEmulationTest extends BaseTest {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 1000;
    private static final boolean IS_MOBILE = true;
    private static final double SCALE_FACTOR = 50;
    private static final DisplayFeature DISPLAY_FEATURE = new DisplayFeature(DisplayFeature.Orientation.HORIZONTAL, 0, 0);

    @Override
    @BeforeMethod
    protected void beforeMethod() {
        getBrowser().maximize();
    }

    private static EmulationHandling emulation() {
        return AqualityServices.getBrowser().devTools().emulation();
    }

    @Test
    public void setAndClearDeviceMetricsOverrideTest() {
        checkDeviceMetricsOverride(() -> emulation().setDeviceMetricsOverride(
                WIDTH, HEIGHT, SCALE_FACTOR, IS_MOBILE));
    }

    @Test
    public void setAndClearDeviceMetricsOverrideWithVersionSpecificParametersTest() {
        checkDeviceMetricsOverride(() -> {
            AqualityServices.getBrowser().devTools().sendCommand(Emulation.setDeviceMetricsOverride(
                    WIDTH, HEIGHT, SCALE_FACTOR, IS_MOBILE, Optional.empty(), Optional.empty(), Optional.empty(),
                    Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                    Optional.of(DISPLAY_FEATURE)
            ));
            AqualityServices.getLogger().info("success");
        });
    }

    @Test
    public void setAndClearDeviceMetricsOverrideWithParametersMapTest() {
        ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
        params.put("width", WIDTH);
        params.put("height", HEIGHT);
        params.put("deviceScaleFactor", SCALE_FACTOR);
        params.put("mobile", IS_MOBILE);
        params.put("displayFeature", DISPLAY_FEATURE);
        checkDeviceMetricsOverride(() -> emulation().setDeviceMetricsOverride(params.build()));
    }

    private void checkDeviceMetricsOverride(Runnable setAction) {
        Supplier<Long> getWindowHeight = () -> getScriptResultOrDefault("getWindowSize.js", 0L);
        final int initialValue = getWindowHeight.get().intValue();
        if (initialValue == HEIGHT) {
            throw new IllegalArgumentException("To check that override works, initial value should differ from the new one");
        }
        setAction.run();
        navigate(TheInternetPage.TABLES);
        Assert.assertEquals(getWindowHeight.get(), HEIGHT, "Browser height should match to override value");
        emulation().clearDeviceMetricsOverride();
        AqualityServices.getConditionalWait().waitFor(() -> {
                AqualityServices.getBrowser().refresh();
                return getWindowHeight.get() == initialValue;
        });

        Assert.assertEquals(getWindowHeight.get(), initialValue, "Browser height should match to initial value after clear");
    }
}
