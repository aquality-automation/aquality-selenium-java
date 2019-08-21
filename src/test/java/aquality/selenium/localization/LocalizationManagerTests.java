package aquality.selenium.localization;

import aquality.selenium.utils.JsonFile;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Field;
import java.util.Objects;

import static org.testng.Assert.*;

public class LocalizationManagerTests {

    private final String fieldLocalManagerName = "localManager";
    private final String tmpDicFilePath = "localization/%1$s.json";
    private final String key = "loc.clicking";
    private final File dicFolder = new File(Objects.requireNonNull(LocalizationManagerTests.class.getClassLoader().getResource("localization")).getPath());
    private final File renamedDicFolder = new File(dicFolder.getPath() + "Renamed");

    @BeforeGroups("requireLocalizationRemove")
    private void renameLocalizationFolder() {
        dicFolder.renameTo(renamedDicFolder);
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

    @Test(groups = "requireLocalizationRemove", priority = -1, expectedExceptions = { UncheckedIOException.class} )
    public void testShouldGetExceptionIfLocalizationFileIsNotExists() {
        LocalizationManager.getInstance();
    }

    @AfterGroups("requireLocalizationRemove")
    private void renameLocalizationFolderToInitial() {
        renamedDicFolder.renameTo(dicFolder);
    }

}