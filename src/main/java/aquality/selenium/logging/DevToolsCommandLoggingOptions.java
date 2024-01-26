package aquality.selenium.logging;

/**
 * DevTools Command/Result logging options.
 */
public class DevToolsCommandLoggingOptions {
    private LoggingParameters command = new LoggingParameters(true, LogLevel.INFO);
    private LoggingParameters result = new LoggingParameters(true, LogLevel.INFO);

    /**
     * Gets logging parameters for command info: name and parameters (if any).
     * @return command info logging parameters.
     */
    public LoggingParameters getCommand() {
        return command;
    }

    /**
     * Sets logging parameters for command info: name and parameters (if any).
     */
    public void setCommand(LoggingParameters command) {
        this.command = command;
    }

    /**
     * Gets logging parameters for command result (when it's present).
     * @return logging parameters of command result.
     */
    public LoggingParameters getResult() {
        return result;
    }

    /**
     * Sets logging parameters for command result (when it's present).
     */
    public void setResult(LoggingParameters result) {
        this.result = result;
    }
}
