package tests.usecases.devtools;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.devtools.NetworkHandling;
import com.google.common.net.MediaType;
import org.apache.hc.core5.http.HttpStatus;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.remote.http.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseTest;
import theinternet.TheInternetPage;
import theinternet.forms.WelcomeForm;

import java.io.ByteArrayInputStream;
import java.util.concurrent.atomic.AtomicInteger;

import static org.openqa.selenium.remote.http.Contents.utf8String;

public class NetworkInterceptionTests extends BaseTest {

    private static final String SOME_PHRASE = "Creamy, delicious cheese!";

    private static NetworkHandling network() {
        return AqualityServices.getBrowser().network();
    }

    @Test
    public void testAllRequestsInterception() {
        WelcomeForm welcomeForm = new WelcomeForm();

        NetworkInterceptor interceptor = network().interceptAllRequests(new HttpResponse()
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

    @Test
    public void testAddAndClearRequestHandler()
    {
        final String somePhrase = "delicious cheese!";
        NetworkInterceptor networkInterceptor = network().addRequestHandler(request -> true, request -> {
            HttpResponse response = new HttpResponse();
            response.setContent(() -> new ByteArrayInputStream(somePhrase.getBytes()));
            response.setStatus(200);
            return response;
        });
        Assert.assertNotNull(networkInterceptor, "Created network interceptor must not be null");

        navigate(TheInternetPage.LOGIN);
        Assert.assertTrue(getBrowser().getDriver().getPageSource().contains(somePhrase), "Request should be intercepted");
        network().clearNetworkInterceptor();
        navigate(TheInternetPage.LOGIN);
        Assert.assertFalse(getBrowser().getDriver().getPageSource().contains(somePhrase), "Request should not be intercepted");
    }

    @Test
    public void testAddAndClearResponseHandler()
    {
        final String somePhrase = "delicious cheese!";
        NetworkInterceptor networkInterceptor = network().addResponseHandler(response -> true, oldResponse -> {
            HttpResponse response = new HttpResponse();
            response.setContent(() -> new ByteArrayInputStream(somePhrase.getBytes()));
            response.setStatus(200);
            return response;
        });
        Assert.assertNotNull(networkInterceptor, "Created network interceptor must not be null");

        navigate(TheInternetPage.LOGIN);
        Assert.assertTrue(getBrowser().getDriver().getPageSource().contains(somePhrase), "Request should be intercepted");
        network().clearNetworkInterceptor();
        navigate(TheInternetPage.LOGIN);
        Assert.assertFalse(getBrowser().getDriver().getPageSource().contains(somePhrase), "Request should not be intercepted");
    }

    @Test
    public void testSubscribeToRequestSentEventAndUnsubscribeFromEvents() {
        navigate(TheInternetPage.LOGIN);
        AtomicInteger counter = new AtomicInteger();
        network().addRequestListener(requestWillBeSent -> counter.incrementAndGet());
        navigate(TheInternetPage.LOGIN);
        Assert.assertTrue(counter.get() > 0, "Should be possible to listen to Request Sent events");
        int oldValue = counter.get();
        network().clearListeners();
        navigate(TheInternetPage.LOGIN);
        Assert.assertEquals(oldValue, counter.get(), "Should be possible to clear event listeners");
    }

    @Test
    public void testSubscribeToResponseReceivedEventAndUnsubscribeFromEvents() {
        navigate(TheInternetPage.LOGIN);
        AtomicInteger counter = new AtomicInteger();
        network().addResponseListener(responseReceived -> counter.incrementAndGet());
        navigate(TheInternetPage.LOGIN);
        Assert.assertTrue(counter.get() > 0, "Should be possible to listen to Response Received events");
        int oldValue = counter.get();
        network().clearListeners();
        navigate(TheInternetPage.LOGIN);
        Assert.assertEquals(oldValue, counter.get(), "Should be possible to clear event listeners");
    }
}
