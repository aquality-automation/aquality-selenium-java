package aquality.selenium.browser.devtools;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.localization.ILocalizedLogger;
import aquality.selenium.logging.HttpExchangeLoggingOptions;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Credentials;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.devtools.idealized.Network;
import org.openqa.selenium.devtools.v142.network.model.*;
import org.openqa.selenium.remote.http.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static aquality.selenium.browser.AqualityServices.getBrowser;
import static aquality.selenium.logging.LocalizedLoggerUtility.logByLevel;
import static org.openqa.selenium.devtools.v142.network.Network.*;

/**
 * DevTools commands for version-independent network interception.
 * For more information, see {@link org.openqa.selenium.devtools.v142.network.Network} and {@link Network}.
 */
public class NetworkHandling {
    public static final String LOC_NETWORK_INTERCEPTOR_START = "loc.browser.network.interceptor.start";
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
    public void startMonitoring() {
        logger.info("loc.browser.network.monitoring.start");
        network.prepareToInterceptTraffic();
    }

    /**
     * Stops network monitoring.
     */
    public void stopMonitoring() {
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
     * @param whenThisMatches URI matcher.
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
        stopMonitoring();
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
        tools.sendCommand(enable(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));
        tools.addListener(requestWillBeSent(), listener);
    }

    /**
     * Adds listener to network response received event.
     * @param listener a listener to add.
     */
    public void addResponseListener(Consumer<ResponseReceived> listener) {
        logger.info("loc.browser.network.event.responsereceived.add");
        tools.sendCommand(enable(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));
        tools.addListener(responseReceived(), listener);
    }

    /**
     * Clears network event listeners.
     */
    public void clearListeners() {
        tools.clearListeners();
    }

    /**
     * Enables HTTP Request/Response logging with default {@link HttpExchangeLoggingOptions}.
     */
    public void enableHttpExchangeLogging() {
        enableHttpExchangeLogging(new HttpExchangeLoggingOptions());
    }

    private String formatHeaders(Headers headers) {
        List<String> formattedHeaders = new ArrayList<>();
        headers.forEach((key, value) -> formattedHeaders.add(String.format("%s\t%s: %s", System.lineSeparator(), key, value)));
        return String.join(",", formattedHeaders);
    }

    private Consumer<RequestWillBeSent> getRequestLogger(HttpExchangeLoggingOptions loggingOptions) {
        return requestWillBeSent -> {
            Request request = requestWillBeSent.getRequest();
            if (loggingOptions.getRequestInfo().isEnabled()) {
                logByLevel(loggingOptions.getRequestInfo().getLogLevel(),
                        "loc.browser.network.event.requestsent.log.info",
                        request.getMethod(), request.getUrl() + request.getUrlFragment().orElse(""), requestWillBeSent.getRequestId());
            }
            if (loggingOptions.getRequestHeaders().isEnabled() && !request.getHeaders().isEmpty()) {
                logByLevel(loggingOptions.getRequestHeaders().getLogLevel(),
                        "loc.browser.network.event.requestsent.log.headers",
                        formatHeaders(request.getHeaders()));
            }
            if (loggingOptions.getRequestPostData().isEnabled() && request.getHasPostData().orElse(false)) {
                logByLevel(loggingOptions.getRequestPostData().getLogLevel(),
                        "loc.browser.network.event.requestsent.log.data",
                        request.getPostDataEntries().orElse(null));
            }
        };
    }

    private Consumer<ResponseReceived> getResponseLogger(HttpExchangeLoggingOptions loggingOptions) {
        return responseReceived -> {
            Response response = responseReceived.getResponse();
            RequestId requestId = responseReceived.getRequestId();
            if (loggingOptions.getResponseInfo().isEnabled()) {
                logByLevel(loggingOptions.getResponseInfo().getLogLevel(),
                        "loc.browser.network.event.responsereceived.log.info",
                        response.getStatus(), response.getUrl(), responseReceived.getType().toString(), requestId);
            }
            if (loggingOptions.getResponseHeaders().isEnabled() && !response.getHeaders().isEmpty()) {
                logByLevel(loggingOptions.getResponseHeaders().getLogLevel(),
                        "loc.browser.network.event.responsereceived.log.headers",
                        formatHeaders(response.getHeaders()));
            }
            if (loggingOptions.getResponseBody().isEnabled()) {
                String responseBody = tools.sendCommand(org.openqa.selenium.devtools.v142.network.Network.getResponseBody(requestId)).getBody();
                if (StringUtils.isNotEmpty(responseBody)) {
                    logByLevel(loggingOptions.getResponseBody().getLogLevel(),
                            "loc.browser.network.event.responsereceived.log.body",
                            responseBody);
                }
            }
        };
    }

    /**
     * Enables HTTP Request/Response logging.
     * @param loggingOptions logging parameters {@link HttpExchangeLoggingOptions}.
     */
    public void enableHttpExchangeLogging(HttpExchangeLoggingOptions loggingOptions) {
        addRequestListener(getRequestLogger(loggingOptions));
        addResponseListener(getResponseLogger(loggingOptions));
    }

    /**
     * Starts network interceptor.
     * @param httpHandler HTTP handler.
     * @return an instance of {@link NetworkInterceptor}.
     */
    public NetworkInterceptor startNetworkInterceptor(HttpHandler httpHandler) {
        logger.info(LOC_NETWORK_INTERCEPTOR_START);
        return new NetworkInterceptor(getBrowser().getDriver(), httpHandler);
    }

    /**
     * Starts network interceptor.
     * @param filter network filter.
     * @return an instance of {@link NetworkInterceptor}.
     */
    public NetworkInterceptor startNetworkInterceptor(Filter filter) {
        logger.info(LOC_NETWORK_INTERCEPTOR_START);
        return new NetworkInterceptor(getBrowser().getDriver(), filter);
    }

    /**
     * Starts network interceptor.
     * @param routable a filter with matcher.
     * @return an instance of {@link NetworkInterceptor}.
     */
    public NetworkInterceptor startNetworkInterceptor(Routable routable) {
        logger.info(LOC_NETWORK_INTERCEPTOR_START);
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
     *  {@link NetworkInterceptor} classes to call {@link NetworkInterceptor#close()} for each.
     */
    public void clearNetworkInterceptor() {
        resetNetworkFilter();
    }

    /**
     * Activates emulation of network conditions.
     * @param offline True to emulate internet disconnection.
     * @param latency Minimum latency from request sent to response headers received (ms).
     * @param downloadThroughput Maximal aggregated download throughput (bytes/sec). -1 disables download throttling.
     * @param uploadThroughput Maximal aggregated upload throughput (bytes/sec). -1 disables upload throttling.
     */
    public void emulateConditions(Boolean offline, Number latency, Number downloadThroughput, Number uploadThroughput) {
        tools.sendCommand(enable(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));
        tools.sendCommand(emulateNetworkConditions(offline, latency, downloadThroughput, uploadThroughput, Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty()));
    }

    /**
     * Activates emulation of network conditions.
     * @param offline True to emulate internet disconnection.
     * @param latency Minimum latency from request sent to response headers received (ms).
     * @param downloadThroughput Maximal aggregated download throughput (bytes/sec). -1 disables download throttling.
     * @param uploadThroughput Maximal aggregated upload throughput (bytes/sec). -1 disables upload throttling.
     * @param connectionType Connection type if known.
     *                       Possible values: "none", "cellular2g", "cellular3g", "cellular4g", "bluetooth", "ethernet",
     *                       "wifi", "wimax", "other".
     */
    public void emulateConditions(Boolean offline, Number latency, Number downloadThroughput, Number uploadThroughput, String connectionType) {
        tools.sendCommand(enable(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));
        tools.sendCommand(emulateNetworkConditions(offline, latency, downloadThroughput, uploadThroughput,
                Optional.of(ConnectionType.fromString(connectionType)), Optional.empty(), Optional.empty(), Optional.empty()));
    }
}
