package automationpractice.forms;

import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class ChallengingDomForm extends Form {

    public static final String LOCATOR_CHALLENGING_DOM_FORM = "//h3[contains(text(),'Challenging DOM')]";
    public static final String ELEMENT_NAME_ROW3_COLUMN5 = "Cell in row 3 column 5";
    public static final String ELEMENT_NAME_ROW5_COLUMN5 = "Cell in row 5 column 5";
    public static final String ELEMENT_NAME_ROW7_COLUMN5 = "Cell in row 7 column 5";
    public static final String ELEMENT_NAME_ROW5_COLUMN7 = "Cell in row 5 column 7";
    public static final String ELEMENT_NAME_ROW5_COLUMN3 = "Cell in row 5 column 3";
    public static final String ELEMENT_NAME_ROW1_COLUMN1 = "Cell in row 1 column 1";
    public static final String ELEMENT_NAME_ROW2_COLUMN1 = "Cell in row 2 column 1";
    public static final String ELEMENT_NAME_HEADER_CHALLENGING_DOM = "Header of Challenging Dom";

    private final String locatorCellRow5Column5 = "//tr[5]/td[5]";
    private final String locatorCellRow3Column5 = "//tr[3]/td[5]";
    private final String locatorCellRow7Column5 = "//tr[7]/td[5]";
    private final String locatorCellRow5Column3 = "//tr[5]/td[3]";
    private final String locatorCellRow5Column7 = "//tr[5]/td[7]";
    private final String locatorCellRow1Column1 = "//tr[1]/td[1]";
    private final String locatorCellRow2Column1 = "//tr[2]/td[1]";

    private ILabel headerName = getElementFactory().getLabel(By.xpath(LOCATOR_CHALLENGING_DOM_FORM), ELEMENT_NAME_HEADER_CHALLENGING_DOM);
    private ILabel cellInRow3Column5 = getElementFactory().getLabel(By.xpath(locatorCellRow3Column5), ELEMENT_NAME_ROW3_COLUMN5);
    private ILabel cellInRow5Column5 = getElementFactory().getLabel(By.xpath(locatorCellRow5Column5), ELEMENT_NAME_ROW5_COLUMN5);
    private ILabel cellInRow7Column5 = getElementFactory().getLabel(By.xpath(locatorCellRow7Column5), ELEMENT_NAME_ROW7_COLUMN5);
    private ILabel cellInRow5Column7 = getElementFactory().getLabel(By.xpath(locatorCellRow5Column7), ELEMENT_NAME_ROW5_COLUMN7);
    private ILabel cellInRow5Column3 = getElementFactory().getLabel(By.xpath(locatorCellRow5Column3), ELEMENT_NAME_ROW5_COLUMN3);
    private ILabel cellInRow1Column1 = getElementFactory().getLabel(By.xpath(locatorCellRow1Column1), ELEMENT_NAME_ROW1_COLUMN1);
    private ILabel cellInRow2Column1 = getElementFactory().getLabel(By.xpath(locatorCellRow2Column1), ELEMENT_NAME_ROW2_COLUMN1);

    public ChallengingDomForm() {
        super(By.xpath(LOCATOR_CHALLENGING_DOM_FORM), "Challenging DOM");
    }

    public ILabel getCellInRow3Column5() {
        return cellInRow3Column5;
    }

    public ILabel getCellInRow5Column5() {
        return cellInRow5Column5;
    }

    public ILabel getCellInRow7Column5() {
        return cellInRow7Column5;
    }

    public ILabel getCellInRow5Column7() {
        return cellInRow5Column7;
    }

    public ILabel getCellInRow5Column3() {
        return cellInRow5Column3;
    }

    public ILabel getCellInRow1Column1() {
        return cellInRow1Column1;
    }

    public ILabel getCellInRow2Column1() {
        return cellInRow2Column1;
    }

    public ILabel getHeaderName() {
        return headerName;
    }

    public String getLocatorCellRow1Column1() {
        return locatorCellRow1Column1;
    }

    public String getLocatorCellRow5Column5() {
        return locatorCellRow5Column5;
    }

    public String getLocatorCellRow3Column5() {
        return locatorCellRow3Column5;
    }

    public String getLocatorCellRow7Column5() {
        return locatorCellRow7Column5;
    }

    public String getLocatorCellRow5Column3() {
        return locatorCellRow5Column3;
    }

    public String getLocatorCellRow5Column7() {
        return locatorCellRow5Column7;
    }
}
