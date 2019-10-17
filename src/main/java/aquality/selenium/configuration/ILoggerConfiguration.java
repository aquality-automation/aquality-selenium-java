package aquality.selenium.configuration;

import aquality.selenium.localization.SupportedLanguage;

/**
 * Describes logger configuration.
 */
public interface ILoggerConfiguration {

    /**
     * Gets language which will be used for framework logger.
     * @return Supported language.
     */
    SupportedLanguage getLanguage();
}
