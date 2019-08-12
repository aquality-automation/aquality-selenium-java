package tests.usecases;

import aquality.selenium.browser.BrowserManager;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.waitings.ConditionalWait;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseTest;
import theinternet.TheInternetPage;
import theinternet.forms.FileDownloaderForm;
import utils.FileUtil;

import java.io.File;
import java.util.ArrayList;

public class FileDownloadingTests extends BaseTest {
    @Test
    public void testDownloading() {
        FileDownloaderForm downloaderForm = new FileDownloaderForm();
        String fileName = downloaderForm.getFileName();
        String filePath = FileUtil.getTargetFilePath(fileName);
        File file = new File(filePath);

        FileUtil.deleteFile(file);

        String fileAddress = "file://".concat(filePath);
        ILabel lblFileContent = elementFactory.getLabel(By.xpath("//pre"), "text file content");
        Assert.assertFalse(FileUtil.isFileDownloaded(fileAddress, lblFileContent), "file should not exist before downloading");
        ((JavascriptExecutor) BrowserManager.getBrowser().getDriver()).executeScript("window.open()");
        ArrayList<String> tabs = new ArrayList<>(BrowserManager.getBrowser().getDriver().getWindowHandles());
        BrowserManager.getBrowser().getDriver().switchTo().window(tabs.get(0));
        BrowserManager.getBrowser().goTo(TheInternetPage.DOWNLOAD.getAddress());
        downloaderForm.getLnkDownload(fileName).clickAndWait();

        BrowserManager.getBrowser().getDriver().switchTo().window(tabs.get(1));

        boolean isContentDisplayed = ConditionalWait.waitForTrue(webDriver -> FileUtil.isFileDownloaded(fileAddress, lblFileContent));

        Assert.assertTrue(isContentDisplayed, "file was not downloaded to correct directory. Element '" + lblFileContent.getLocator() + "' was not displayed");
    }


}