package forms;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class MyLocationForm extends Form {

    private final ILabel lblLatitude = getElementFactory().getLabel(By.id("latitude"), "Latitude");
    private final ILabel lblLongitude = getElementFactory().getLabel(By.id("longitude"), "Longitude");
    private final IButton btnConsent = getElementFactory().getButton(By.xpath("//button[@aria-label='Consent']"), "Consent");

    public MyLocationForm() {
        super(By.xpath("//h1[contains(text(),'My Location')]"), "My Location");
    }

    public double getLatitude() {
        if (!lblLatitude.state().isDisplayed() && btnConsent.state().isDisplayed()) {
            clickConsent();
        }
        return Double.parseDouble(lblLatitude.getText());
    }

    public void clickConsent() {
        btnConsent.click();
    }

    public double getLongitude() {
        return Double.parseDouble(lblLongitude.getText());
    }
}
