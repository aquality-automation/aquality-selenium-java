package aquality.selenium.localization;

import aquality.selenium.configuration.Configuration;
import aquality.selenium.utils.JsonFile;

import java.io.IOException;
import java.io.UncheckedIOException;

public class LocalizationManager {

    private final JsonFile localManager;
    private static ThreadLocal<LocalizationManager> instance = ThreadLocal.withInitial(LocalizationManager::new);

    private LocalizationManager(){
        SupportedLanguage language = Configuration.getInstance().getLoggerConfiguration().getLanguage();
        String translateDictFile = String.format("localization/%1$s.json", language.name().toLowerCase());
        try{
            localManager = new JsonFile(translateDictFile);
        }catch (IOException e){
            throw new UncheckedIOException("LocalizationManager was not instantiated. Localtization file was not found by path " + translateDictFile, e);
        }
    }

    public static LocalizationManager getInstance() {
        return instance.get();
    }

    public String getValue(final String key) {
        return localManager.getValue("/" + key).toString();
    }
}
