package aquality.selenium.localization;

import aquality.selenium.configuration.Configuration;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;

public class LocalizationManager {

    private final ISettingsFile localManager;
    private static ThreadLocal<LocalizationManager> instance = ThreadLocal.withInitial(LocalizationManager::new);

    private LocalizationManager(){
        SupportedLanguage language = SupportedLanguage.valueOf(Configuration.getInstance().getLoggerConfiguration().getLanguage().toUpperCase());
        String translateDictFile = String.format("localization/%1$s.json", language.name().toLowerCase());
        localManager = new JsonSettingsFile(translateDictFile);
    }

    public static LocalizationManager getInstance() {
        return instance.get();
    }

    public String getValue(final String key) {
        return localManager.getValue("/" + key).toString();
    }
}
