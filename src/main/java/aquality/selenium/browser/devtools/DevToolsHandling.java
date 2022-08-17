package aquality.selenium.browser.devtools;

import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;

public class DevToolsHandling {
    private final HasDevTools devToolsProvider;
    private DevTools session;

    public DevToolsHandling(HasDevTools devToolsProvider) {
        this.devToolsProvider = devToolsProvider;
    }

    private DevTools getDevTools() {
        if (session == null) {
            session = devToolsProvider.getDevTools();
        }
        return session;
    }

    public DevTools getSession() {
        getDevTools().createSessionIfThereIsNotOne();
        return session;
    }

    public DevTools getSession(String windowHandle) {
        getDevTools().createSessionIfThereIsNotOne(windowHandle);
        return session;
    }

    public EmulationTools emulation() {
        return new EmulationTools(getSession());
    }

    public NetworkHandling network() {
        return new NetworkHandling(getSession());
    }
}
