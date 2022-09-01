package tests.usecases.devtools;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.logging.HttpExchangeLoggingOptions;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseTest;
import theinternet.TheInternetPage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class LogHttpExchangeTest extends BaseTest {
    private final Path logFilePath = new File("target/log/log.log").toPath();

    private String getLastMessage() {
        try {
            List<String> lines = Files.readAllLines(logFilePath);
            return lines.isEmpty() ? null : lines.get(lines.size() - 1);
        } catch (IOException e) {
            return null;
        }
    }

    @Test
    public void testEnablingHttpExchangeLogging() {
        navigate(TheInternetPage.DROPDOWN);
        String logMessage1 = getLastMessage();
        Assert.assertNotNull(logMessage1, "Some message should appear in log file and should not be empty");
        HttpExchangeLoggingOptions httpExchangeLoggingOptions = new HttpExchangeLoggingOptions();
        AqualityServices.getBrowser().network().enableHttpExchangeLogging(httpExchangeLoggingOptions);
        AqualityServices.getBrowser().getDriver().navigate().refresh();
        String logMessage2 = getLastMessage();
        Assert.assertNotNull(logMessage2, "Some message should appear in log file and should not be empty");
        Assert.assertNotEquals(logMessage2, logMessage1, "HTTP logging message should be in file, although no Aquality-actions performed");
    }
}
