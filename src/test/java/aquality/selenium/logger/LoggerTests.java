package aquality.selenium.logger;

import org.apache.log4j.*;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;

import static org.testng.Assert.*;

public class LoggerTests {

    private final static String appenderLogFilePattern = "target/log/appender-%s.log";
    private final static String testMessage = "test message";
    private final static String testExceptionText = "test exception";
    private final static String log4jFieldName = "log4J";
    private org.apache.log4j.Logger log4j;
    private Appender appender;
    private File appenderFile;

    @BeforeMethod
    private void addMessagesAppender() throws IOException {
        appenderFile = getRandomAppenderFile();
        appender = getFileAppender(appenderFile);
        Logger.getInstance().addAppender(appender);
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
        Logger.getInstance().addAppender(appender).info(testMessage);
        assertTrue(appenderFile.exists(), String.format("New appender is not added to log4j. File '%s' is not created.", appenderFile.getPath()));
        assertTrue(isFileContainsText(appenderFile, testMessage), String.format("Log '%s' doesn't contains message '%s'.", appenderFile.getPath(), testMessage));
    }

    @Test
    public void testShouldBePossibleToRemoveAppender() throws IOException {
        Logger.getInstance().addAppender(appender).removeAppender(appender).info(testMessage);
        if(appenderFile.exists()){
            assertFalse(isFileContainsText(appenderFile, testMessage), String.format("New appender is not removed from log4j. File '%s' is not empty.", appenderFile.getPath()));
        }
    }

    @Test(groups = "messages")
    public void testInfoMessageShouldBeDisplayedAccordingToLogLevel() throws IOException {
        log4j.setLevel(Level.FATAL);
        Logger.getInstance().info(testMessage);
        assertFalse(isFileContainsText(appenderFile, testMessage));

        log4j.setLevel(Level.INFO);
        Logger.getInstance().info(testMessage);
        assertTrue(isFileContainsText(appenderFile, testMessage));
    }

    @Test(groups = "messages")
    public void testInfoMessageWithParametersShouldBeDisplayedAccordingToLogLevel() throws IOException {
        log4j.setLevel(Level.FATAL);
        Logger.getInstance().info("%s", testMessage);
        assertFalse(isFileContainsText(appenderFile, testMessage));

        log4j.setLevel(Level.INFO);
        Logger.getInstance().info("%s", testMessage);
        assertTrue(isFileContainsText(appenderFile, testMessage));
    }

    @Test(groups = "messages")
    public void testDebugMessageWithParametersShouldBeDisplayedAccordingToLogLevel() throws IOException {
        log4j.setLevel(Level.WARN);
        Logger.getInstance().debug("%s", testMessage);
        assertFalse(isFileContainsText(appenderFile, testMessage));

        log4j.setLevel(Level.DEBUG);
        Logger.getInstance().debug("%s", testMessage);
        assertTrue(isFileContainsText(appenderFile, testMessage));
    }

    @Test(groups = "messages")
    public void testDebugMessageShouldBeDisplayedAccordingToLogLevel() throws IOException {
        log4j.setLevel(Level.WARN);
        Logger.getInstance().debug(testMessage);
        assertFalse(isFileContainsText(appenderFile, testMessage));

        log4j.setLevel(Level.DEBUG);
        Logger.getInstance().debug(testMessage);
        assertTrue(isFileContainsText(appenderFile, testMessage));
    }

    @Test(groups = "messages")
    public void testDebugMessageWithThrowableShouldBeDisplayedAccordingToLogLevel() throws IOException {
        log4j.setLevel(Level.WARN);
        Logger.getInstance().debug(testMessage, new Exception(testExceptionText));
        assertFalse(isFileContainsText(appenderFile, testMessage));
        assertFalse(isFileContainsText(appenderFile, testExceptionText));

        log4j.setLevel(Level.DEBUG);
        Logger.getInstance().debug(testMessage, new Exception(testExceptionText));
        assertTrue(isFileContainsText(appenderFile, testMessage));
        assertTrue(isFileContainsText(appenderFile, testExceptionText));
    }

    @Test(groups = "messages")
    public void testWarnMessageShouldBeDisplayedAccordingToLogLevel() throws IOException {
        log4j.setLevel(Level.ERROR);
        Logger.getInstance().warn(testMessage);
        assertFalse(isFileContainsText(appenderFile, testMessage));

        log4j.setLevel(Level.WARN);
        Logger.getInstance().warn(testMessage);
        assertTrue(isFileContainsText(appenderFile, testMessage));
    }

    @Test(groups = "messages")
    public void testFatalMessageShouldBeDisplayedAccordingToLogLevel() throws IOException {
        log4j.setLevel(Level.OFF);
        Logger.getInstance().fatal(testMessage, new Exception(testExceptionText));
        assertFalse(isFileContainsText(appenderFile, testMessage));
        assertFalse(isFileContainsText(appenderFile, testExceptionText));

        log4j.setLevel(Level.FATAL);
        Logger.getInstance().fatal(testMessage, new Exception(testExceptionText));
        assertTrue(isFileContainsText(appenderFile, testMessage));
        assertTrue(isFileContainsText(appenderFile, testExceptionText));
    }

    @Test(groups = "messages")
    public void testErrorMessageShouldBeDisplayedAccordingToLogLevel() throws IOException {
        log4j.setLevel(Level.FATAL);
        Logger.getInstance().error(testMessage);
        assertFalse(isFileContainsText(appenderFile, testMessage));

        log4j.setLevel(Level.ERROR);
        Logger.getInstance().error(testMessage);
        assertTrue(isFileContainsText(appenderFile, testMessage));
    }

    private Appender getFileAppender(File file) throws IOException {
        Layout layout = new PatternLayout("%m%n");
        RollingFileAppender fileAppender = new RollingFileAppender(layout, file.getPath());
        fileAppender.setName("test");
        fileAppender.setAppend(true);
        return fileAppender;
    }

    private boolean isFileContainsText(File file, String line) throws IOException {
        if (!file.exists()) {
            return false;
        }
        return String.join("", Files.readAllLines(file.toPath(), StandardCharsets.UTF_8)).contains(line);
    }

    private File getRandomAppenderFile() {
        return new File(String.format(appenderLogFilePattern, UUID.randomUUID()));
    }

    @AfterMethod
    private void removeFileAppenderFromLogger() {
        Logger.getInstance().removeAppender(appender);
    }
}
