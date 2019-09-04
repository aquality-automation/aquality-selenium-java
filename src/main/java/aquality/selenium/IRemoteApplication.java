package aquality.selenium;

import org.openqa.selenium.remote.RemoteWebDriver;

public interface IRemoteApplication {

    void quit();

    RemoteWebDriver getDriver();

    void setImplicitWaitTimeout(Long timeout);
}
