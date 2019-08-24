package theinternet.forms;

import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class FileDownloaderForm extends Form {
    private static final String DEFAULT_FILE_NAME = "some-file.txt";
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
}
