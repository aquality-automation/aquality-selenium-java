package aquality.selenium.browser.devtools;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.localization.ILocalizedLogger;
import aquality.selenium.logging.DevToolsCommandLoggingOptions;
import aquality.selenium.logging.LoggingParameters;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.devtools.Command;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.Event;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v142.performance.Performance;
import org.openqa.selenium.devtools.v142.performance.model.Metric;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static aquality.selenium.logging.LocalizedLoggerUtility.logByLevel;

/**
 * Wrapper for Selenium {@link DevTools} functionality.
 */
public class DevToolsHandling {
    private final HasDevTools devToolsProvider;
    private final ILocalizedLogger logger;

    private DevTools session;
    private NetworkHandling network;
    private EmulationHandling emulation;
    private JavaScriptHandling javaScript;

    /**
     * Initializes an instance of {@link DevToolsHandling}
     * @param devToolsProvider Instance of {@link org.openqa.selenium.WebDriver} which supports CDP protocol.
     */
    public DevToolsHandling(HasDevTools devToolsProvider) {
        this.devToolsProvider = devToolsProvider;
        this.logger = AqualityServices.getLocalizedLogger();
    }

    HasDevTools getDevToolsProvider() {
        return devToolsProvider;
    }

    private DevTools getDevTools() {
        return getDevTools("default");
    }

    private DevTools getDevTools(String handleToLog) {
        if (session == null) {
            logger.info("loc.browser.devtools.session.get", handleToLog);
            session = devToolsProvider.getDevTools();
        }
        return session;
    }

    private void logCommand(String commandName, Map<String, Object> commandParameters,
                            DevToolsCommandLoggingOptions loggingOptions) {
        LoggingParameters logging = (loggingOptions == null ? new DevToolsCommandLoggingOptions() : loggingOptions)
                .getCommand();
        if (!logging.isEnabled())
        {
            return;
        }
        if (!commandParameters.isEmpty()) {
            logByLevel(logging.getLogLevel(), "loc.browser.devtools.command.execute.withparams", commandName, commandParameters);
        }
        else {
            logByLevel(logging.getLogLevel(), "loc.browser.devtools.command.execute", commandName);
        }
    }

    private void logCommandResult(Object result, DevToolsCommandLoggingOptions loggingOptions) {
        LoggingParameters logging = (loggingOptions == null ? new DevToolsCommandLoggingOptions() : loggingOptions)
                .getCommand();
        if (result != null && logging.isEnabled()) {
            if (result instanceof Map && ((Map<?, ?>) result).isEmpty()) {
                return;
            }
            logByLevel(logging.getLogLevel(), "loc.browser.devtools.command.execute.result", result);
        }
    }

    /**
     * Creates a session to communicate with a browser using the Chromium Developer Tools debugging protocol, if there is not one.
     * If the session already exist, returns existing session.
     * Defaults to autodetect the protocol version for Chromium, V85 for Firefox.
     * @return The active session to use to communicate with the Chromium Developer Tools debugging protocol.
     */
    public DevTools getDevToolsSession() {
        getDevTools().createSessionIfThereIsNotOne();
        return session;
    }

    /**
     * Creates a session to communicate with a browser using the Chromium Developer Tools debugging protocol, if there is not one, on given window/tab (aka target).
     * If the session already exist, returns existing session.
     * If windowHandle is null, then the first "page" type will be selected.
     * Pass the windowHandle if you have multiple windows/tabs opened to connect to the expected window/tab.
     * Defaults to autodetect the protocol version for Chromium, V85 for Firefox.
     * @param windowHandle result of {@link org.openqa.selenium.WebDriver#getWindowHandle()}, optional.     *
     * @return The active session to use to communicate with the Chromium Developer Tools debugging protocol.
     */
    public DevTools getDevToolsSession(String windowHandle) {
        getDevTools(windowHandle).createSessionIfThereIsNotOne(windowHandle);
        return session;
    }

    /**
     * Gets a value indicating whether a DevTools session is active.
     * @return true if there is an active session, false otherwise.
     */
    public boolean hasActiveDevToolsSession() {
        logger.info("loc.browser.devtools.session.isactive");
        boolean result = devToolsProvider.getDevTools().getCdpSession() != null;
        logger.info("loc.browser.devtools.session.isactive.result", result);
        return result;
    }

    /**
     * Closes a DevTools session.
     */
    public void closeDevToolsSession() {
        logger.info("loc.browser.devtools.session.close");
        getDevTools().disconnectSession();
    }

    /**
     * Executes a custom Chromium Dev Tools Protocol Command.
     * Note: works only if current driver is instance of {@link ChromiumDriver}.
     * @param commandName Name of the command to execute.
     * @param commandParameters Parameters of the command to execute.
     * @return An object representing the result of the command, if applicable.
     */
    public Map<String, Object> executeCdpCommand(String commandName, Map<String, Object> commandParameters) {
        return executeCdpCommand(commandName, commandParameters, null);
    }

    /**
     * Executes a custom Chromium Dev Tools Protocol Command.
     * Note: works only if current driver is instance of {@link ChromiumDriver}.
     * @param commandName Name of the command to execute.
     * @param commandParameters Parameters of the command to execute.
     * @param loggingOptions Logging preferences.
     * @return An object representing the result of the command, if applicable.
     */
    public Map<String, Object> executeCdpCommand(String commandName, Map<String, Object> commandParameters,
                                                 DevToolsCommandLoggingOptions loggingOptions) {
        if (devToolsProvider instanceof ChromiumDriver) {
            logCommand(commandName, commandParameters, loggingOptions);
            ChromiumDriver driver = (ChromiumDriver) devToolsProvider;
            Map<String, Object> result = driver.executeCdpCommand(commandName, commandParameters);
            logCommandResult(result, loggingOptions);
            return result;
        }
        else {
            throw new UnsupportedOperationException("Execution of CDP command directly is not supported for current browser. Try sendCommand method instead.");
        }
    }

    /**
     * Sends the specified command and returns the associated command response.
     * @param command An instance of the {@link Command} to send.
     * @param <X> The type of the command's result. For most commands it's {@link Void}
     * @return the result of the command, if applicable
     */
    public <X> X sendCommand(Command<X> command) {
        return sendCommand(command, null);
    }

    /**
     * Sends the specified command and returns the associated command response.
     * @param command An instance of the {@link Command} to send.
     * @param <X> The type of the command's result. For most commands it's {@link Void}
     * @param loggingOptions Logging preferences.
     * @return the result of the command, if applicable
     */
    public <X> X sendCommand(Command<X> command, DevToolsCommandLoggingOptions loggingOptions) {
        logCommand(command.getMethod(), command.getParams(), loggingOptions);
        X result = getDevToolsSession().send(command);
        logCommandResult(result, loggingOptions);
        return result;
    }

    /**
     * Adds a listener for a specific event with the handler for it.
     * @param event Event to listen for
     * @param handler Handler to call
     * @param <X> The type of the event's result.
     */
    public <X> void addListener(Event<X> event, Consumer<X> handler) {
        logger.info("loc.browser.devtools.listener.add", event.getMethod());
        getDevToolsSession().addListener(event, handler);
    }

    /**
     * Removes all the listeners, and disables all the domains.
     */
    public void clearListeners() {
        logger.info("loc.browser.devtools.listener.clear");
        getDevToolsSession().clearListeners();
    }

    /**
     * Version-independent emulation DevTools commands.
     * @return an instance of {@link EmulationHandling}.
     */
    public EmulationHandling emulation() {
        if (emulation == null) {
            emulation = new EmulationHandling(this);
        }
        return emulation;
    }

    /**
     * DevTools commands for network requests interception.
     * @return an instance of {@link NetworkHandling}.
     */
    public NetworkHandling network() {
        if (network == null) {
            network = new NetworkHandling(this);
        }
        return network;
    }

    /**
     * Provides JavaScript Monitoring functionality.
     * @return an instance of {@link JavaScriptHandling}
     */
    public JavaScriptHandling javaScript() {
        if (javaScript == null) {
            javaScript = new JavaScriptHandling(this);
        }
        return javaScript;
    }

    /**
     * Disable collecting and reporting metrics.
     */
    public void disablePerformanceMonitoring() {
        sendCommand(Performance.disable());
    }

    /**
     * Enable collecting and reporting metrics.
     * @param timeDomain Time domain to use for collecting and reporting duration metrics.
     *                   Allowed Values: timeTicks, threadTicks.
     */
    public void enablePerformanceMonitoring(String timeDomain) {
        sendCommand(Performance.enable(Optional.of(Performance.EnableTimeDomain.fromString(timeDomain))));
    }

    /**
     * Enable collecting and reporting metrics.
     */
    public void enablePerformanceMonitoring() {
        sendCommand(Performance.enable(Optional.empty()));
    }

    /**
     * Retrieve current values of run-time metrics.
     * @return Current values for run-time metrics
     */
    public Map<String, Number> getPerformanceMetrics() {
        Command<List<Metric>> command = Performance.getMetrics();
        logCommand(command.getMethod(), command.getParams(), null);
        List<Metric> metrics = getDevToolsSession().send(command);
        Map<String, Number> result = metrics.stream().collect(Collectors.toMap(Metric::getName, Metric::getValue));
        logCommandResult(result.isEmpty() ? "empty" : result, null);
        return result;
    }
}
