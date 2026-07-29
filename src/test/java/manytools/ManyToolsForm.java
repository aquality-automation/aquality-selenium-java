package manytools;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.elements.ElementState;
import aquality.selenium.core.utilities.IActionRetrier;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

import java.util.Collections;

public abstract class ManyToolsForm<T extends ManyToolsForm<T>> extends Form {
    private static final String BASE_URL = "https://manytools.org/";
    private final ILabel lblValue = getFormLabel().findChildElement(By.xpath(".//code"), getName(), ILabel.class, ElementState.EXISTS_IN_ANY_STATE);
    private final ILabel lblConsentDialog = getElementFactory().getLabel(By.id("cmpwrapper"), "Cookie consent dialog", ElementState.EXISTS_IN_ANY_STATE);
    private final IButton btnDecline = lblConsentDialog.findElementInShadowRoot(By.id("cmpbntnotxt"), "Decline cookies", IButton.class);

    protected ManyToolsForm(String name) {
        super(By.id("maincontent"), name);
    }

    protected abstract String getUrlPart();

    @SuppressWarnings("unchecked")
    public T open() {
        AqualityServices.get(IActionRetrier.class).doWithRetry(() -> {
            AqualityServices.getBrowser().goTo(BASE_URL + getUrlPart());
            state().waitForDisplayed();
        }, Collections.singletonList(TimeoutException.class));

        if (lblConsentDialog.state().isExist() && btnDecline.state().isDisplayed()) {
            btnDecline.click();
            lblConsentDialog.state().waitForNotDisplayed();
        }
        return (T) this;
    }

    public String getValue() {
        return lblValue.getText();
    }
}
