package aquality.selenium.browser.devtools;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.localization.ILocalizedLogger;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.devtools.v96.network.Network;
import org.openqa.selenium.devtools.v96.network.model.RequestWillBeSent;
import org.openqa.selenium.devtools.v96.network.model.ResponseReceived;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.http.Route;

import java.net.URI;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static aquality.selenium.browser.AqualityServices.getBrowser;

public class NetworkTools {

    private final DevTools tools;
    private final ILocalizedLogger localizedLogger = AqualityServices.getLocalizedLogger();

    public NetworkTools(DevTools tools) {
        this.tools = tools;
    }

    public void addBasicAuthentication(String hostPart, String username, String password) {
        localizedLogger.info("loc.browser.network.authentication.add");
        Predicate<URI> uriPredicate = uri -> uri.getHost().contains(hostPart);
        tools.getDomains().network().addAuthHandler(uriPredicate, UsernameAndPassword.of(username, password));
    }

    public void clearBasicAuthentication() {
        localizedLogger.info("loc.browser.network.authentication.clear");
        tools.getDomains().network().disable();
    }

    public void addRequestHandler(Consumer<RequestWillBeSent> handler) {
        localizedLogger.info("loc.browser.network.event.requestsent.add");
        tools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        tools.addListener(Network.requestWillBeSent(), handler);
    }

    public void addResponseHandler(Consumer<ResponseReceived> handler) {
        localizedLogger.info("loc.browser.network.event.responsereceived.add");
        tools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        tools.addListener(Network.responseReceived(), handler);
    }

    public void clearHandlers() {
        tools.clearListeners();
    }

    public NetworkInterceptor startNetworkInterceptor(HttpResponse response) {
        return new NetworkInterceptor(
                getBrowser().getDriver(),
                Route.matching(req -> true)
                        .to(() -> req -> response));
    }

    public void closeNetworkInterceptor(NetworkInterceptor interceptor) {
        interceptor.close();
    }
}
