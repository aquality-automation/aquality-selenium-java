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
import org.openqa.selenium.logging.EventType;
import org.openqa.selenium.logging.HasLogEvents;
import org.openqa.selenium.remote.Augmenter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
     * Gets the read-only list of binding callbacks added for this JavaScript engine.
     * @return list of binding callbacks added for this JavaScript engine.
     */
    public List<String> getBindings() {
        logger.info("loc.browser.javascript.scriptcallbackbindings.get");
        return new ArrayList<>(bindings);
    }

    /**
     * Removes all initialization scripts from being loaded for each document, and stops listening for events.
     */
    public void disable() {
        logger.info("loc.browser.javascript.reset");
        engine.disable();
        bindings.clear();
    }

    /**
     * Pins a JavaScript snippet for execution in the browser without transmitting the entire script across the wire
     * for every execution.
     * @param exposeScriptAs The name of the callback that will trigger events when the named binding is called by
     *                       JavaScript executing in the browser.
     * @param script The JavaScript to pin.
     * @return a {@link ScriptId} object to use to execute the script.
     */
    public ScriptId pin(String exposeScriptAs, String script) {
        logger.info("loc.browser.javascript.snippet.pin");
        logger.info("loc.browser.javascript.scriptcallbackbinding.add", exposeScriptAs);
        bindings.add(exposeScriptAs);
        return engine.pin(exposeScriptAs, script);
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
    public void addConsoleEventListener(Consumer<ConsoleEvent> listener) {
        logger.info("loc.browser.javascript.event.consoleapicalled.add");
        getDriverThatHasLogEvents().onLogEvent(consoleEvent(listener));
    }

    /**
     * Adds a listener for events that occur when methods on the JavaScript console are called.
     * @param listener a listener to add, consuming a javascript exception.
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
     * Adds a binding to a callback method that will raise an event when the named binding is called by JavaScript
     * executing in the browser.
     * @param scriptName The name of the callback that will trigger events.
     */
    public void addJsBinding(String scriptName) {
        logger.info("loc.browser.javascript.scriptcallbackbinding.add", scriptName);
        engine.addJsBinding(scriptName);
        bindings.add(scriptName);
    }

    /**
     * Removes a binding to a JavaScript callback.
     * @param scriptName The name of the callback to be removed.
     */
    public void removeJsBinding(String scriptName) {
        logger.info("loc.browser.javascript.scriptcallbackbinding.remove", scriptName);
        engine.removeJsBinding(scriptName);
        bindings.remove(scriptName);
    }
}
