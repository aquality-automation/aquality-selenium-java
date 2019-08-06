package aquality.selenium.localization;

import aquality.selenium.configuration.PropertiesResourceManager;

public class LocalizationManager {

    private static final String LOC_DICT_PATH = "log/localization/%1$s.properties";
    private static final PropertiesResourceManager LANG_PROPS = new PropertiesResourceManager("log/loglang.properties");
    private static final String LOCALE_KEY = "logger.lang";
    private final PropertiesResourceManager localManager;

    private static ThreadLocal<LocalizationManager> instance = ThreadLocal.withInitial(LocalizationManager::new);

    private LocalizationManager(){
        SupportedLocale locale = SupportedLocale.valueOf(LANG_PROPS.getProperty(LOCALE_KEY, SupportedLocale.EN.name()));
        localManager = new PropertiesResourceManager(String.format(LOC_DICT_PATH, locale.name().toLowerCase()));
    }

    public static LocalizationManager getInstance() {
        return instance.get();
    }

    public String getValue(final String key) {
        return localManager.getProperty(key, key);
    }
}
