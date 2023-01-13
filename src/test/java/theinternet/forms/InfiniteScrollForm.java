package theinternet.forms;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.configuration.ITimeoutConfiguration;
import aquality.selenium.elements.interfaces.ILabel;
import org.openqa.selenium.By;

import java.util.List;
import java.util.function.Supplier;

import static aquality.selenium.browser.AqualityServices.getConditionalWait;

public class InfiniteScrollForm extends TheInternetForm {
    private final Supplier<List<ILabel>> getExampleLabels = () -> getElementFactory().findElements(By.xpath("//div[contains(@class,'jscroll-added')]"), "Example", ILabel.class);

    public InfiniteScrollForm(){
        super(By.xpath("//div[@id='content' and .//h3[contains(.,'Infinite Scroll')]]"), "Infinite Scroll");
    }

    public List<ILabel> getExampleLabels() {
        return getExampleLabels.get();
    }

    public ILabel getLastExampleLabel() {
        List<ILabel> labels = getExampleLabels.get();
        return labels.get(labels.size() - 1);
    }

    public void waitForMoreExamples() {
        int examplesCount = getExampleLabels().size();
        getConditionalWait().waitFor(() -> examplesCount != getExampleLabels().size(),
                AqualityServices.get(ITimeoutConfiguration.class).getScript());
    }

    @Override
    protected String getUri() {
        return "/infinite_scroll";
    }
}
