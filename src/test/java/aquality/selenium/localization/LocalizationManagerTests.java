package aquality.selenium.localization;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Field;

import static org.testng.Assert.assertEquals;

public class LocalizationManagerTests {

    private static final String fieldLocalManagerName = "localManager";
    private static final String tmpDicFilePath = "localization/%1$s.json";
    private static final String key = "loc.clicking";
    private static final String languageKey = "logger.language";


    @BeforeGroups("unsupportedLanguage")
    private void renameLocalizationFolder() {
        System.setProperty(languageKey, "UnsupportedLanguage");
    }

    @DataProvider
    private Object[][] languagesAndValues() {
        return new Object[][]{
                {SupportedLanguage.RU, "Клик"},
                {SupportedLanguage.EN, "Clicking"}
        };
    }

    @Test
    public void testGetInstanceShouldReturnSameObjectEachTime() {
        assertEquals(LocalizationManager.getInstance(), LocalizationManager.getInstance());
    }

    @Test(dataProvider = "languagesAndValues")
    public void testGetValueShouldReturnValueFromSelectedLanguage(SupportedLanguage language, String value) throws NoSuchFieldException, IllegalAccessException {
        Field fieldLocalManager = LocalizationManager.class.getDeclaredField(fieldLocalManagerName);
        fieldLocalManager.setAccessible(true);

        ISettingsFile jsonFileRu = new JsonSettingsFile(String.format(tmpDicFilePath, language.name().toLowerCase()));
        fieldLocalManager.set(LocalizationManager.getInstance(), jsonFileRu);
        assertEquals(LocalizationManager.getInstance().getValue(key), value);
    }

    @Test(groups = "unsupportedLanguage", priority = -1, expectedExceptions = { IllegalArgumentException.class} )
    public void testShouldGetExceptionIfLocalizationFileIsNotExists() {
        LocalizationManager.getInstance();
    }

    @AfterGroups("unsupportedLanguage")
    private void renameLocalizationFolderToInitial() {
        System.clearProperty(languageKey);
    }

}