package tests.usecases.devtools;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.devtools.EmulationHandling;
import manytools.BrowserLanguageForm;
import manytools.UserAgentForm;
import org.openqa.selenium.devtools.idealized.Network;
import org.openqa.selenium.devtools.v108.emulation.Emulation;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.BaseTest;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class OverrideUserAgentTest extends BaseTest {

    private static final String CUSTOM_USER_AGENT = "Mozilla/5.0 (iPhone; CPU iPhone OS 6_1_4 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10B350 Safari/8536.25";
    private static final String CUSTOM_ACCEPT_LANGUAGE = "be-BY";

    private static EmulationHandling emulation() {
        return AqualityServices.getBrowser().devTools().emulation();
    }

    @BeforeMethod
    @Override
    protected void beforeMethod() {
        String defaultUserAgent = new UserAgentForm().open().getValue();
        if (CUSTOM_USER_AGENT.equals(defaultUserAgent)) {
            throw new IllegalStateException("Default user agent header should be different from the custom one to check override");
        }
    }

    @Test
    public void overrideUserAgentTest() {
        emulation().setUserAgentOverride(CUSTOM_USER_AGENT);
        Assert.assertEquals(new UserAgentForm().open().getValue(), CUSTOM_USER_AGENT, "User agent should match to value set");
    }

    @Test
    public void overrideUserAgentViaNetworkTest() {
        AqualityServices.getBrowser().network().setUserAgent(CUSTOM_USER_AGENT);
        Assert.assertEquals(new UserAgentForm().open().getValue(), CUSTOM_USER_AGENT, "User agent should match to value set");
    }

    @Test
    public void overrideUserAgentViaNetworkModelTest() {
        AqualityServices.getBrowser().network().setUserAgent(new Network.UserAgent(CUSTOM_USER_AGENT));
        Assert.assertEquals(new UserAgentForm().open().getValue(), CUSTOM_USER_AGENT, "User agent should match to value set");
    }

    @Test
    public void overrideUserAgentByParametersMapTest() {
        Map<String, Object> params = Collections.singletonMap("userAgent", CUSTOM_USER_AGENT);
        emulation().setUserAgentOverride(params);
        Assert.assertEquals(new UserAgentForm().open().getValue(), CUSTOM_USER_AGENT, "User agent should match to value set");
    }

    @Test
    public void overrideUserAgentByVersionSpecificCommandTest() {
        AqualityServices.getBrowser().devTools().sendCommand(Emulation.setUserAgentOverride(CUSTOM_USER_AGENT,
                Optional.of(CUSTOM_ACCEPT_LANGUAGE), Optional.empty(),
                Optional.empty()));
        Assert.assertEquals(new UserAgentForm().open().getValue(), CUSTOM_USER_AGENT, "User agent should match to value set");
    }

    @Test
    public void overrideUserAgentByCdpCommandTest() {
        Map<String, Object> params = Collections.singletonMap("userAgent", CUSTOM_USER_AGENT);
        AqualityServices.getBrowser().devTools().executeCdpCommand("Emulation.setUserAgentOverride", params);
        Assert.assertEquals(new UserAgentForm().open().getValue(), CUSTOM_USER_AGENT, "User agent should match to value set");
    }

    @Test
    public void overrideUserAgentAndLanguageTest() {
        String defaultLanguage = new BrowserLanguageForm().open().getValue();
        String defaultUserAgent = new UserAgentForm().open().getValue();
        if (defaultLanguage.contains(CUSTOM_ACCEPT_LANGUAGE)) {
            throw new IllegalStateException("Default accept-language header should be different from the custom one to check override");
        }
        if (CUSTOM_USER_AGENT.equals(defaultUserAgent)) {
            throw new IllegalStateException("Default user agent header should be different from the custom one to check override");
        }

        emulation().setUserAgentOverride(CUSTOM_USER_AGENT, Optional.of(CUSTOM_ACCEPT_LANGUAGE), Optional.empty());
        Assert.assertTrue(new BrowserLanguageForm().open().getValue().contains(CUSTOM_ACCEPT_LANGUAGE), "Accept-language header should match to value set");
        Assert.assertEquals(new UserAgentForm().open().getValue(), CUSTOM_USER_AGENT, "User agent should match to value set");
    }
}
