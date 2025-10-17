package aquality.selenium.logging;

/**
 * HTTP Request/Response logging options.
 */
public class HttpExchangeLoggingOptions {
    private LoggingParameters requestInfo = new LoggingParameters(true, LogLevel.INFO);
    private LoggingParameters requestHeaders = new LoggingParameters(true, LogLevel.DEBUG);
    private LoggingParameters requestPostData = new LoggingParameters(false, LogLevel.DEBUG);
    private LoggingParameters responseInfo = new LoggingParameters(true, LogLevel.INFO);
    private LoggingParameters responseHeaders = new LoggingParameters(true, LogLevel.DEBUG);
    private LoggingParameters responseBody = new LoggingParameters(false, LogLevel.DEBUG);

    /**
     * Gets logging parameters of general request info: Method, URL, Request ID.
     *
     * @return request info logging parameters.
     */
    public LoggingParameters getRequestInfo() {
        return requestInfo;
    }

    /**
     * Sets logging parameters of general request info: Method, URL, Request ID.
     *
     * @param requestInfo request info logging parameters.
     */
    public void setRequestInfo(LoggingParameters requestInfo) {
        this.requestInfo = requestInfo;
    }

    /**
     * Gets logging parameters of request headers.
     *
     * @return logging parameters of request headers.
     */
    public LoggingParameters getRequestHeaders() {
        return requestHeaders;
    }

    /**
     * Sets logging parameters of request headers.
     *
     * @param requestHeaders logging parameters of request headers.
     */
    public void setRequestHeaders(LoggingParameters requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    /**
     * Gets logging parameters of request POST data.
     *
     * @return logging parameters of request POST data.
     */
    public LoggingParameters getRequestPostData() {
        return requestPostData;
    }

    /**
     * Sets logging parameters of request POST data.
     *
     * @param requestPostData logging parameters of request POST data.
     */
    public void setRequestPostData(LoggingParameters requestPostData) {
        this.requestPostData = requestPostData;
    }

    /**
     * Gets logging parameters of general response info: Status code, URL, Resource type, Request ID.
     *
     * @return logging parameters of general response info.
     */
    public LoggingParameters getResponseInfo() {
        return responseInfo;
    }

    /**
     * Sets logging parameters of general response info: Status code, URL, Resource type, Request ID.
     *
     * @param responseInfo logging parameters of general response info.
     */
    public void setResponseInfo(LoggingParameters responseInfo) {
        this.responseInfo = responseInfo;
    }

    /**
     * Gets logging parameters of response headers.
     *
     * @return logging parameters of response headers.
     */
    public LoggingParameters getResponseHeaders() {
        return responseHeaders;
    }

    /**
     * Sets logging parameters of response headers.
     *
     * @param responseHeaders logging parameters of response headers.
     */
    public void setResponseHeaders(LoggingParameters responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    /**
     * Gets logging parameters of response body.
     *
     * @return logging parameters of response body.
     */
    public LoggingParameters getResponseBody() {
        return responseBody;
    }

    /**
     * Sets logging parameters of response body.
     *
     * @param responseBody logging parameters of response body.
     */
    public void setResponseBody(LoggingParameters responseBody) {
        this.responseBody = responseBody;
    }
}
