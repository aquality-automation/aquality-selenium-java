package theinternet.forms;

import aquality.selenium.forms.Form;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ILabel;
import org.openqa.selenium.By;

import java.time.Duration;

public class DynamicLoadingForm extends Form {

    private static final Duration DEFAULT_LOADING_TIMEOUT = Duration.ofSeconds(5L);

    private final IButton btnStart = getElementFactory().getButton(By.xpath("//div[@id='start']/button"), "Start");
    private final ILabel lblLoading = getElementFactory().getLabel(By.id("loading"), "loading");
    private final ILabel lblFinish = getElementFactory().getLabel(By.id("finish"), "finish");

    public DynamicLoadingForm() {
        super(By.id("content"), "Dynamic Loading");
    }

    public ILabel getLblLoading() {
        return lblLoading;
    }

    public IButton getBtnStart() {
        return btnStart;
    }

    public Duration getTimeout() {
        return DEFAULT_LOADING_TIMEOUT;
    }

    public ILabel getLblFinish() {
        return lblFinish;
    }
}
