package testreport;

import aquality.selenium.browser.AqualityServices;
import org.apache.logging.log4j.core.util.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ScreenshotListener extends TestListenerAdapter {
    @Override
    public void onTestFailure(ITestResult result) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        String dateString = formatter.format(calendar.getTime());
        String methodName = result.getName();
        if(!result.isSuccess() && AqualityServices.isBrowserStarted()){
            File scrFile = ((TakesScreenshot)AqualityServices.getBrowser().getDriver()).getScreenshotAs(OutputType.FILE);
            try {
                String reportDirectory = String.format("%s/target/surefire-reports", new File(System.getProperty("user.dir")).getAbsolutePath());
                File destFile = new File(String.format("%s/failure_screenshots/%s_%s.png", reportDirectory, methodName, dateString));
                FileUtils.makeParentDirs(destFile);
                Files.copy(scrFile.toPath(), destFile.toPath());
                Reporter.log(String.format("<a href='%s'> <img src='%s' height='100' width='100'/> </a>", destFile.getAbsolutePath(), destFile.getAbsolutePath()));
            } catch (IOException e) {
                AqualityServices.getLogger().fatal("An IO exception occurred while tried to save a screenshot", e);
            }
        }
    }
}
