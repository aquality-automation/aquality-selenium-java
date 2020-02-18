package aquality.selenium.browser;

import aquality.selenium.configuration.Configuration;
import aquality.selenium.configuration.IConfiguration;
import com.google.inject.Injector;

/**
 * Controls browser and Aquality services.
 */
public class AqualityServices extends aquality.selenium.core.applications.AqualityServices<Browser> {
    private static final ThreadLocal<AqualityServices> INSTANCE_CONTAINER = ThreadLocal.withInitial(AqualityServices::new);
    private static final ThreadLocal<IBrowserFactory> factoryContainer = new ThreadLocal<>();

    private AqualityServices(){
        super(AqualityServices::getBrowser, () -> new BrowserModule(AqualityServices::getBrowser));
    }

    private AqualityServices(BrowserModule module){
        super(AqualityServices::getBrowser, () -> module);
    }

    private static AqualityServices getInstance() {
        return INSTANCE_CONTAINER.get();
    }

    /**
     * Gets instance of browser.
     * @return Instance of desired browser.
     */
    public static Browser getBrowser(){
        return getInstance().getApp(AqualityServices::startBrowser);
    }

    /**
     * Sets default(local {@link LocalBrowserFactory} or remote {@link RemoteBrowserFactory}) browser factory.
     */
    public static void setDefaultBrowserFactory(){
        IConfiguration configuration = Configuration.getInstance();
        IBrowserFactory browserFactory = Configuration.getInstance().getBrowserProfile().isRemote()
                ? new RemoteBrowserFactory(configuration) : new LocalBrowserFactory(configuration);
        setBrowserFactory(browserFactory);
    }

    /**
     * Sets custom browser factory.
     * @param browserFactory Custom implementation of {@link IBrowserFactory}
     */
    public static void setBrowserFactory(IBrowserFactory browserFactory) {
        remove(factoryContainer);
        AqualityServices.factoryContainer.set(browserFactory);
    }

    private static Browser startBrowser(Injector injector) {
        if(factoryContainer.get() == null){
            // todo: pass this parameter into the next method
            setDefaultBrowserFactory();
        }

        return factoryContainer.get().getBrowser();
    }

    /**
     * Sets instance of browser.
     * @param browser Instance of desired browser.
     */
    public static void setBrowser(Browser browser) {
        getInstance().setApp(browser);
    }

    /**
     * Reinitializes the dependency injector with custom {@link BrowserModule}.
     *
     * @param module {@link BrowserModule} object with custom or overriden services.
     */
    public static void initInjector(BrowserModule module) {
        remove(INSTANCE_CONTAINER);
        INSTANCE_CONTAINER.set(new AqualityServices(module));
    }

    private static void remove(ThreadLocal<?> container){
        if(container.get() != null){
            container.remove();
        }
    }
}
