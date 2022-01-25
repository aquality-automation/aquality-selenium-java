package aquality.selenium.browser.devtools;

import aquality.selenium.configuration.IBrowserProfile;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;

public class DevToolsFactory {

    public static DevTools getDevTools(WebDriver driver, IBrowserProfile browserProfile) {
        DevTools tools;
        try {
            tools = ((HasDevTools)driver).getDevTools();
        } catch (ClassCastException e) {
            throw new IllegalArgumentException(String.format("Browser [%s] is not supported.", browserProfile.getBrowserName()));
        }
        return tools;
    }
}
