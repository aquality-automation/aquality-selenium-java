package tests.usecases.devtools;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.devtools.DevToolsHandling;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseTest;
import theinternet.TheInternetPage;

import java.util.Map;

public class PerformanceMetricsTest extends BaseTest {
    private static DevToolsHandling devTools() {
        return AqualityServices.getBrowser().devTools();
    }

    @Test
    public void getAndCloseDevToolsSessionTest() {
        devTools().enablePerformanceMonitoring();
        navigate(TheInternetPage.REDIRECTOR);
        Map<String, Number> metrics = devTools().getPerformanceMetrics();
        Assert.assertFalse(metrics.isEmpty(), "Some metrics should be returned");
        devTools().disablePerformanceMonitoring();
        getBrowser().refresh();
        Assert.assertTrue(devTools().getPerformanceMetrics().isEmpty(),
                "Metrics should have not been collected after performance monitoring have been disabled");
        devTools().enablePerformanceMonitoring("timeTicks");
        getBrowser().refresh();
        metrics = devTools().getPerformanceMetrics();
        Assert.assertFalse(metrics.isEmpty(), "Some metrics should be returned");
        getBrowser().refresh();
        Map<String, Number> otherMetrics = devTools().getPerformanceMetrics();
        Assert.assertFalse(otherMetrics.isEmpty(), "Some metrics should be returned");
        Assert.assertNotEquals(otherMetrics, metrics, "Some additional metrics should have been collected");
    }
}
