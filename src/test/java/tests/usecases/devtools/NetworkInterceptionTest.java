package tests.usecases.devtools;

import com.google.common.net.MediaType;
import org.apache.hc.core5.http.HttpStatus;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.remote.http.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseTest;
import theinternet.forms.WelcomeForm;

import static org.openqa.selenium.remote.http.Contents.utf8String;

public class NetworkInterceptionTest extends BaseTest {

    private static final String SOME_PHRASE = "Creamy, delicious cheese!";

    @Test
    public void testAllRequestsInterception() {
        WelcomeForm welcomeForm = new WelcomeForm();

        NetworkInterceptor interceptor = getBrowser().network().interceptAllRequests(new HttpResponse()
                .setStatus(HttpStatus.SC_OK)
                .addHeader("Content-Type", MediaType.HTML_UTF_8.toString())
                .setContent(utf8String(SOME_PHRASE)));
        Assert.assertNotNull(interceptor, "Network interceptor must not be null");
        getBrowser().goTo(welcomeForm.getUrl());
        Assert.assertTrue(getBrowser().getDriver().getPageSource().contains(SOME_PHRASE));

        getBrowser().devTools().network().clearNetworkInterceptor();
        getBrowser().goTo(welcomeForm.getUrl());
        Assert.assertFalse(getBrowser().getDriver().getPageSource().contains(SOME_PHRASE));
    }
}
