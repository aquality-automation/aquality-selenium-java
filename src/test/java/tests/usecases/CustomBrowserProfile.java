package tests.usecases;

import aquality.selenium.configuration.BrowserProfile;
import aquality.selenium.configuration.driversettings.ChromeSettings;
import aquality.selenium.configuration.driversettings.IDriverSettings;
import aquality.selenium.core.utilities.ISettingsFile;
import com.google.inject.Inject;
import org.openqa.selenium.InvalidArgumentException;

import java.io.File;
import java.io.IOException;

public class CustomBrowserProfile extends BrowserProfile {
    private static final String downloadDirInitialized = "./target/downloads_custom/";

    static String getDownloadDirInitialized() {
        return downloadDirInitialized;
    }

    private final ISettingsFile settingsFile;

    @Inject
    public CustomBrowserProfile(ISettingsFile settingsFile) {
        super(settingsFile);
        this.settingsFile = settingsFile;
    }

    @Override
    public boolean isRemote() {
        return false;
    }

    @Override
    public IDriverSettings getDriverSettings() {
        return new CustomChromeSettings(settingsFile);
    }

    private class CustomChromeSettings extends ChromeSettings {

        CustomChromeSettings(ISettingsFile jsonFile) {
            super(jsonFile);
        }

        @Override
        public String getDownloadDir() {
            try {
                return new File(downloadDirInitialized).getCanonicalPath();
            } catch (IOException e) {
                throw new InvalidArgumentException(e.getMessage());
            }
        }
    }
}
