package aquality.selenium.logging;

import static aquality.selenium.browser.AqualityServices.getLocalizedLogger;

/**
 * Wrapper over the {@link aquality.selenium.core.localization.ILocalizedLogger}.
 */
public class LocalizedLoggerUtility {
    private LocalizedLoggerUtility() throws InstantiationException {
        throw new InstantiationException("Utility class");
    }

    /**
     * Logging of localized messages with a specific log level.
     * @param logLevel logging level.
     * @param messageKey localized message key.
     * @param args arguments for the localized message.
     */
    public static void logByLevel(LogLevel logLevel, String messageKey, Object... args) {
        switch (logLevel) {
            case DEBUG: {
                getLocalizedLogger().debug(messageKey, args);
                break;
            }
            case INFO: {
                getLocalizedLogger().info(messageKey, args);
                break;
            }
            case WARN: {
                getLocalizedLogger().warn(messageKey, args);
                break;
            }
            case ERROR: {
                getLocalizedLogger().error(messageKey, args);
                break;
            }
            case FATAL: {
                getLocalizedLogger().fatal(messageKey, new Throwable(), args);
                break;
            }
            default:
                throw new IllegalArgumentException(String.format("Localized logging at level [ %s] is not supported.", logLevel));
        }        
    }
}
