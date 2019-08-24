package aquality.selenium.localization;

import aquality.selenium.utils.JsonFile;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.testng.Assert.*;

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
    public void testGetValueShouldReturnValueFromSelectedLanguage(SupportedLanguage language, String value) throws NoSuchFieldException, IllegalAccessException, IOException {
        Field fieldLocalManager = LocalizationManager.class.getDeclaredField(fieldLocalManagerName);
        fieldLocalManager.setAccessible(true);

        JsonFile jsonFileRu = new JsonFile(String.format(tmpDicFilePath, language.name().toLowerCase()));
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