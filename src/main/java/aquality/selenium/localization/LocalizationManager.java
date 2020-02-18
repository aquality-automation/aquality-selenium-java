package aquality.selenium.localization;

import aquality.selenium.configuration.Configuration;
import aquality.selenium.utils.JsonFile;

public class LocalizationManager {

    private final JsonFile localManager;
    private static ThreadLocal<LocalizationManager> instance = ThreadLocal.withInitial(LocalizationManager::new);

    private LocalizationManager(){
        SupportedLanguage language = SupportedLanguage.valueOf(Configuration.getInstance().getLoggerConfiguration().getLanguage().toUpperCase());
        String translateDictFile = String.format("localization/%1$s.json", language.name().toLowerCase());
        localManager = new JsonFile(translateDictFile);
    }

    public static LocalizationManager getInstance() {
        return instance.get();
    }

    public String getValue(final String key) {
        return localManager.getValue("/" + key).toString();
    }
}
