package aquality.selenium.configuration;

import aquality.selenium.localization.SupportedLanguage;
import aquality.selenium.utils.JsonFile;

public class LoggerConfiguration implements ILoggerConfiguration{

    private final JsonFile settingsFile;

    public LoggerConfiguration(JsonFile settingsFile){
        this.settingsFile = settingsFile;
    }

    @Override
    public SupportedLanguage getLanguage() {
        return SupportedLanguage.valueOf(settingsFile.getValue("/logger/language").toString().toUpperCase());
    }
}
