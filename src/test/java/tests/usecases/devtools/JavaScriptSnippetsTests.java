package tests.usecases.devtools;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.JavaScript;
import aquality.selenium.browser.devtools.JavaScriptHandling;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.ScriptKey;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseTest;
import theinternet.forms.FormAuthenticationForm;
import theinternet.forms.WelcomeForm;

public class JavaScriptSnippetsTests extends BaseTest {
    private static JavaScriptHandling javaScriptEngine() {
        return AqualityServices.getBrowser().javaScriptEngine();
    }

    @Test
    public void testPinScriptAndUnpinIt() {
        String script = JavaScript.GET_ELEMENT_XPATH.getScript();
        WelcomeForm welcomeForm = new WelcomeForm();
        ScriptKey pinnedScript = javaScriptEngine().pinScript(script);
        Assert.assertTrue(javaScriptEngine().getPinnedScripts().contains(pinnedScript), "Should be possible to read pinned scripts");
        getBrowser().goTo(welcomeForm.getUrl());
        String xpath = (String) welcomeForm.getSubTitleLabel().getJsActions().executeScript(pinnedScript);
        Assert.assertTrue(StringUtils.isNotEmpty(xpath), "Pinned script should be possible to execute");
        String expectedValue = welcomeForm.getSubTitleLabel().getJsActions().getXPath();
        Assert.assertEquals(xpath, expectedValue, "Pinned script should return the same value");

        javaScriptEngine().unpinScript(pinnedScript);
        Assert.assertThrows(JavascriptException.class,
                () -> welcomeForm.getSubTitleLabel().getJsActions().executeScript(pinnedScript));
        javaScriptEngine().reset();
    }

    @Test
    public void testPinScriptWithoutReturnedValueAndUnpinAll() {
        String valueToSet = "username";
        String script = JavaScript.SET_VALUE.getScript();
        ScriptKey pinnedScript = javaScriptEngine().pinScript(script);

        FormAuthenticationForm authenticationForm = new FormAuthenticationForm();
        getBrowser().goTo(authenticationForm.getUrl());
        getBrowser().waitForPageToLoad();

        authenticationForm.getTxbUsername().getJsActions().executeScript(pinnedScript, valueToSet);
        Assert.assertEquals(authenticationForm.getTxbUsername().getValue(), valueToSet,
                String.format("Text should be '%s' after setting value via pinned JS", valueToSet));

        javaScriptEngine().clearPinnedScripts();
        Assert.assertThrows(JavascriptException.class,
                () -> authenticationForm.getTxbUsername().getJsActions().executeScript(pinnedScript));
        javaScriptEngine().reset();
    }
}
