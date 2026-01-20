package aquality.selenium.browser.devtools;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.localization.ILocalizedLogger;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.ScriptKey;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.events.ConsoleEvent;
import org.openqa.selenium.devtools.events.DomMutationEvent;
import org.openqa.selenium.devtools.idealized.Events;
import org.openqa.selenium.devtools.idealized.Javascript;
import org.openqa.selenium.devtools.idealized.ScriptId;
import org.openqa.selenium.devtools.idealized.target.model.SessionID;
import org.openqa.selenium.devtools.v144.page.Page;
import org.openqa.selenium.devtools.v144.page.model.ScriptIdentifier;
import org.openqa.selenium.devtools.v144.runtime.Runtime;
import org.openqa.selenium.logging.EventType;
import org.openqa.selenium.logging.HasLogEvents;
import org.openqa.selenium.remote.Augmenter;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;

import static org.openqa.selenium.devtools.events.CdpEventTypes.domMutation;

/**
 * DevTools commands for version-independent network interception.
 * For more information, see {@link Javascript}.
 */
public class JavaScriptHandling {
    private final DevToolsHandling tools;
    private final Javascript<?, ?> engine;
    private final Events<?, ?> events;
    private final ILocalizedLogger logger = AqualityServices.getLocalizedLogger();
    private final Set<String> bindings = new HashSet<>();
    private final Set<InitializationScript> initializationScripts = new HashSet<>();

    /**
     * Initializes a new instance of the {@link JavaScriptHandling} class.
     * @param tools Instance of {@link DevToolsHandling}.
     */
    public JavaScriptHandling(DevToolsHandling tools) {
        this.tools = tools;
        this.engine = tools.getDevToolsSession().getDomains().javascript();
        this.events = tools.getDevToolsSession().getDomains().events();
    }

    /**
     * Adds a binding to a callback method that will raise an event when the named binding is called by JavaScript
     * executing in the browser.
     * @param scriptName The name of the callback that will trigger events.
     */
    public void addScriptCallbackBinding(String scriptName) {
        logger.info("loc.browser.javascript.scriptcallbackbinding.add", scriptName);
        bindings.add(scriptName);
        tools.sendCommand(Runtime.addBinding(scriptName, Optional.empty(), Optional.empty()));
    }

    /**
     * Removes a binding to a JavaScript callback.
     * @param scriptName The name of the callback to be removed.
     */
    public void removeScriptCallbackBinding(String scriptName) {
        logger.info("loc.browser.javascript.scriptcallbackbinding.remove", scriptName);
        bindings.remove(scriptName);
        tools.sendCommand(Runtime.removeBinding(scriptName));
    }

    /**
     * Gets the read-only list of binding callbacks added for this JavaScript engine.
     * @return list of binding callbacks added for this JavaScript engine.
     */
    public List<String> getScriptCallbackBindings() {
        logger.info("loc.browser.javascript.scriptcallbackbindings.get");
        return new ArrayList<>(bindings);
    }

    /**
     * Removes all bindings to JavaScript callbacks.
     */
    public void clearScriptCallbackBindings() {
        logger.info("loc.browser.javascript.scriptcallbackbindings.clear");
        bindings.forEach(scriptName -> {
            bindings.remove(scriptName);
            tools.sendCommand(Runtime.removeBinding(scriptName));
        });
    }

    /**
     * Adds JavaScript to be loaded on every document load, and adds a binding to a callback method
     * that will raise an event when the script with that name is called.
     * @param scriptName The friendly name by which to refer to this initialization script.
     * @param script The JavaScript to be loaded on every page.
     * @return Initialization script.
     */
    public InitializationScript addInitializationScript(String scriptName, String script) {
        logger.info("loc.browser.javascript.initializationscript.add", scriptName);
        logger.info("loc.browser.javascript.scriptcallbackbinding.add", scriptName);
        ScriptId scriptId = engine.pin(scriptName, script);
        InitializationScript initializationScript = new InitializationScript(scriptId, scriptName, script);
        bindings.add(scriptName);
        initializationScripts.add(initializationScript);
        return initializationScript;
    }

    private void removeInitializationScriptCore(InitializationScript script) {
        tools.sendCommand(Page.removeScriptToEvaluateOnNewDocument(new ScriptIdentifier(script.getScriptId().getActualId().toString())));
        try {
            final Field pinnedScripts = Javascript.class.getDeclaredField("pinnedScripts");
            pinnedScripts.setAccessible(true);
            //noinspection unchecked
            ((Map<SessionID, Map<String, ScriptId>>)pinnedScripts.get(engine))
                    .get(tools.getDevToolsSession().getCdpSession())
                    .remove(script.getScriptSource());
            pinnedScripts.setAccessible(false);
        } catch (ReflectiveOperationException e) {
            AqualityServices.getLogger().fatal("Error while removing initialization script", e);
        }
        initializationScripts.remove(script);
        removeScriptCallbackBinding(script.getScriptName());
    }

    /**
     * Removes JavaScript from being loaded on every document load, and removes a callback binding for it.
     * @param script an instance of script to be removed.
     */
    public void removeInitializationScript(InitializationScript script) {
        logger.info("loc.browser.javascript.initializationscript.remove", script.getScriptName());
        removeInitializationScriptCore(script);
    }

    /**
     * Gets the read-only list of initialization scripts added for this JavaScript engine.
     * @return the list of added initialization scripts.
     */
    public List<InitializationScript> getInitializationScripts() {
        logger.info("loc.browser.javascript.initializationscripts.get");
        return new ArrayList<>(initializationScripts);
    }

    /**
     * Removes all initialization scripts from being loaded on every document load.
     */
    public void clearInitializationScripts() {
        logger.info("loc.browser.javascript.initializationscripts.clear");
        initializationScripts.forEach(this::removeInitializationScriptCore);
    }

    private JavascriptExecutor getJavascriptExecutor() {
        return (JavascriptExecutor) tools.getDevToolsProvider();
    }

    /**
     * Pins a JavaScript snippet for execution in the browser without transmitting the entire script across the wire for every execution.
     * @param script The JavaScript to pin.
     * @return object to use to execute the script.
     */
    public ScriptKey pinScript(String script) {
        logger.info("loc.browser.javascript.snippet.pin");
        return getJavascriptExecutor().pin(script);
    }

    /**
     * Unpins a previously pinned script from the browser.
     * @param pinnedScript The {@link ScriptKey} object to unpin.
     */
    public void unpinScript(ScriptKey pinnedScript) {
        logger.info("loc.browser.javascript.snippet.unpin");
        getJavascriptExecutor().unpin(pinnedScript);
    }

    /**
     * Gets list of previously pinned scripts.
     * @return a set of previously pinned scripts.
     */
    public Set<ScriptKey> getPinnedScripts() {
        logger.info("loc.browser.javascript.snippets.get");
        return getJavascriptExecutor().getPinnedScripts();
    }

    /**
     * Unpins previously pinned scripts from being loaded on every document load.
     */
    public void clearPinnedScripts() {
        logger.info("loc.browser.javascript.snippets.clear");
        getJavascriptExecutor().getPinnedScripts().forEach(getJavascriptExecutor()::unpin);
    }

    /**
     * Starts monitoring for events from the browser's JavaScript engine.
     */
    public void startEventMonitoring() {
        logger.info("loc.browser.javascript.event.monitoring.start");
        tools.sendCommand(Runtime.enable());
    }

    /**
     * Stops monitoring for events from the browser's JavaScript engine, and clears JavaScript console event listeners.
     */
    public void stopEventMonitoring() {
        logger.info("loc.browser.javascript.event.monitoring.stop");
        events.disable();
    }

    /**
     * Adds a listener for events that occur when a JavaScript callback with a named binding is executed.
     * To add a binding, use {@link JavaScriptHandling#addScriptCallbackBinding(String)}.
     * @param listener a listener to add, consuming a name of exposed script.
     */
    public void addBindingCalledListener(Consumer<String> listener) {
        logger.info("loc.browser.javascript.event.callbackexecuted.add");
        engine.addBindingCalledListener(listener);
    }

    private HasLogEvents getDriverThatHasLogEvents() {
        WebDriver driver = (WebDriver) tools.getDevToolsProvider();
        if (!(driver instanceof HasLogEvents)) {
            Augmenter augmenter = new Augmenter();
            String browserName = AqualityServices.getBrowserProfile().getBrowserName().name().toLowerCase();
            driver = augmenter.addDriverAugmentation(browserName, HasLogEvents.class, (caps, exec) -> new HasLogEvents() {
                @Override
                public <X> void onLogEvent(EventType<X> kind) {
                    kind.initializeListener((WebDriver) tools.getDevToolsProvider());
                }
            }).augment(driver);
            if (!(driver instanceof HasLogEvents)) {
                throw new UnsupportedOperationException(
                        String.format("Driver for the current browser [%s] doesn't implement HasLogEvents", browserName));
            }
        }
        return (HasLogEvents) driver;
    }

    /**
     * Adds a listener for events that occur when a value of an attribute in an element is being changed.
     * @param listener a listener to add, consuming a dom mutation event.
     */
    public void addDomMutatedListener(Consumer<DomMutationEvent> listener) {
        logger.info("loc.browser.javascript.event.dommutated.add");
        getDriverThatHasLogEvents().onLogEvent(domMutation(listener));
    }

    /**
     * Adds a listener for events that occur when methods on the JavaScript console are called.
     * @param listener a listener to add, consuming a {@link ConsoleEvent}.
     */
    public void addJavaScriptConsoleApiListener(Consumer<ConsoleEvent> listener) {
        logger.info("loc.browser.javascript.event.consoleapicalled.add");
        events.addConsoleListener(listener);
    }

    /**
     * Adds a listener for events that occur when an exception is thrown by JavaScript being executed in the browser.
     * @param listener a listener to add, consuming a javascript exception.
     */
    public void addJavaScriptExceptionThrownListener(Consumer<JavascriptException> listener) {
        logger.info("loc.browser.javascript.event.exceptionthrown.add");
        events.addJavascriptExceptionListener(listener);
    }

    /**
     * Removes all bindings to JavaScript callbacks and all initialization scripts from being loaded for each document.
     */
    public void clearAll() {
        logger.info("loc.browser.javascript.clearall");
        clearInitializationScripts();
        clearScriptCallbackBindings();
    }

    /**
     * Removes all bindings to JavaScript callbacks and all initialization scripts from being loaded for each document,
     * and stops listening for events.
     */
    public void reset() {
        logger.info("loc.browser.javascript.reset");
        engine.disable();
        clearInitializationScripts();
        clearScriptCallbackBindings();
    }
}
