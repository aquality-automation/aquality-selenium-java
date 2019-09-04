package aquality.selenium.localization;

import aquality.selenium.configuration.ILoggerConfiguration;
import aquality.selenium.configuration.guice.ConfigurationModule;
import aquality.selenium.utils.JsonFile;
import com.google.inject.Guice;

public class LocalizationManager {

    private final JsonFile localManager;
    private static ThreadLocal<LocalizationManager> instance = ThreadLocal.withInitial(LocalizationManager::new);

    private LocalizationManager(){
        SupportedLanguage language = Guice.createInjector(new ConfigurationModule())
                .getInstance(ILoggerConfiguration.class).getLanguage();
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
