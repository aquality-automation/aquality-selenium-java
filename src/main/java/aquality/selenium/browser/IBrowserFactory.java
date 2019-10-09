package aquality.selenium.browser;

/**
 * Factory that creates instance of desired Browser based on {@link aquality.selenium.configuration.IConfiguration}
 */
public interface IBrowserFactory {

    /**
     * Gets instance of Browser.
     * @return Instance of desired Browser.
     */
    Browser getBrowser();
}
