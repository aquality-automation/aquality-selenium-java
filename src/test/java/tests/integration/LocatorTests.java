package tests.integration;

import aquality.selenium.elements.interfaces.ILabel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.BaseTest;
import theinternet.TheInternetPage;

import static aquality.selenium.locators.RelativeBySupplier.with;

public class LocatorTests extends BaseTest {
    private final String labelLocatorCell = "//td";
    private final String labelLocatorCellRow5Column5 = "//tr[5]/td[5]";
    private final String labelLocatorCellRow3Column5 = "//tr[3]/td[5]";
    private final String nameElementRow3Column5 = "expectedRow3Column5GotWithByXpath";
    private final String friendlyMessage = "Actual cell text is not equal expected";

    @Test
    public void testAboveLocatorWithDifferentAboveParametersType() {
        navigate(TheInternetPage.CHALLENGING_DOM);

        ILabel cellInRow3Column5 = elementFactory.getLabel(By.xpath(labelLocatorCellRow3Column5), "CellInRow4Column5");
        ILabel cellInRow5Column5 = elementFactory.getLabel(By.xpath(labelLocatorCellRow5Column5), "CellInRow5Column5");

        ILabel actualCellRaw3Column5GotWithByXpath =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell)).above(By.xpath(labelLocatorCellRow5Column5)),
                        nameElementRow3Column5);

        ILabel actualCellRaw3Column5GotWithWebElement =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell)).above(cellInRow5Column5.getElement()),
                        nameElementRow3Column5);

        ILabel actualCellRaw3Column5GotWithAqualityElement =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell)).above(cellInRow5Column5),
                        nameElementRow3Column5);

        WebElement actualWebElementCellRaw3Column5GotBySeleniumRelative =
                getBrowser().getDriver().findElement(RelativeLocator.with(By.xpath(labelLocatorCell)).above(By.xpath(labelLocatorCellRow5Column5)));

        checkDifferentTypesWithSoftAssert(
                actualCellRaw3Column5GotWithAqualityElement.getText(),
                actualCellRaw3Column5GotWithWebElement.getText(),
                actualCellRaw3Column5GotWithByXpath.getText(),
                actualWebElementCellRaw3Column5GotBySeleniumRelative.getText(),
                cellInRow3Column5.getText());
    }

    public void checkDifferentTypesWithSoftAssert(String textAquality, String textWebElement, String textByXpath, String textSelenium, String expectedText) {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(textAquality, expectedText, friendlyMessage);
        softAssert.assertEquals(textByXpath, expectedText, friendlyMessage);
        softAssert.assertEquals(textWebElement, expectedText, friendlyMessage);
        softAssert.assertEquals(textSelenium, expectedText, friendlyMessage);
        softAssert.assertAll();
    }
}
