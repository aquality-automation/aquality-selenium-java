package aquality.selenium.browser.devtools;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.localization.ILocalizedLogger;
import org.openqa.selenium.Credentials;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.devtools.idealized.Network;
import org.openqa.selenium.devtools.v85.network.model.RequestWillBeSent;
import org.openqa.selenium.devtools.v85.network.model.ResponseReceived;
import org.openqa.selenium.remote.http.*;

import java.net.URI;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static aquality.selenium.browser.AqualityServices.getBrowser;
import static org.openqa.selenium.devtools.v85.network.Network.*;

/**
 * DevTools commands for version-independent network interception.
 * For more information, see {@link org.openqa.selenium.devtools.v85.network.Network} and {@link Network}.
 */
public class NetworkHandling {
    private final DevToolsHandling tools;
    private final Network<?, ?> network;
    private final ILocalizedLogger logger = AqualityServices.getLocalizedLogger();

    /**
     * Initializes a new instance of the {@link NetworkHandling} class.
     *
     * @param tools Instance of {@link DevToolsHandling}.
     */
    public NetworkHandling(DevToolsHandling tools) {
        this.tools = tools;
        this.network = tools.getDevToolsSession().getDomains().network();
    }

    /**
     * Starts network monitoring.
     */
    public void prepareToInterceptTraffic() {
        logger.info("loc.browser.network.monitoring.start");
        network.prepareToInterceptTraffic();
    }

    /**
     * Stops network monitoring.
     */
    public void disable() {
        logger.info("loc.browser.network.monitoring.stop");
        network.disable();
    }

    /**
     * Overrides the values of user agent.
     * @param userAgent User agent to use.
     */
    public void setUserAgent(String userAgent) {
        logger.info("loc.browser.network.useragent.set", userAgent);
        network.setUserAgent(userAgent);
    }

    /**
     * Overrides the values of user agent.
     * @param userAgent User agent to use.
     */
    public void setUserAgent(Network.UserAgent userAgent) {
        logger.info("loc.browser.network.useragent.set", userAgent);
        network.setUserAgent(userAgent);
    }

    /**
     * Add basic authentication handler.
     * @param useTheseCredentials parameters, such as URI matcher and credentials.
     */
    public void addAuthHandler(Predicate<URI> whenThisMatches, Supplier<Credentials> useTheseCredentials) {
        logger.info("loc.browser.network.authentication.add");
        network.addAuthHandler(whenThisMatches, useTheseCredentials);
    }

    /**
     * Add basic authentication handler.
     * @param hostPart part of the host name for URI matcher.
     * @param username authentication username.
     * @param password authentication password.
     */
    public void addBasicAuthentication(String hostPart, String username, String password) {
        Predicate<URI> uriPredicate = uri -> uri.getHost().contains(hostPart);
        addAuthHandler(uriPredicate, UsernameAndPassword.of(username, password));
    }

    /**
     * Clears basic authentication handler.
     */
    public void clearBasicAuthentication() {
        logger.info("loc.browser.network.authentication.clear");
        disable();
    }

    /**
     * Clears the most recent network filter.
     */
    public void resetNetworkFilter() {
        logger.info("loc.browser.network.filter.clear");
        network.resetNetworkFilter();
    }

    /**
     * Starts traffic interception with specified filter.
     * @param filter HTTP filter.
     */
    public void interceptTrafficWith(Filter filter) {
        logger.info("loc.browser.network.filter.set");
        network.interceptTrafficWith(filter);
    }

    /**
     * Adds listener to network request sent event.
     * @param listener a listener to add.
     */
    public void addRequestListener(Consumer<RequestWillBeSent> listener) {
        logger.info("loc.browser.network.event.requestsent.add");
        tools.sendCommand(enable(Optional.empty(), Optional.empty(), Optional.empty()));
        tools.addListener(requestWillBeSent(), listener);
    }

    /**
     * Adds listener to network response received event.
     * @param listener a listener to add.
     */
    public void addResponseListener(Consumer<ResponseReceived> listener) {
        logger.info("loc.browser.network.event.responsereceived.add");
        tools.sendCommand(enable(Optional.empty(), Optional.empty(), Optional.empty()));
        tools.addListener(responseReceived(), listener);
    }

    /**
     * Clears network event listeners.
     */
    public void clearListeners() {
        tools.clearListeners();
    }

    /**
     * Starts network interceptor.
     * @param httpHandler HTTP handler.
     * @return an instance of {@link NetworkInterceptor}.
     */
    public NetworkInterceptor startNetworkInterceptor(HttpHandler httpHandler) {
        logger.info("loc.browser.network.interceptor.start");
        return new NetworkInterceptor(getBrowser().getDriver(), httpHandler);
    }

    /**
     * Starts network interceptor.
     * @param filter network filter.
     * @return an instance of {@link NetworkInterceptor}.
     */
    public NetworkInterceptor startNetworkInterceptor(Filter filter) {
        logger.info("loc.browser.network.interceptor.start");
        return new NetworkInterceptor(getBrowser().getDriver(), filter);
    }

    /**
     * Starts network interceptor.
     * @param routable a filter with matcher.
     * @return an instance of {@link NetworkInterceptor}.
     */
    public NetworkInterceptor startNetworkInterceptor(Routable routable) {
        logger.info("loc.browser.network.interceptor.start");
        return new NetworkInterceptor(getBrowser().getDriver(), routable);
    }

    /**
     * Starts network interceptor.
     * @param requestMatcher predicate to match the request.
     * @param handler handler for matched requests.
     * @return an instance of {@link NetworkInterceptor}.
     */
    public NetworkInterceptor startNetworkInterceptor(Predicate<HttpRequest> requestMatcher, Supplier<HttpHandler> handler) {
        return startNetworkInterceptor(Route.matching(requestMatcher).to(handler));
    }

    /**
     * Intercepts any request with predefined response.
     * @param response HTTP response.
     * @return an instance of {@link NetworkInterceptor}.
     */
    public NetworkInterceptor interceptAllRequests(HttpResponse response) {
        return startNetworkInterceptor(req -> true, () -> req -> response);
    }

    /**
     * Adds request transformer.
     * @param requestMatcher predicate to match the request.
     * @param requestTransformer function to transform the request.
     * @return an instance of {@link NetworkInterceptor}.
     */
    public NetworkInterceptor addRequestTransformer(Predicate<HttpRequest> requestMatcher, Function<HttpRequest, HttpRequest> requestTransformer) {
        return startNetworkInterceptor((Filter) next -> req ->
                requestMatcher.test(req) ? next.execute(requestTransformer.apply(req)) : next.execute(req));
    }

    /**
     * Adds request handler.
     * @param requestMatcher predicate to match the request.
     * @param requestHandler handler for matched requests.
     * @return an instance of {@link NetworkInterceptor}.
     */
    public NetworkInterceptor addRequestHandler(Predicate<HttpRequest> requestMatcher, Function<HttpRequest, HttpResponse> requestHandler) {
        return startNetworkInterceptor(
                Route.matching(requestMatcher).to(() -> requestHandler::apply));
    }

    /**
     * Adds response handler.
     * @param responseMatcher predicate to match the response.
     * @param responseTransformer function to transform the response.
     * @return an instance of {@link NetworkInterceptor}.
     */
    public NetworkInterceptor addResponseHandler(Predicate<HttpResponse> responseMatcher, Function<HttpResponse, HttpResponse> responseTransformer) {
        return startNetworkInterceptor(
                (Filter) next -> req -> {
                    HttpResponse response = next.execute(req);
                    return responseMatcher.test(response) ? responseTransformer.apply(response) : response;
                });
    }

    /**
     * Clears the latest network interceptor.
     * Currently, Selenium supports only a single network interceptor. Any new Network interceptor will override the previous one.
     * And on {@link NetworkInterceptor#close()} the NetworkInterceptor class just calls the {@link Network#resetNetworkFilter()}, so it's enough to call it.
     * If multiple network interceptors at the same time would be allowed, we may want to store all
     *  {@link NetworkInterceptor} classes to call {@link NetworkInterceptor::close()} for each.
     */
    public void clearNetworkInterceptor() {
        resetNetworkFilter();
    }
}
