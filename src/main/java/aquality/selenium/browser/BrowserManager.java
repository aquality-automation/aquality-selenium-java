package aquality.selenium.browser;

import aquality.selenium.configuration.Configuration;
import aquality.selenium.configuration.IConfiguration;

/**
 * Controls browser instance creation.
 */
public class BrowserManager {
    private static final ThreadLocal<Browser> browserContainer = new ThreadLocal<>();
    private static final ThreadLocal<IBrowserFactory> factoryContainer = new ThreadLocal<>();

    private BrowserManager(){
    }

    /**
     * Gets instance of browser.
     * @return Instance of desired browser.
     */
    public static Browser getBrowser(){
        if(browserContainer.get() == null || browserContainer.get().getDriver().getSessionId() == null) {
            setDefaultBrowser();
        }
        return browserContainer.get();
    }

    /**
     * Sets default(local {@link LocalBrowserFactory} or remote {@link RemoteBrowserFactory}) browser factory.
     */
    public static void setDefaultFactory(){
        IConfiguration configuration = Configuration.getInstance();
        IBrowserFactory browserFactory = Configuration.getInstance().getBrowserProfile().isRemote()
                ? new RemoteBrowserFactory(configuration) : new LocalBrowserFactory(configuration);
        setFactory(browserFactory);
    }

    /**
     * Sets custom browser factory.
     * @param browserFactory Custom implementation of {@link IBrowserFactory}
     */
    public static void setFactory(IBrowserFactory browserFactory){
        remove(factoryContainer);
        BrowserManager.factoryContainer.set(browserFactory);
    }

    private static void setDefaultBrowser(){
        if(factoryContainer.get() == null){
            setDefaultFactory();
        }
        setBrowser(factoryContainer.get().getBrowser());
    }

    /**
     * Sets instance of browser.
     * @param browser Instance of desired browser.
     */
    public static void setBrowser(Browser browser){
        remove(browserContainer);
        BrowserManager.browserContainer.set(browser);
    }

    private static void remove(ThreadLocal<?> container){
        if(container.get() != null){
            container.remove();
        }
    }
}
