package aquality.selenium.configuration;

import aquality.selenium.utils.JsonFile;

import java.io.IOException;
import java.io.UncheckedIOException;

public class Configuration implements IConfiguration{

    private static ThreadLocal<Configuration> instance = ThreadLocal.withInitial(Configuration::new);
    private final ITimeoutConfiguration timeoutConfiguration;
    private final IBrowserProfile browserProfile;

    private Configuration() {
        JsonFile settings = getSettings();
        timeoutConfiguration = new TimeoutConfiguration(settings);
        browserProfile = new BrowserProfile(settings);
    }

    public static Configuration getInstance() {
        return instance.get();
    }

    @Override
    public IBrowserProfile getBrowserProfile() {
       return browserProfile;
    }

    @Override
    public ITimeoutConfiguration getTimeoutConfiguration() {
        return timeoutConfiguration;
    }

    private JsonFile getSettings() {
        String settingsProfile = System.getProperty("profile") == null ? "settings.json" : "settings." + System.getProperty("profile") + ".json";
        try{
            return new JsonFile(settingsProfile);
        }catch (IOException e){
            throw new UncheckedIOException(String.format("Browser profile assigned in file %1$s was not found in the root of resources directory", settingsProfile), e);
        }
    }
}
