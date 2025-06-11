package automationpractice.forms;

import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class ChallengingDomForm extends Form {

    public static final String LOCATOR_CHALLENGING_DOM_FORM = "//h3[contains(text(),'Challenging DOM')]";

    public ChallengingDomForm() {
        super(By.xpath(LOCATOR_CHALLENGING_DOM_FORM), "Challenging Dom");
    }

    public String getHeaderText() {
        return getFormLabel().getText();
    }

    public ILabel getCell(int row, int column) {
        return getElementFactory().getLabel(By.xpath(String.format("//tr[%d]/td[%d]", row, column)), String.format("Cell at row %d and column %d", row, column));
    }

}
