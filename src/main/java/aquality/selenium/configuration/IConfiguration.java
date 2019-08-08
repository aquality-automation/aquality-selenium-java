package aquality.selenium.configuration;

public interface IConfiguration {

    IBrowserProfile getBrowserProfile();

    ITimeoutConfiguration getTimeoutConfiguration();

    ILoggerConfiguration getLoggerConfiguration();
}
