package tests.integration;

import aquality.selenium.localization.LocalizationManager;
import aquality.selenium.localization.SupportedLanguage;
import aquality.selenium.utils.JsonFile;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.lang.reflect.Field;

public class LocalizationManagerTests {

    private final String fieldLocalManagerName = "localManager";
    private final String tmpDicFilePath = "localization/%1$s.json";
    private final String key = "loc.clicking";
    private final String expectedMessageRu = "Клик";
    private final String expectedMessageEn = "Clicking";

    @Test
    public void testShouldGetPrintRussianKeys() throws IllegalAccessException, NoSuchFieldException, IOException {
        Field fieldLocalManager = LocalizationManager.class.getDeclaredField(fieldLocalManagerName);
        fieldLocalManager.setAccessible(true);
        JsonFile langFile = (JsonFile) fieldLocalManager.get(LocalizationManager.getInstance());

        SoftAssert softAssert = new SoftAssert();
        JsonFile jsonFileRu = new JsonFile(String.format(tmpDicFilePath, SupportedLanguage.RU.name().toLowerCase()));
        fieldLocalManager.set(LocalizationManager.getInstance(), jsonFileRu);
        softAssert.assertEquals(LocalizationManager.getInstance().getValue(key), expectedMessageRu);

        JsonFile jsonFileEn = new JsonFile(String.format(tmpDicFilePath, SupportedLanguage.EN.name().toLowerCase()));
        fieldLocalManager.set(LocalizationManager.getInstance(), jsonFileEn);
        softAssert.assertEquals(LocalizationManager.getInstance().getValue(key), expectedMessageEn);

        fieldLocalManager.set(LocalizationManager.getInstance(), langFile);
        softAssert.assertAll();
    }
}
