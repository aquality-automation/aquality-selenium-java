package aquality.selenium.browser.devtools;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.localization.ILocalizedLogger;
import org.openqa.selenium.Credentials;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.devtools.v85.network.model.RequestWillBeSent;
import org.openqa.selenium.devtools.v85.network.model.ResponseReceived;
import org.openqa.selenium.remote.http.*;
import org.openqa.selenium.devtools.idealized.Network;

import java.net.URI;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static aquality.selenium.browser.AqualityServices.getBrowser;
import static org.openqa.selenium.devtools.v85.network.Network.*;

public class NetworkHandling {
    private final DevTools tools;
    private final Network<?, ?> network;
    private final ILocalizedLogger localizedLogger = AqualityServices.getLocalizedLogger();

    public NetworkHandling(DevTools tools) {
        this.tools = tools;
        this.network = tools.getDomains().network();
    }

    public void disable() {
        network.disable();
    }

    public void setUserAgent(String userAgent) {
        network.setUserAgent(userAgent);
    }

    public void setUserAgent(Network.UserAgent userAgent) {
        network.setUserAgent(userAgent);
    }

    public void addAuthHandler(Predicate<URI> whenThisMatches, Supplier<Credentials> useTheseCredentials) {
        localizedLogger.info("loc.browser.network.authentication.add");
        network.addAuthHandler(whenThisMatches, useTheseCredentials);
    }

    public void resetNetworkFilter() {
        network.resetNetworkFilter();
    }

    public void interceptTrafficWith(Filter filter) {
        network.interceptTrafficWith(filter);
    }

    public void prepareToInterceptTraffic() {
        network.prepareToInterceptTraffic();
    }

    public void addBasicAuthentication(String hostPart, String username, String password) {
        Predicate<URI> uriPredicate = uri -> uri.getHost().contains(hostPart);
        addAuthHandler(uriPredicate, UsernameAndPassword.of(username, password));
    }

    public void clearBasicAuthentication() {
        localizedLogger.info("loc.browser.network.authentication.clear");
        disable();
        prepareToInterceptTraffic();
    }

    public void addRequestHandler(Consumer<RequestWillBeSent> handler) {
        localizedLogger.info("loc.browser.network.event.requestsent.add");
        tools.send(enable(Optional.empty(), Optional.empty(), Optional.empty()));
        tools.addListener(requestWillBeSent(), handler);
    }

    public void addResponseHandler(Consumer<ResponseReceived> handler) {
        localizedLogger.info("loc.browser.network.event.responsereceived.add");
        tools.send(enable(Optional.empty(), Optional.empty(), Optional.empty()));
        tools.addListener(responseReceived(), handler);
    }

    public void clearHandlers() {
        tools.clearListeners();
    }

    public NetworkInterceptor interceptAllRequests(HttpResponse response) {
        return startNetworkInterceptor(req -> true, () -> req -> response);
    }

    public NetworkInterceptor startNetworkInterceptor(Predicate<HttpRequest> requestMatcher, Supplier<HttpHandler> handler) {
        return new NetworkInterceptor(
                getBrowser().getDriver(),
                Route.matching(requestMatcher)
                        .to(handler));
    }

    public void closeNetworkInterceptor(NetworkInterceptor interceptor) {
        interceptor.close();
    }
}
