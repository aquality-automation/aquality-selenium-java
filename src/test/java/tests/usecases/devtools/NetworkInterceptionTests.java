package tests.usecases.devtools;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.devtools.NetworkHandling;
import com.google.common.net.MediaType;
import manytools.RequestHeadersForm;
import org.apache.hc.core5.http.HttpStatus;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.remote.http.HttpHandler;
import org.openqa.selenium.remote.http.HttpRequest;
import org.openqa.selenium.remote.http.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.BaseTest;
import theinternet.TheInternetPage;
import theinternet.forms.WelcomeForm;

import java.io.ByteArrayInputStream;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static org.openqa.selenium.remote.http.Contents.utf8String;

public class NetworkInterceptionTests extends BaseTest {

    private static final String SOME_PHRASE = "Creamy, delicious cheese!";

    private static NetworkHandling network() {
        return AqualityServices.getBrowser().network();
    }

    @Override
    @BeforeMethod
    protected void beforeMethod() {
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

        getBrowser().network().clearNetworkInterceptor();
        getBrowser().goTo(welcomeForm.getUrl());
        Assert.assertFalse(getBrowser().getDriver().getPageSource().contains(SOME_PHRASE));
    }

    @Test
    public void testRequestsInterception() {
        WelcomeForm welcomeForm = new WelcomeForm();
        NetworkInterceptor interceptor = network().startNetworkInterceptor((HttpHandler) request -> new HttpResponse()
                .setStatus(HttpStatus.SC_OK)
                .addHeader("Content-Type", MediaType.HTML_UTF_8.toString())
                .setContent(utf8String(SOME_PHRASE)));
        Assert.assertNotNull(interceptor, "Network interceptor must not be null");
        getBrowser().goTo(welcomeForm.getUrl());
        Assert.assertTrue(getBrowser().getDriver().getPageSource().contains(SOME_PHRASE));

        getBrowser().network().clearNetworkInterceptor();
        getBrowser().goTo(welcomeForm.getUrl());
        Assert.assertFalse(getBrowser().getDriver().getPageSource().contains(SOME_PHRASE));
    }

    @Test
    public void testRequestsFilter() {
        final String paramName = "Test";
        final String paramValue = "delicious cheese!";
        Function<HttpRequest, HttpRequest> requestTransformer = request -> {
            request.addHeader(paramName, paramValue);
            return request;
        };
        network().interceptTrafficWith(next -> req -> next.execute(requestTransformer.apply(req)));
        Assert.assertEquals(new RequestHeadersForm().open().getNullableValue(paramName), paramValue, "Request should be modified");
        network().resetNetworkFilter();
        Assert.assertNotEquals(new RequestHeadersForm().open().getNullableValue(paramName), paramValue, "Request should not be modified");
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
    public void testAddAndClearRequestTransformer()
    {
        final String paramName = "Test";
        final String paramValue = "delicious cheese!";
        NetworkInterceptor networkInterceptor = network().addRequestTransformer(request -> true, request -> {
            request.addHeader(paramName, paramValue);
            return request;
        });
        Assert.assertNotNull(networkInterceptor, "Created network interceptor must not be null");
        Assert.assertEquals(new RequestHeadersForm().open().getNullableValue(paramName), paramValue, "Request should be modified");
        network().clearNetworkInterceptor();
        Assert.assertNotEquals(new RequestHeadersForm().open().getNullableValue(paramName), paramValue, "Request should not be modified");
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
        Assert.assertEquals(counter.get(), oldValue, "Should be possible to clear event listeners");
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
        Assert.assertEquals(counter.get(), oldValue, "Should be possible to clear event listeners");
    }
}
