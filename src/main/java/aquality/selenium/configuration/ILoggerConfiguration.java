package aquality.selenium.configuration;

import aquality.selenium.localization.SupportedLanguage;

/**
 * Describes logger configuration.
 */
public interface ILoggerConfiguration {

    /**
     * Gets language of framework.
     * @return Supported language.
     */
    SupportedLanguage getLanguage();
}
