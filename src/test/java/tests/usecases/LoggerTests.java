package tests.usecases;

import aquality.selenium.logger.Logger;
import org.apache.log4j.Appender;
import org.apache.log4j.Layout;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.FileUtil;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class LoggerTests {

    private final static String AddAppenderLogFile = "target/log/addAppenderTestLog.log";
    private final static String RemoveAppenderLogFile = "target/log/removeAppenderTestLog.log";
    private final static String TestMessage = "test message";

    @BeforeTest
    private void before(){
        FileUtil.deleteFile(new File(AddAppenderLogFile));
        FileUtil.deleteFile(new File(RemoveAppenderLogFile));
    }

    @Test
    public void testShouldBePossibleToAddAppender() throws IOException {
        Appender appender = getFileAppender(AddAppenderLogFile);
        Logger.getInstance().addAppender(appender).info(TestMessage);
        File file = new File(AddAppenderLogFile);
        Assert.assertTrue(file.exists(), String.format("New appender is not added to log4net. File '%s' is not created.", AddAppenderLogFile));
        List<String> content = Files.readAllLines(Paths.get(AddAppenderLogFile), StandardCharsets.UTF_8);
        Assert.assertTrue(content.contains(TestMessage), String.format("Log '%s' doesn't contains message '%s'.", AddAppenderLogFile, TestMessage));
    }

    @Test
    public void testShouldBePossibleToRemoveAppender() throws IOException {
        Appender appender = getFileAppender(RemoveAppenderLogFile);
        Logger.getInstance().addAppender(appender).removeAppender(appender).info(TestMessage);
        File file = new File(RemoveAppenderLogFile);
        if(file.exists()){
            List<String> content = Files.readAllLines(Paths.get(RemoveAppenderLogFile), StandardCharsets.UTF_8);
            Assert.assertTrue(content.isEmpty(), String.format("New appender is not removed from log4net. File '%s' is not empty.", RemoveAppenderLogFile));
        }
    }

    private Appender getFileAppender(String filePath) throws IOException {
        Layout layout = new PatternLayout("%m%n");
        RollingFileAppender fileAppender = new RollingFileAppender(layout, filePath);
        fileAppender.setName("test");
        fileAppender.setAppend(true);
        return fileAppender;
    }
}
