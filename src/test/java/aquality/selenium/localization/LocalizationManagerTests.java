package aquality.selenium.localization;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.localization.ILocalizationManager;
import aquality.selenium.core.localization.LocalizationManager;
import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Field;

import static org.testng.Assert.assertEquals;

public class LocalizationManagerTests {

    private static final String fieldLocalManagerName = "localizationFile";
    private static final String tmpDicFilePath = "localization/%1$s.json";
    private static final String key = "loc.clicking";

    @DataProvider
    private Object[][] languagesAndValues() {
        return new Object[][]{
                {"ru", "Клик"},
                {"en", "Clicking"}
        };
    }

    @Test
    public void testGetInstanceShouldReturnSameObjectEachTime() {
        assertEquals(AqualityServices.get(ILocalizationManager.class), AqualityServices.get(ILocalizationManager.class));
    }

    @Test(dataProvider = "languagesAndValues")
    public void testGetValueShouldReturnValueFromSelectedLanguage(String language, String value) throws NoSuchFieldException, IllegalAccessException {
        Field fieldLocalManager = LocalizationManager.class.getDeclaredField(fieldLocalManagerName);
        fieldLocalManager.setAccessible(true);

        ISettingsFile jsonFile = new JsonSettingsFile(String.format(tmpDicFilePath, language));
        fieldLocalManager.set(AqualityServices.get(ILocalizationManager.class), jsonFile);
        assertEquals(AqualityServices.get(ILocalizationManager.class).getLocalizedMessage(key), value);
    }

    @Test
    public void testShouldGetExceptionIfLocalizationFileIsNotExists() {
        Assert.assertThrows(IllegalArgumentException.class, () -> getLocalizationManager("invalid").getLocalizedMessage(key));
    }

    private LocalizationManager getLocalizationManager(String language) {
        return new LocalizationManager(() -> language, Logger.getInstance());
    }
}