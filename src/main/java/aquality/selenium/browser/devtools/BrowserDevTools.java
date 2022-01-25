package aquality.selenium.browser.devtools;

import aquality.selenium.configuration.IBrowserProfile;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;

public class BrowserDevTools {

    DevTools tools;

    public BrowserDevTools(WebDriver driver, IBrowserProfile browserProfile) {
        tools = DevToolsFactory.getDevTools(driver, browserProfile);
        tools.createSessionIfThereIsNotOne();
    }

    public EmulationTools emulation() {
        return new EmulationTools(tools);
    }

    public NetworkTools network() {
        return new NetworkTools(tools);
    }
}
