package aquality.selenium.configuration;

import aquality.selenium.utils.JsonFile;

public class TimeoutConfiguration implements ITimeoutConfiguration{
    private final JsonFile settingsFile;

    private final long condition;
    private final long script;
    private final long pageLoad;
    private final long pollInterval;
    private final long implicit;
    
    public TimeoutConfiguration(JsonFile settingsFile) {
        this.settingsFile = settingsFile;
        condition = getTimeout(TIMEOUT.CONDITION);
        script = getTimeout(TIMEOUT.SCRIPT);
        pageLoad = getTimeout(TIMEOUT.PAGE_LOAD);
        pollInterval = getTimeout(TIMEOUT.POLL_INTERVAL);
        implicit = getTimeout(TIMEOUT.IMPLICIT);
    }

    private long getTimeout(TIMEOUT timeout){
        return Long.valueOf(settingsFile.getValue("/timeouts/" + timeout.getKey()).toString());
    }


    public long getImplicit(){
        return implicit;
    }

    public long getCondition(){
        return condition;
    }

    public long getScript(){
        return script;
    }

    public long getPageLoad(){
        return pageLoad;
    }

    public long getPollingInterval(){
        return pollInterval;
    }

    private enum TIMEOUT {
        IMPLICIT("timeoutImplicit"),
        CONDITION("timeoutCondition"),
        SCRIPT("timeoutScript"),
        PAGE_LOAD("timeoutPageLoad"),
        POLL_INTERVAL("timeoutPollingInterval");

        private String key;
        TIMEOUT(String key){
            this.key = key;
        }

        private String getKey(){
            return key;
        }
    }
}
