package aquality.selenium.browser;

import aquality.selenium.configuration.IBrowserProfile;
import aquality.selenium.configuration.IConfiguration;
import aquality.selenium.core.localization.ILocalizedLogger;
import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.waitings.IConditionalWait;
import aquality.selenium.elements.interfaces.IElementFactory;
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
        return getInstance().getApp(injector -> AqualityServices.startBrowser());
    }

    /**
     * Resolves required service from DI container.
     * Note that the service should be binded in {@link BrowserModule}.
     *
     * @param type class of required service.
     * @param <T> type of required service.
     * @return required service.
     */
    public static <T> T get(Class<T> type) {
        return getServiceProvider().getInstance(type);
    }

    private static Injector getServiceProvider() {
        return getInstance().getInjector();
    }

    /**
     * Sets default(local {@link LocalBrowserFactory} or remote {@link RemoteBrowserFactory}) browser factory.
     */
    public static void setDefaultBrowserFactory(){
        IBrowserFactory browserFactory = getBrowserProfile().isRemote()
                ? new RemoteBrowserFactory() : new LocalBrowserFactory();
        setBrowserFactory(browserFactory);
    }

    /**
     * Sets custom browser factory.
     * @param browserFactory Custom implementation of {@link IBrowserFactory}
     */
    public static void setBrowserFactory(IBrowserFactory browserFactory) {
        if((factoryContainer).get() != null) {
            factoryContainer.remove();
        }
        AqualityServices.factoryContainer.set(browserFactory);
    }

    private static Browser startBrowser() {
        if(factoryContainer.get() == null){
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
        if((INSTANCE_CONTAINER).get() != null){
            INSTANCE_CONTAINER.remove();
        }
        INSTANCE_CONTAINER.set(new AqualityServices(module));
    }

    /**
     * Gets logger.
     *
     * @return instance of logger.
     */
    public static Logger getLogger() {
        return get(Logger.class);
    }

    /**
     * Gets logger which logs messages in current language.
     *
     * @return instance of localized logger.
     */
    public static ILocalizedLogger getLocalizedLogger() {
        return get(ILocalizedLogger.class);
    }

    /**
     * Provides the utility used to wait for some condition.
     *
     * @return instance of conditional wait.
     */
    public static IConditionalWait getConditionalWait() {
        return get(IConditionalWait.class);
    }

    /**
     * Gets factory to create elements.
     *
     * @return Factory of elements.
     */
    public static IElementFactory getElementFactory() {
        return get(IElementFactory.class);
    }

    /**
     * Gets current profile of browser settings.
     *
     * @return current browser profile.
     */
    public static IBrowserProfile getBrowserProfile() {
        return get(IBrowserProfile.class);
    }

    /**
     * Provides interface to access all described configurations.
     *
     * @return configuration.
     */
    public static IConfiguration getConfiguration() {
        return get(IConfiguration.class);
    }
}
