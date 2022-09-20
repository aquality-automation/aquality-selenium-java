package aquality.selenium.browser.devtools;

import org.openqa.selenium.devtools.idealized.ScriptId;

/**
 * Represents a JavaScript script that is loaded and run on every document load.
 */
public class InitializationScript {
    private final ScriptId scriptId;
    private final String scriptName;
    private final String scriptSource;

    /**
     * Initializes a new instance of initialization script.
     * @param scriptId the internal ID of the initialization script.
     * @param scriptName the friendly name of the initialization script.
     * @param scriptSource the JavaScript source of the initialization script.
     */
    public InitializationScript(ScriptId scriptId, String scriptName, String scriptSource) {
        this.scriptId = scriptId;
        this.scriptName = scriptName;
        this.scriptSource = scriptSource;
    }

    /**
     * Gets the internal ID of the initialization script.
     * @return the internal ID of the initialization script.
     */
    public ScriptId getScriptId() {
        return scriptId;
    }

    /**
     * Gets the friendly name of the initialization script.
     * @return the friendly name of the initialization script.
     */
    public String getScriptName() {
        return scriptName;
    }

    /**
     * Gets the JavaScript source of the initialization script
     * @return the JavaScript source of the initialization script
     */
    public String getScriptSource() {
        return scriptSource;
    }
}
