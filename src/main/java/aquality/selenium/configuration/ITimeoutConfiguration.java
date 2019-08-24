package aquality.selenium.configuration;

public interface ITimeoutConfiguration {

    long getImplicit();

    long getCondition();

    long getScript();

    long getPageLoad();

    long getPollingInterval();

    long getCommand();
}
