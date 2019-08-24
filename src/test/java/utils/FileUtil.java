package utils;

import aquality.selenium.browser.BrowserManager;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.logger.Logger;
import org.openqa.selenium.WebDriverException;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {

    public static String getProjectBaseDir(){
        return  System.getProperty("user.dir") != null ? System.getProperty("user.dir") : System.getProperty("project.basedir");
    }

    public static File getResourceFileByName(String fileName){
        return Paths.get(getProjectBaseDir(), "/src/test/resources/" + fileName).toFile();
    }

    public static void deleteFile(File file){
        if(file.exists()){
            try {
                Files.delete(Paths.get(file.getCanonicalPath()));
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }

    public static boolean isFileDownloaded(String fileAddress, ILabel lblFileContent) {
        try {
            BrowserManager.getBrowser().goTo(fileAddress);
            return lblFileContent.state().isDisplayed();
        } catch (WebDriverException e) {
            Logger.getInstance().warn(e.getMessage());
            return false;
        }
    }

    public static String getTargetFilePath(String fileName) {
        String downloadDirectory = BrowserManager.getBrowser().getDownloadDirectory();

        // below is workaround for case when local FS is different from remote (e.g. local machine runs on Windows but remote runs on Linux)
        if(downloadDirectory.contains("/") && !downloadDirectory.endsWith("/")) {
            downloadDirectory = downloadDirectory.concat("/");
        }
        if(downloadDirectory.contains("\\") && !downloadDirectory.endsWith("\\")) {
            downloadDirectory = downloadDirectory.concat("\\");
        }
        return downloadDirectory.concat(fileName);
    }
}
