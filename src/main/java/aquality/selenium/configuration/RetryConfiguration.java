package aquality.selenium.configuration;

import aquality.selenium.utils.JsonFile;

public class RetryConfiguration implements IRetryConfiguration {
    private final int number;
    private final long pollingInterval;


    public RetryConfiguration(JsonFile settingsFile) {
        this.number = Integer.parseInt(settingsFile.getValue("/retry/number").toString());
        this.pollingInterval = Long.parseLong(settingsFile.getValue("/retry/pollingInterval").toString());
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public long getPollingInterval() {
        return pollingInterval;
    }
}
