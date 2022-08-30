package aquality.selenium.browser.devtools;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.localization.ILocalizedLogger;
import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.events.ConsoleEvent;
import org.openqa.selenium.devtools.events.DomMutationEvent;
import org.openqa.selenium.devtools.idealized.Events;
import org.openqa.selenium.devtools.idealized.Javascript;
import org.openqa.selenium.devtools.idealized.ScriptId;
import org.openqa.selenium.devtools.v85.page.Page;
import org.openqa.selenium.devtools.v85.runtime.Runtime;
import org.openqa.selenium.logging.EventType;
import org.openqa.selenium.logging.HasLogEvents;
import org.openqa.selenium.remote.Augmenter;

import java.util.*;
import java.util.function.Consumer;

import static org.openqa.selenium.devtools.events.CdpEventTypes.consoleEvent;
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
        tools.sendCommand(Runtime.addBinding(scriptName, Optional.empty()));
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

    /**
     * Removes JavaScript from being loaded on every document load, and removes a callback binding for it.
     * @param script an instance of script to be removed.
     */
    public void removeInitializationScript(InitializationScript script) {
        logger.info("loc.browser.javascript.initializationscript.remove", script.getScriptName());
        tools.sendCommand(Page.removeScriptToEvaluateOnNewDocument(script.getScriptId().getActualId()));
        initializationScripts.remove(script);
        removeScriptCallbackBinding(script.getScriptName());
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
        initializationScripts.forEach(script -> {
            Page.removeScriptToEvaluateOnNewDocument(script.getScriptId().getActualId());
            initializationScripts.remove(script);
            tools.sendCommand(Runtime.removeBinding(script.getScriptName()));
            bindings.remove(script.getScriptName());
        });
    }

    /**
     * Adds a listener for events that occur when a JavaScript callback with a named binding is executed.
     * To add a binding, use {@link this.addJsBinding}.
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
                throw new NotImplementedException(
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
     * @param listener a listener to add, consuming a javascript exception.
     */
    public void addJavaScriptConsoleApiListener(Consumer<ConsoleEvent> listener) {
        logger.info("loc.browser.javascript.event.consoleapicalled.add");
        getDriverThatHasLogEvents().onLogEvent(consoleEvent(listener));
    }

    /**
     * Adds a listener for events that occur when methods on the JavaScript console are called.
     * Consider using a method {@link this.addJavaScriptConsoleApiListener} instead.
     * @param listener a listener to add, consuming a javascript exception.
     */
    public void addConsoleEventListener(Consumer<ConsoleEvent> listener) {
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
     * Disables console event listener and JavaScript event listener (disables the runtime).
     */
    public void disableConsoleEventListeners() {
        logger.info("loc.browser.javascript.event.consoleapicalled.disable");
        events.disable();
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
