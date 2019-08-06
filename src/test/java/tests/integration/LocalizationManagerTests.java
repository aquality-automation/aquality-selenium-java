package tests.integration;

import aquality.selenium.configuration.PropertiesResourceManager;
import aquality.selenium.localization.LocalizationManager;
import aquality.selenium.localization.SupportedLocale;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Field;

public class LocalizationManagerTests {

    private static final String FIELD_LOCAL_MANAGER = "localManager";
    private static final String FIELD_LOCAL_DICT_PATH = "LOC_DICT_PATH";

    @Test
    public void testShouldGetPrintRussianKeys() throws IllegalAccessException, NoSuchFieldException {
        String expectedMessage = "Клик";
        String key = "loc.clicking";
        Field fieldLocalManager = LocalizationManager.class.getDeclaredField(FIELD_LOCAL_MANAGER);
        Field fieldLocalDictPath = LocalizationManager.class.getDeclaredField(FIELD_LOCAL_DICT_PATH);
        fieldLocalManager.setAccessible(true);
        fieldLocalDictPath.setAccessible(true);
        String localDictPath = (String) fieldLocalDictPath.get(LocalizationManager.getInstance());
        PropertiesResourceManager originalLocalManager = (PropertiesResourceManager) fieldLocalManager.get(LocalizationManager.getInstance());
        fieldLocalManager.set(LocalizationManager.getInstance(), new PropertiesResourceManager(String.format(localDictPath, SupportedLocale.RU.name().toLowerCase())));
        String actualMessage = LocalizationManager.getInstance().getValue(key);
        fieldLocalManager.set(LocalizationManager.getInstance(), originalLocalManager);
        Assert.assertEquals(actualMessage, expectedMessage, String.format("LocalizationManager got wrong Russian value for key '%s'.", key));
    }
}
