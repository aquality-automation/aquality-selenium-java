package tests.integration;

import aquality.selenium.elements.interfaces.ILabel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.BaseTest;
import theinternet.TheInternetPage;

import static aquality.selenium.locators.RelativeBySupplier.with;

public class LocatorTests extends BaseTest {
    private final String labelLocatorCell = "//td";
    private final String labelLocatorCellRow5Column5 = "//tr[5]/td[5]";
    private final String labelLocatorCellRow3Column5 = "//tr[3]/td[5]";
    private final String labelLocatorCellRow7Column5 = "//tr[7]/td[5]";
    private final String labelLocatorCellRow5Column3 = "//tr[5]/td[3]";
    private final String labelLocatorCellRow5Column7 = "//tr[5]/td[7]";
    private final String nameElementRow3Column5 = "expectedRow3Column5GotWithByXpath";
    private final String nameElementRow7Column5 = "expectedRow7Column5GotWithByXpath";
    private final String nameElementRow5Column3 = "expectedRow5Column3GotWithByXpath";
    private final String nameElementRow5Column7 = "expectedRow5Column7GotWithByXpath";
    private final String nameElementRow5Column5 = "expectedRow5Column5";
    private final String friendlyMessage = "Actual cell text is not equal expected";

    @BeforeMethod
    public void beforeMethod() {
        navigate(TheInternetPage.CHALLENGING_DOM);
    }

    @Test
    public void testAboveLocatorWithDifferentAboveParametersType() {

        ILabel cellInRow3Column5 = elementFactory.getLabel(By.xpath(labelLocatorCellRow3Column5), "CellInRow3Column5");
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

    @Test
    public void testBelowLocatorWithDifferentBelowParametersType () {

        ILabel cellInRow7Column5 = elementFactory.getLabel(By.xpath(labelLocatorCellRow7Column5), "CellInRow7Column5");
        ILabel cellInRow5Column5 = elementFactory.getLabel(By.xpath(labelLocatorCellRow5Column5), "CellInRow5Column5");

        ILabel actualCellRaw7Column5GotWithByXpath =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell)).below(By.xpath(labelLocatorCellRow5Column5)),
                        nameElementRow7Column5);

        ILabel actualCellRaw7Column5GotWithWebElement =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell)).below(cellInRow5Column5.getElement()),
                        nameElementRow7Column5);

        ILabel actualCellRaw7Column5GotWithAqualityElement =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell)).below(cellInRow5Column5),
                        nameElementRow7Column5);

        WebElement actualWebElementCellRaw7Column5GotBySeleniumRelative =
                getBrowser().getDriver().findElement(RelativeLocator.with(By.xpath(labelLocatorCell)).below(By.xpath(labelLocatorCellRow5Column5)));

        checkDifferentTypesWithSoftAssert(
                actualCellRaw7Column5GotWithAqualityElement.getText(),
                actualCellRaw7Column5GotWithWebElement.getText(),
                actualCellRaw7Column5GotWithByXpath.getText(),
                actualWebElementCellRaw7Column5GotBySeleniumRelative.getText(),
                cellInRow7Column5.getText());
    }


    @Test
    public void testToLeftOfLocatorWithDifferentToLeftOfParametersType () {

        ILabel cellInRow5Column3 = elementFactory.getLabel(By.xpath(labelLocatorCellRow5Column3), "CellInRow5Column3");
        ILabel cellInRow5Column5 = elementFactory.getLabel(By.xpath(labelLocatorCellRow5Column5), "CellInRow5Column5");

        ILabel actualCellRaw5Column3GotWithByXpath =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell)).toLeftOf(By.xpath(labelLocatorCellRow5Column5)),
                        nameElementRow5Column3);

        ILabel actualCellRaw5Column3GotWithWebElement =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell)).toLeftOf(cellInRow5Column5.getElement()),
                        nameElementRow5Column3);

        ILabel actualCellRaw5Column3GotWithAqualityElement =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell)).toLeftOf(cellInRow5Column5),
                        nameElementRow5Column3);

        WebElement actualWebElementCellRaw5Column3GotBySeleniumRelative =
                getBrowser().getDriver().findElement(RelativeLocator.with(By.xpath(labelLocatorCell)).toLeftOf(By.xpath(labelLocatorCellRow5Column5)));

        checkDifferentTypesWithSoftAssert(
                actualCellRaw5Column3GotWithAqualityElement.getText(),
                actualCellRaw5Column3GotWithWebElement.getText(),
                actualCellRaw5Column3GotWithByXpath.getText(),
                actualWebElementCellRaw5Column3GotBySeleniumRelative.getText(),
                cellInRow5Column3.getText());
    }

    @Test
    public void testToRightOfLocatorWithDifferentToRightOfParametersType () {

        ILabel cellInRow5Column7 = elementFactory.getLabel(By.xpath(labelLocatorCellRow5Column7), "CellInRow5Column7");
        ILabel cellInRow5Column5 = elementFactory.getLabel(By.xpath(labelLocatorCellRow5Column5), "CellInRow5Column5");

        ILabel actualCellRaw5Column7GotWithByXpath =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell)).toRightOf(By.xpath(labelLocatorCellRow5Column5)),
                        nameElementRow5Column7);

        ILabel actualCellRaw5Column7GotWithWebElement =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell)).toRightOf(cellInRow5Column5.getElement()),
                        nameElementRow5Column3);

        ILabel actualCellRaw5Column7GotWithAqualityElement =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell)).toRightOf(cellInRow5Column5),
                        nameElementRow5Column3);

        WebElement actualWebElementCellRaw5Column7GotBySeleniumRelative =
                getBrowser().getDriver().findElement(RelativeLocator.with(By.xpath(labelLocatorCell)).toRightOf(By.xpath(labelLocatorCellRow5Column5)));

        checkDifferentTypesWithSoftAssert(
                actualCellRaw5Column7GotWithAqualityElement.getText(),
                actualCellRaw5Column7GotWithWebElement.getText(),
                actualCellRaw5Column7GotWithByXpath.getText(),
                actualWebElementCellRaw5Column7GotBySeleniumRelative.getText(),
                cellInRow5Column7.getText());
    }

    @Test
    public void testAboveBelowLeftRight() {

        ILabel cellInRow3Column5 = elementFactory.getLabel(By.xpath(labelLocatorCellRow3Column5), "CellInRow3Column5");
        ILabel cellInRow5Column7 = elementFactory.getLabel(By.xpath(labelLocatorCellRow5Column7), "CellInRow5Column7");
        ILabel cellInRow5Column5 = elementFactory.getLabel(By.xpath(labelLocatorCellRow5Column5), "CellInRow5Column5");
        ILabel cellInRow5Column3 = elementFactory.getLabel(By.xpath(labelLocatorCellRow5Column3), "CellInRow5Column3");
        ILabel cellInRow7Column5 = elementFactory.getLabel(By.xpath(labelLocatorCellRow7Column5), "CellInRow7Column5");

        ILabel actualCellRaw5Column5GotWithAqualityElement =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell))
                        .above(cellInRow7Column5)
                        .below(cellInRow3Column5)
                        .toRightOf(cellInRow5Column3)
                        .toLeftOf(cellInRow5Column7)
                        .above(cellInRow7Column5), nameElementRow5Column5);

        ILabel actualCellRaw5Column5GotWithWebElement =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell))
                        .above(cellInRow7Column5.getElement())
                        .below(cellInRow3Column5.getElement())
                        .toRightOf(cellInRow5Column3.getElement())
                        .toLeftOf(cellInRow5Column7.getElement())
                        .above(cellInRow7Column5.getElement())
                        , nameElementRow5Column5);

        ILabel actualCellRaw5Column5GotWithXpath =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell))
                                .above(By.xpath(labelLocatorCellRow7Column5))
                                .below(By.xpath(labelLocatorCellRow3Column5))
                                .toRightOf(By.xpath(labelLocatorCellRow5Column3))
                                .toLeftOf(By.xpath(labelLocatorCellRow5Column7))
                                .above(By.xpath(labelLocatorCellRow7Column5))
                        , nameElementRow5Column5);

        WebElement actualWebElementCellRaw5Column5GotBySeleniumRelative =
                getBrowser().getDriver().findElement(RelativeLocator.with(By.xpath(labelLocatorCell))
                        .above(By.xpath(labelLocatorCellRow7Column5))
                        .below(By.xpath(labelLocatorCellRow3Column5))
                        .toRightOf(By.xpath(labelLocatorCellRow5Column3))
                        .toLeftOf(By.xpath(labelLocatorCellRow5Column7))
                        .above(By.xpath(labelLocatorCellRow7Column5))
                        );

        checkDifferentTypesWithSoftAssert(
                actualCellRaw5Column5GotWithAqualityElement.getText(),
                actualCellRaw5Column5GotWithXpath.getText(),
                actualCellRaw5Column5GotWithWebElement.getText(),
                actualWebElementCellRaw5Column5GotBySeleniumRelative.getText(),
                cellInRow5Column5.getText());
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
