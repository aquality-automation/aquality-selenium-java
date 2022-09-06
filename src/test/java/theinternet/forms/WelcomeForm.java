package theinternet.forms;

import aquality.selenium.elements.interfaces.ILabel;
import org.openqa.selenium.By;

public class WelcomeForm extends TheInternetForm {
    private final ILabel lblSubtitle = getElementFactory().getLabel(By.xpath("//h2"), "Sub title");

    public WelcomeForm() {
        super(By.xpath("//h1[contains(.,'Welcome to the-internet')]"), "Welcome to the-internet");
    }

    @Override
    protected String getUri() {
        return "";
    }

    public ILabel getSubTitleLabel() {
        return lblSubtitle;
    }
}
