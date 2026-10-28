package theinternet.forms;

import aquality.selenium.browser.AlertActions;
import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.logging.Logger;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.UnhandledAlertException;
import theinternet.TheInternetPage;

public class FileDownloaderForm extends Form {
    private static final String DEFAULT_FILE_NAME = "some-file.txt";
    private static final int MAX_ALERTS_COUNT = 100;
    private static final String LINK_TEMPLATE = "//a[contains(@href,'%s')]";

    public FileDownloaderForm() {
        super(By.id("content"), "File Downloader");
    }

    public ILink getLnkDownload(String fileName) {
        return getElementFactory().getLink(By.xpath(String.format(LINK_TEMPLATE, fileName)),
                "Download file ".concat(fileName));
    }

    public String getFileName() {
        return DEFAULT_FILE_NAME;
    }

    public void open() {
        String url = TheInternetPage.DOWNLOAD.getAddress();
        try {
            AqualityServices.getBrowser().goTo(url);
            AqualityServices.getBrowser().waitForPageToLoad();
        } catch (UnhandledAlertException e) {
            Logger.getInstance().debug("Unhandled alert detected", e);
            for (int i = 0; i < MAX_ALERTS_COUNT; i++)
            {
                try
                {
                    AqualityServices.getBrowser().handleAlert(AlertActions.ACCEPT);
                }
                catch (NoAlertPresentException noAlertPresentException)
                {
                    Logger.getInstance().debug("No more alerts present", noAlertPresentException);
                    break;
                }
            }
            if (!url.equals(AqualityServices.getBrowser().getCurrentUrl()))
            {
                throw new NoSuchWindowException(url + " was not opened", e);
            }
            AqualityServices.getBrowser().waitForPageToLoad();
        }
    }
}
