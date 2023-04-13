package forms;

import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class MyLocationForm extends Form {

    private final ILabel lblLatitude = getElementFactory().getLabel(By.id("latitude"), "Latitude");
    private final ILabel lblLongitude = getElementFactory().getLabel(By.id("longitude"), "Longitude");

    public MyLocationForm() {
        super(By.xpath("//h1[contains(text(),'My Location')]"), "My Location");
    }

    public double getLatitude() {
       return Double.parseDouble(lblLatitude.getText());
    }

    public double getLongitude() {
        return Double.parseDouble(lblLongitude.getText());
    }
}
