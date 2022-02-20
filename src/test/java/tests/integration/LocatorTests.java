package tests.integration;

import aquality.selenium.elements.interfaces.ILabel;
import automationpractice.forms.ChallengingDomForm;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.BaseTest;
import theinternet.TheInternetPage;
import java.util.List;
import static aquality.selenium.locators.RelativeBySupplier.with;

public class LocatorTests extends BaseTest {
    private final ChallengingDomForm challengingDomForm = new ChallengingDomForm();
    private final String labelLocatorCell = "//td";
    private final String nameElementRow3Column5 = "expectedRow3Column5";
    private final String nameElementRow7Column5 = "expectedRow7Column5";
    private final String nameElementRow5Column3 = "expectedRow5Column3";
    private final String nameElementRow5Column7 = "expectedRow5Column7";
    private final String nameElementRow5Column5 = "expectedRow5Column5";
    private final String nameElementRow2Column1 = "expectedRow2Column1";
    private final String nameElementHeaderOfPage = "Challenging Dom";
    private final String friendlyMessageEquallingText = "Actual cell text is not equal expected";
    private final String friendlyMessageElementFound = "Element with not reachable distance is exist";
    private final int positiveDistance = 300;
    private final int negativeDistance = 100;

    @BeforeMethod
    public void beforeMethod() {
        navigate(TheInternetPage.CHALLENGING_DOM);
    }

    @Test
    public void testAboveLocatorWithDifferentAboveParametersType() {
        ILabel cellInRow5Column5 = challengingDomForm.getCellInRow5Column5();

        ILabel actualCellRaw3Column5GotWithByXpath =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell)).above(By.xpath(challengingDomForm.getLocatorCellRow5Column5())),
                        nameElementRow3Column5);

        ILabel actualCellRaw3Column5GotWithWebElement =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell)).above(cellInRow5Column5.getElement()),
                        nameElementRow3Column5);

        ILabel actualCellRaw3Column5GotWithAqualityElement =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell)).above(cellInRow5Column5),
                        nameElementRow3Column5);

        WebElement actualWebElementCellRaw3Column5GotBySeleniumRelative =
                getBrowser().getDriver().findElement(RelativeLocator
                        .with(By.xpath(labelLocatorCell))
                        .above(By.xpath(challengingDomForm.getLocatorCellRow5Column5())));

        checkDifferentTypesWithSoftAssert(
                actualCellRaw3Column5GotWithAqualityElement.getText(),
                actualCellRaw3Column5GotWithWebElement.getText(),
                actualCellRaw3Column5GotWithByXpath.getText(),
                actualWebElementCellRaw3Column5GotBySeleniumRelative.getText(),
                challengingDomForm.getCellInRow3Column5().getText());
    }

    @Test
    public void testBelowLocatorWithDifferentBelowParametersType() {
        ILabel cellInRow5Column5 = challengingDomForm.getCellInRow5Column5();

        ILabel actualCellRaw7Column5GotWithByXpath =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell)).below(By.xpath(challengingDomForm.getLocatorCellRow5Column5())),
                        nameElementRow7Column5);

        ILabel actualCellRaw7Column5GotWithWebElement =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell)).below(cellInRow5Column5.getElement()),
                        nameElementRow7Column5);

        ILabel actualCellRaw7Column5GotWithAqualityElement =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell)).below(cellInRow5Column5),
                        nameElementRow7Column5);

        WebElement actualWebElementCellRaw7Column5GotBySeleniumRelative =
                getBrowser().getDriver().findElement(RelativeLocator
                        .with(By.xpath(labelLocatorCell))
                        .below(By.xpath(challengingDomForm.getLocatorCellRow5Column5())));

        checkDifferentTypesWithSoftAssert(
                actualCellRaw7Column5GotWithAqualityElement.getText(),
                actualCellRaw7Column5GotWithWebElement.getText(),
                actualCellRaw7Column5GotWithByXpath.getText(),
                actualWebElementCellRaw7Column5GotBySeleniumRelative.getText(),
                challengingDomForm.getCellInRow7Column5().getText());
    }


    @Test
    public void testToLeftOfLocatorWithDifferentToLeftOfParametersType() {
        ILabel cellInRow5Column5 = challengingDomForm.getCellInRow5Column5();

        ILabel actualCellRaw5Column3GotWithByXpath =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell)).toLeftOf(By.xpath(challengingDomForm.getLocatorCellRow5Column5())),
                        nameElementRow5Column3);

        ILabel actualCellRaw5Column3GotWithWebElement =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell)).toLeftOf(cellInRow5Column5.getElement()),
                        nameElementRow5Column3);

        ILabel actualCellRaw5Column3GotWithAqualityElement =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell)).toLeftOf(cellInRow5Column5),
                        nameElementRow5Column3);

        WebElement actualWebElementCellRaw5Column3GotBySeleniumRelative =
                getBrowser().getDriver().findElement(RelativeLocator
                        .with(By.xpath(labelLocatorCell))
                        .toLeftOf(By.xpath(challengingDomForm.getLocatorCellRow5Column5())));

        checkDifferentTypesWithSoftAssert(
                actualCellRaw5Column3GotWithAqualityElement.getText(),
                actualCellRaw5Column3GotWithWebElement.getText(),
                actualCellRaw5Column3GotWithByXpath.getText(),
                actualWebElementCellRaw5Column3GotBySeleniumRelative.getText(),
                challengingDomForm.getCellInRow5Column3().getText());
    }

    @Test
    public void testToRightOfLocatorWithDifferentToRightOfParametersType() {
        ILabel cellInRow5Column5 = challengingDomForm.getCellInRow5Column5();

        ILabel actualCellRaw5Column7GotWithByXpath =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell)).toRightOf(By.xpath(challengingDomForm.getLocatorCellRow5Column5())),
                        nameElementRow5Column7);

        ILabel actualCellRaw5Column7GotWithWebElement =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell)).toRightOf(cellInRow5Column5.getElement()),
                        nameElementRow5Column3);

        ILabel actualCellRaw5Column7GotWithAqualityElement =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell)).toRightOf(cellInRow5Column5),
                        nameElementRow5Column3);

        WebElement actualWebElementCellRaw5Column7GotBySeleniumRelative =
                getBrowser().getDriver().findElement(RelativeLocator
                        .with(By.xpath(labelLocatorCell))
                        .toRightOf(By.xpath(challengingDomForm.getLocatorCellRow5Column5())));

        checkDifferentTypesWithSoftAssert(
                actualCellRaw5Column7GotWithAqualityElement.getText(),
                actualCellRaw5Column7GotWithWebElement.getText(),
                actualCellRaw5Column7GotWithByXpath.getText(),
                actualWebElementCellRaw5Column7GotBySeleniumRelative.getText(),
                challengingDomForm.getCellInRow5Column7().getText());
    }

    @Test
    public void testAboveBelowLeftRight() {
        ILabel cellInRow3Column5 = challengingDomForm.getCellInRow3Column5();
        ILabel cellInRow5Column7 = challengingDomForm.getCellInRow5Column7();
        ILabel cellInRow5Column5 = challengingDomForm.getCellInRow5Column5();
        ILabel cellInRow5Column3 = challengingDomForm.getCellInRow5Column3();
        ILabel cellInRow7Column5 = challengingDomForm.getCellInRow7Column5();

        ILabel actualCellRaw5Column5GotWithAqualityElement =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell))
                        .above(cellInRow7Column5)
                        .below(cellInRow3Column5)
                        .toRightOf(cellInRow5Column3)
                        .toLeftOf(cellInRow5Column7)
                        .above(cellInRow7Column5)
                        , nameElementRow5Column5);

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
                                .above(By.xpath(challengingDomForm.getLocatorCellRow7Column5()))
                                .below(By.xpath(challengingDomForm.getLocatorCellRow3Column5()))
                                .toRightOf(By.xpath(challengingDomForm.getLocatorCellRow5Column3()))
                                .toLeftOf(By.xpath(challengingDomForm.getLocatorCellRow5Column7()))
                                .above(By.xpath(challengingDomForm.getLocatorCellRow7Column5()))
                        , nameElementRow5Column5);

        WebElement actualWebElementCellRaw5Column5GotBySeleniumRelative =
                getBrowser().getDriver().findElement(RelativeLocator.with(By.xpath(labelLocatorCell))
                        .above(By.xpath(challengingDomForm.getLocatorCellRow7Column5()))
                        .below(By.xpath(challengingDomForm.getLocatorCellRow3Column5()))
                        .toRightOf(By.xpath(challengingDomForm.getLocatorCellRow5Column3()))
                        .toLeftOf(By.xpath(challengingDomForm.getLocatorCellRow5Column7()))
                        .above(By.xpath(challengingDomForm.getLocatorCellRow7Column5())));

        checkDifferentTypesWithSoftAssert(
                actualCellRaw5Column5GotWithAqualityElement.getText(),
                actualCellRaw5Column5GotWithWebElement.getText(),
                actualCellRaw5Column5GotWithXpath.getText(),
                actualWebElementCellRaw5Column5GotBySeleniumRelative.getText(),
                cellInRow5Column5.getText());
    }

    @Test
    public void testNear() {
        ILabel actualCellRaw2Column1GotWithAqualityElement =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell)).near(challengingDomForm.getCellInRow1Column1()),
                        nameElementRow2Column1);

        ILabel actualCellRaw2Column1GotWithWebElement =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell)).near(challengingDomForm.getCellInRow1Column1().getElement()),
                        nameElementRow2Column1);

        ILabel actualCellRaw2Column1GotWithXpath =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell)).near(By.xpath(challengingDomForm.getLocatorCellRow1Column1())),
                        nameElementRow2Column1);


        WebElement actualWebElementCellRaw2Column1GotBySeleniumRelative =
                getBrowser().getDriver().findElement(RelativeLocator.with(By.xpath(labelLocatorCell))
                        .near(By.xpath(challengingDomForm.getLocatorCellRow1Column1())));

        checkDifferentTypesWithSoftAssert(
                actualCellRaw2Column1GotWithAqualityElement.getText(),
                actualCellRaw2Column1GotWithWebElement.getText(),
                actualCellRaw2Column1GotWithXpath.getText(),
                actualWebElementCellRaw2Column1GotBySeleniumRelative.getText(),
                challengingDomForm.getCellInRow2Column1().getText());
    }

    @Test
    public void testNearWithDistance() {
        ILabel actualHeaderNameGotWithAqualityElement =
                elementFactory.getLabel(with(By.xpath(ChallengingDomForm.LOCATOR_CHALLENGING_DOM_FORM))
                                .near(challengingDomForm.getCellInRow1Column1(), positiveDistance),
                        nameElementHeaderOfPage);

        ILabel actualHeaderNameGotWithWebElement =
                elementFactory.getLabel(with(By.xpath(ChallengingDomForm.LOCATOR_CHALLENGING_DOM_FORM))
                                .near(challengingDomForm.getCellInRow1Column1().getElement(), positiveDistance),
                        nameElementHeaderOfPage);

        ILabel actualHeaderNameGotWithXpath =
                elementFactory.getLabel(with(By.xpath(ChallengingDomForm.LOCATOR_CHALLENGING_DOM_FORM))
                                .near(By.xpath(challengingDomForm.getLocatorCellRow1Column1()), positiveDistance),
                        nameElementHeaderOfPage);


        WebElement actualWebElementHeaderNameGotBySeleniumRelative =
                getBrowser().getDriver().findElement(RelativeLocator.with(By.xpath(ChallengingDomForm.LOCATOR_CHALLENGING_DOM_FORM))
                        .near(getBrowser().getDriver().findElement(By.xpath(challengingDomForm.getLocatorCellRow1Column1())),positiveDistance));

        checkDifferentTypesWithSoftAssert(
                actualHeaderNameGotWithAqualityElement.getText(),
                actualHeaderNameGotWithWebElement.getText(),
                actualHeaderNameGotWithXpath.getText(),
                actualWebElementHeaderNameGotBySeleniumRelative.getText(),
                challengingDomForm.getHeaderName().getText());
    }

    @Test
    public void testNearWithDistanceNegative() {
        ILabel actualHeaderNameGotWithAqualityElement =
                elementFactory.getLabel(with(By.xpath(ChallengingDomForm.LOCATOR_CHALLENGING_DOM_FORM))
                                .near(challengingDomForm.getCellInRow1Column1(), negativeDistance),
                        nameElementHeaderOfPage);

        ILabel actualHeaderNameGotWithWebElement =
                elementFactory.getLabel(with(By.xpath(ChallengingDomForm.LOCATOR_CHALLENGING_DOM_FORM))
                                .near(challengingDomForm.getCellInRow1Column1().getElement(), negativeDistance),
                        nameElementHeaderOfPage);

        ILabel actualHeaderNameGotWithXpath =
                elementFactory.getLabel(with(By.xpath(ChallengingDomForm.LOCATOR_CHALLENGING_DOM_FORM))
                                .near(By.xpath(challengingDomForm.getLocatorCellRow1Column1()), negativeDistance),
                        nameElementHeaderOfPage);


        List<WebElement> actualsWebElementsHeaderNameGotBySeleniumRelative =
                getBrowser().getDriver().findElements(RelativeLocator.with(By.xpath(ChallengingDomForm.LOCATOR_CHALLENGING_DOM_FORM))
                        .near(getBrowser().getDriver().findElement(By.xpath(challengingDomForm.getLocatorCellRow1Column1())), negativeDistance));

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertFalse(actualHeaderNameGotWithAqualityElement.state().isExist(), friendlyMessageElementFound);
        softAssert.assertFalse(actualHeaderNameGotWithWebElement.state().isExist(), friendlyMessageElementFound);
        softAssert.assertFalse(actualHeaderNameGotWithXpath.state().isExist(), friendlyMessageElementFound);
        softAssert.assertEquals(actualsWebElementsHeaderNameGotBySeleniumRelative.size(),0, "Elements with not reachable distance was found");
        softAssert.assertAll();
    }

    public void checkDifferentTypesWithSoftAssert(String textAquality, String textWebElement, String textByXpath, String textSelenium, String expectedText) {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(textAquality, expectedText, friendlyMessageEquallingText);
        softAssert.assertEquals(textByXpath, expectedText, friendlyMessageEquallingText);
        softAssert.assertEquals(textWebElement, expectedText, friendlyMessageEquallingText);
        softAssert.assertEquals(textSelenium, expectedText, friendlyMessageEquallingText);
        softAssert.assertAll();
    }
}
