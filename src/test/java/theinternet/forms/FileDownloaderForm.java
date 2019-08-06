package theinternet.forms;

import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import aquality.selenium.forms.PageInfo;
import org.openqa.selenium.By;

@PageInfo(id = "content", pageName = "File Downloader")
public class FileDownloaderForm extends Form {
    private static final String DEFAULT_FILE_NAME = "some-file.txt";
    private static final String LINK_TEMPLATE = "//a[contains(@href,'%s')]";

    public ILink getLnkDownload(String fileName) {
        return getElementFactory().getLink(By.xpath(String.format(LINK_TEMPLATE, fileName)),
                "Download file ".concat(fileName));
    }

    public String getFileName() {
        return DEFAULT_FILE_NAME;
    }
}
