package aquality.selenium.logger;

import org.apache.log4j.*;
import org.testng.annotations.*;
import utils.FileUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.testng.Assert.*;

public class LoggerTests {

    private final static String appenderLogFile = "target/log/appender.log";
    private final static String testMessagesAppenderLogFile = "target/log/appenderMessages.log";
    private final static String testMessage = "test message";
    private final static String testExceptionText = "test exception";
    private final static String log4jFieldName = "log4J";
    private static org.apache.log4j.Logger log4j;
    private static Appender messagesTestsAppender;

    @BeforeMethod
    private void addMessagesAppender() throws IOException {
        FileUtil.deleteFile(new File(testMessagesAppenderLogFile));
        FileUtil.deleteFile(new File(appenderLogFile));
        messagesTestsAppender = getFileAppender(testMessagesAppenderLogFile);
        Logger.getInstance().addAppender(messagesTestsAppender);
    }

    @BeforeGroups("messages")
    private void initializeLog4jField() throws NoSuchFieldException, IllegalAccessException {
        Field log4jField = Logger.class.getDeclaredField(log4jFieldName);
        log4jField.setAccessible(true);
        log4j = ((ThreadLocal<org.apache.log4j.Logger>) log4jField.get(Logger.getInstance())).get();
    }

    @Test
    public void testGetInstanceShouldReturnSameObjectEachTime() {
        assertEquals(Logger.getInstance(), Logger.getInstance());
    }

    @Test
    public void testShouldBePossibleToAddAppender() throws IOException {
        Appender appender = getFileAppender(appenderLogFile);
        Logger.getInstance().addAppender(appender).info(testMessage);
        File file = new File(appenderLogFile);
        assertTrue(file.exists(), String.format("New appender is not added to log4j. File '%s' is not created.", appenderLogFile));
        assertTrue(isFileContainsText(appenderLogFile, testMessage), String.format("Log '%s' doesn't contains message '%s'.", appenderLogFile, testMessage));
    }

    @Test
    public void testShouldBePossibleToRemoveAppender() throws IOException {
        Appender appender = getFileAppender(appenderLogFile);
        Logger.getInstance().addAppender(appender).removeAppender(appender).info(testMessage);
        File file = new File(appenderLogFile);
        if(file.exists()){
            List<String> content = Files.readAllLines(Paths.get(appenderLogFile), StandardCharsets.UTF_8);
            assertTrue(content.isEmpty(), String.format("New appender is not removed from log4j. File '%s' is not empty.", appenderLogFile));
        }
    }

    @Test(groups = "messages")
    public void testInfoMessageShouldBeDisplayedAccordingToLogLevel() throws IOException {
        log4j.setLevel(Level.FATAL);
        Logger.getInstance().info(testMessage);
        assertFalse(isFileContainsText(testMessagesAppenderLogFile, testMessage));

        log4j.setLevel(Level.INFO);
        Logger.getInstance().info(testMessage);
        assertTrue(isFileContainsText(testMessagesAppenderLogFile, testMessage));
    }

    @Test(groups = "messages")
    public void testInfoMessageWithParametersShouldBeDisplayedAccordingToLogLevel() throws IOException {
        log4j.setLevel(Level.FATAL);
        Logger.getInstance().info("%s", testMessage);
        assertFalse(isFileContainsText(testMessagesAppenderLogFile, testMessage));

        log4j.setLevel(Level.INFO);
        Logger.getInstance().info("%s", testMessage);
        assertTrue(isFileContainsText(testMessagesAppenderLogFile, testMessage));
    }

    @Test(groups = "messages")
    public void testDebugMessageWithParametersShouldBeDisplayedAccordingToLogLevel() throws IOException {
        log4j.setLevel(Level.WARN);
        Logger.getInstance().debug("%s", testMessage);
        assertFalse(isFileContainsText(testMessagesAppenderLogFile, testMessage));

        log4j.setLevel(Level.DEBUG);
        Logger.getInstance().debug("%s", testMessage);
        assertTrue(isFileContainsText(testMessagesAppenderLogFile, testMessage));
    }

    @Test(groups = "messages")
    public void testDebugMessageShouldBeDisplayedAccordingToLogLevel() throws IOException {
        log4j.setLevel(Level.WARN);
        Logger.getInstance().debug(testMessage);
        assertFalse(isFileContainsText(testMessagesAppenderLogFile, testMessage));

        log4j.setLevel(Level.DEBUG);
        Logger.getInstance().debug(testMessage);
        assertTrue(isFileContainsText(testMessagesAppenderLogFile, testMessage));
    }

    @Test(groups = "messages")
    public void testDebugMessageWithThrowableShouldBeDisplayedAccordingToLogLevel() throws IOException {
        log4j.setLevel(Level.WARN);
        Logger.getInstance().debug(testMessage, new Exception(testExceptionText));
        assertFalse(isFileContainsText(testMessagesAppenderLogFile, testMessage));
        assertFalse(isFileContainsText(testMessagesAppenderLogFile, testExceptionText));

        log4j.setLevel(Level.DEBUG);
        Logger.getInstance().debug(testMessage, new Exception(testExceptionText));
        assertTrue(isFileContainsText(testMessagesAppenderLogFile, testMessage));
        assertTrue(isFileContainsText(testMessagesAppenderLogFile, testExceptionText));
    }

    @Test(groups = "messages")
    public void testWarnMessageShouldBeDisplayedAccordingToLogLevel() throws IOException {
        log4j.setLevel(Level.ERROR);
        Logger.getInstance().warn(testMessage);
        assertFalse(isFileContainsText(testMessagesAppenderLogFile, testMessage));

        log4j.setLevel(Level.WARN);
        Logger.getInstance().warn(testMessage);
        assertTrue(isFileContainsText(testMessagesAppenderLogFile, testMessage));
    }

    @Test(groups = "messages")
    public void testFatalMessageShouldBeDisplayedAccordingToLogLevel() throws IOException {
        log4j.setLevel(Level.OFF);
        Logger.getInstance().fatal(testMessage, new Exception(testExceptionText));
        assertFalse(isFileContainsText(testMessagesAppenderLogFile, testMessage));
        assertFalse(isFileContainsText(testMessagesAppenderLogFile, testExceptionText));

        log4j.setLevel(Level.FATAL);
        Logger.getInstance().fatal(testMessage, new Exception(testExceptionText));
        assertTrue(isFileContainsText(testMessagesAppenderLogFile, testMessage));
        assertTrue(isFileContainsText(testMessagesAppenderLogFile, testExceptionText));
    }

    @Test(groups = "messages")
    public void testErrorMessageShouldBeDisplayedAccordingToLogLevel() throws IOException {
        log4j.setLevel(Level.FATAL);
        Logger.getInstance().error(testMessage);
        assertFalse(isFileContainsText(testMessagesAppenderLogFile, testMessage));

        log4j.setLevel(Level.ERROR);
        Logger.getInstance().error(testMessage);
        assertTrue(isFileContainsText(testMessagesAppenderLogFile, testMessage));
    }

    private Appender getFileAppender(String filePath) throws IOException {
        Layout layout = new PatternLayout("%m%n");
        RollingFileAppender fileAppender = new RollingFileAppender(layout, filePath);
        fileAppender.setName("test");
        fileAppender.setAppend(true);
        return fileAppender;
    }

    private boolean isFileContainsText(String path, String line) throws IOException {
        if (!new File(path).exists()) {
            return false;
        }
        return String.join("", Files.readAllLines(new File(path).toPath(), StandardCharsets.UTF_8)).contains(line);
    }

    @AfterMethod
    private void removeFileAppenderFromLogger() {
        Logger.getInstance().removeAppender(messagesTestsAppender);
    }
}
