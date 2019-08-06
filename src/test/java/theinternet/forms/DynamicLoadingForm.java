package theinternet.forms;

import aquality.selenium.forms.Form;
import aquality.selenium.forms.PageInfo;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ILabel;
import org.openqa.selenium.By;

@PageInfo(id = "content", pageName = "Dynamic Loading")
public class DynamicLoadingForm extends Form {

    private static final long DEFAULT_LOADING_TIMEOUT = 5L;

    private final IButton btnStart = getElementFactory().getButton(By.xpath("//div[@id='start']/button"), "Start");
    private final ILabel lblLoading = getElementFactory().getLabel(By.id("loading"), "loading");
    private final ILabel lblFinish = getElementFactory().getLabel(By.id("finish"), "finish");

    public ILabel getLblLoading() {
        return lblLoading;
    }

    public IButton getBtnStart() {
        return btnStart;
    }

    public long getTimeout() {
        return DEFAULT_LOADING_TIMEOUT;
    }

    public ILabel getLblFinish() {
        return lblFinish;
    }
}
