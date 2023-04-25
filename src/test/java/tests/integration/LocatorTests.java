package tests.integration;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.ByImage;
import aquality.selenium.elements.interfaces.ILabel;
import automationpractice.forms.ChallengingDomForm;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.BaseTest;
import theinternet.TheInternetPage;
import theinternet.forms.BrokenImagesForm;

import java.util.List;

import static aquality.selenium.locators.RelativeBySupplier.with;

public class LocatorTests extends BaseTest {
    private final ChallengingDomForm challengingDomForm = new ChallengingDomForm();
    private final String labelLocatorCell = "//td";
    private final String friendlyMessageEquallingText = "Actual cell text is not equal expected";
    private final String friendlyMessageElementFound = "Element with not reachable distance is exist";
    private final int distanceToFindElementWithPositiveResult = 300;
    private final int distanceToFindElementWithNegativeResult = 100;

    @BeforeMethod
    public void beforeMethod() {
        navigate(TheInternetPage.CHALLENGING_DOM);
    }

    @Test
    public void testByImageLocator() {
        BrokenImagesForm form = new BrokenImagesForm();
        Assert.assertFalse(form.getLabelByImage().state().isDisplayed(), "Should be impossible to find element on page by image when it is absent");
        getBrowser().goTo(form.getUrl());
        Assert.assertTrue(form.getLabelByImage().state().isDisplayed(), "Should be possible to find element on page by image");

        List<ILabel> childLabels = form.getChildLabelsByImage();
        List<ILabel> docLabels = form.getLabelsByImage();
        Assert.assertTrue(docLabels.size() > 1, "List of elements should be possible to find by image");
        Assert.assertEquals(docLabels.size(), childLabels.size(), "Should be possible to find child elements by image with the same count");

        ILabel screen = AqualityServices.getElementFactory().getLabel(new ByImage(AqualityServices.getBrowser().getScreenshot()), "full screen");
        Assert.assertTrue(screen.state().waitForDisplayed(), "Should be possible to find element by full page screenshot");
    }

    @Test
    public void testAboveLocatorWithDifferentAboveParametersType() {
        ILabel cellInRow5Column5 = challengingDomForm.getCellInRow5Column5();

        ILabel actualCellRaw3Column5GotWithByXpath =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell)).above(By.xpath(challengingDomForm.getLocatorCellRow5Column5())),
                        ChallengingDomForm.ELEMENT_NAME_ROW3_COLUMN5);

        ILabel actualCellRaw3Column5GotWithWebElement =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell)).above(cellInRow5Column5.getElement()),
                        ChallengingDomForm.ELEMENT_NAME_ROW3_COLUMN5);

        ILabel actualCellRaw3Column5GotWithAqualityElement =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell)).above(cellInRow5Column5),
                        ChallengingDomForm.ELEMENT_NAME_ROW3_COLUMN5);

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
                        ChallengingDomForm.ELEMENT_NAME_ROW7_COLUMN5);

        ILabel actualCellRaw7Column5GotWithWebElement =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell)).below(cellInRow5Column5.getElement()),
                        ChallengingDomForm.ELEMENT_NAME_ROW7_COLUMN5);

        ILabel actualCellRaw7Column5GotWithAqualityElement =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell)).below(cellInRow5Column5),
                        ChallengingDomForm.ELEMENT_NAME_ROW7_COLUMN5);

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
                        ChallengingDomForm.ELEMENT_NAME_ROW5_COLUMN3);

        ILabel actualCellRaw5Column3GotWithWebElement =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell)).toLeftOf(cellInRow5Column5.getElement()),
                        ChallengingDomForm.ELEMENT_NAME_ROW5_COLUMN3);

        ILabel actualCellRaw5Column3GotWithAqualityElement =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell)).toLeftOf(cellInRow5Column5),
                        ChallengingDomForm.ELEMENT_NAME_ROW5_COLUMN3);

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
                        ChallengingDomForm.ELEMENT_NAME_ROW5_COLUMN7);

        ILabel actualCellRaw5Column7GotWithWebElement =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell)).toRightOf(cellInRow5Column5.getElement()),
                        ChallengingDomForm.ELEMENT_NAME_ROW5_COLUMN7);

        ILabel actualCellRaw5Column7GotWithAqualityElement =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell)).toRightOf(cellInRow5Column5),
                        ChallengingDomForm.ELEMENT_NAME_ROW5_COLUMN7);

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
    public void testAboveBelowLeftRightWithDifferentParametersType() {
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
                        , ChallengingDomForm.ELEMENT_NAME_ROW5_COLUMN5);

        ILabel actualCellRaw5Column5GotWithWebElement =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell))
                                .above(cellInRow7Column5.getElement())
                                .below(cellInRow3Column5.getElement())
                                .toRightOf(cellInRow5Column3.getElement())
                                .toLeftOf(cellInRow5Column7.getElement())
                                .above(cellInRow7Column5.getElement())
                        , ChallengingDomForm.ELEMENT_NAME_ROW5_COLUMN5);

        ILabel actualCellRaw5Column5GotWithXpath =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell))
                                .above(By.xpath(challengingDomForm.getLocatorCellRow7Column5()))
                                .below(By.xpath(challengingDomForm.getLocatorCellRow3Column5()))
                                .toRightOf(By.xpath(challengingDomForm.getLocatorCellRow5Column3()))
                                .toLeftOf(By.xpath(challengingDomForm.getLocatorCellRow5Column7()))
                                .above(By.xpath(challengingDomForm.getLocatorCellRow7Column5()))
                        , ChallengingDomForm.ELEMENT_NAME_ROW5_COLUMN5);

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
    public void testNearWithDifferentNearParameterType() {
        ILabel actualCellRaw2Column1GotWithAqualityElement =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell)).near(challengingDomForm.getCellInRow1Column1()),
                        ChallengingDomForm.ELEMENT_NAME_ROW2_COLUMN1);

        ILabel actualCellRaw2Column1GotWithWebElement =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell)).near(challengingDomForm.getCellInRow1Column1().getElement()),
                        ChallengingDomForm.ELEMENT_NAME_ROW2_COLUMN1);

        ILabel actualCellRaw2Column1GotWithXpath =
                elementFactory.getLabel(with(By.xpath(labelLocatorCell)).near(By.xpath(challengingDomForm.getLocatorCellRow1Column1())),
                        ChallengingDomForm.ELEMENT_NAME_ROW2_COLUMN1);


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
    public void testNearWithDistanceWithDifferentParametersType() {
        ILabel actualHeaderNameGotWithAqualityElement =
                elementFactory.getLabel(with(By.xpath(ChallengingDomForm.LOCATOR_CHALLENGING_DOM_FORM))
                                .near(challengingDomForm.getCellInRow1Column1(), distanceToFindElementWithPositiveResult),
                        ChallengingDomForm.ELEMENT_NAME_HEADER_CHALLENGING_DOM);

        ILabel actualHeaderNameGotWithWebElement =
                elementFactory.getLabel(with(By.xpath(ChallengingDomForm.LOCATOR_CHALLENGING_DOM_FORM))
                                .near(challengingDomForm.getCellInRow1Column1().getElement(), distanceToFindElementWithPositiveResult),
                        ChallengingDomForm.ELEMENT_NAME_HEADER_CHALLENGING_DOM);

        ILabel actualHeaderNameGotWithXpath =
                elementFactory.getLabel(with(By.xpath(ChallengingDomForm.LOCATOR_CHALLENGING_DOM_FORM))
                                .near(By.xpath(challengingDomForm.getLocatorCellRow1Column1()), distanceToFindElementWithPositiveResult),
                        ChallengingDomForm.ELEMENT_NAME_HEADER_CHALLENGING_DOM);


        WebElement actualWebElementHeaderNameGotBySeleniumRelative =
                getBrowser().getDriver().findElement(RelativeLocator.with(By.xpath(ChallengingDomForm.LOCATOR_CHALLENGING_DOM_FORM))
                        .near(getBrowser().getDriver().findElement(By.xpath(challengingDomForm.getLocatorCellRow1Column1())), distanceToFindElementWithPositiveResult));

        checkDifferentTypesWithSoftAssert(
                actualHeaderNameGotWithAqualityElement.getText(),
                actualHeaderNameGotWithWebElement.getText(),
                actualHeaderNameGotWithXpath.getText(),
                actualWebElementHeaderNameGotBySeleniumRelative.getText(),
                challengingDomForm.getHeaderName().getText());
    }

    @Test
    public void testNearWithDistanceNegativeWithDifferentParametersType() {
        ILabel actualHeaderNameGotWithAqualityElement =
                elementFactory.getLabel(with(By.xpath(ChallengingDomForm.LOCATOR_CHALLENGING_DOM_FORM))
                                .near(challengingDomForm.getCellInRow1Column1(), distanceToFindElementWithNegativeResult),
                        ChallengingDomForm.ELEMENT_NAME_HEADER_CHALLENGING_DOM);

        ILabel actualHeaderNameGotWithWebElement =
                elementFactory.getLabel(with(By.xpath(ChallengingDomForm.LOCATOR_CHALLENGING_DOM_FORM))
                                .near(challengingDomForm.getCellInRow1Column1().getElement(), distanceToFindElementWithNegativeResult),
                        ChallengingDomForm.ELEMENT_NAME_HEADER_CHALLENGING_DOM);

        ILabel actualHeaderNameGotWithXpath =
                elementFactory.getLabel(with(By.xpath(ChallengingDomForm.LOCATOR_CHALLENGING_DOM_FORM))
                                .near(By.xpath(challengingDomForm.getLocatorCellRow1Column1()), distanceToFindElementWithNegativeResult),
                        ChallengingDomForm.ELEMENT_NAME_HEADER_CHALLENGING_DOM);


        List<WebElement> actualWebElementsHeaderNameGotBySeleniumRelative =
                getBrowser().getDriver().findElements(RelativeLocator.with(By.xpath(ChallengingDomForm.LOCATOR_CHALLENGING_DOM_FORM))
                        .near(getBrowser().getDriver().findElement(By.xpath(challengingDomForm.getLocatorCellRow1Column1())), distanceToFindElementWithNegativeResult));

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertFalse(actualHeaderNameGotWithAqualityElement.state().isExist(), friendlyMessageElementFound);
        softAssert.assertFalse(actualHeaderNameGotWithWebElement.state().isExist(), friendlyMessageElementFound);
        softAssert.assertFalse(actualHeaderNameGotWithXpath.state().isExist(), friendlyMessageElementFound);
        softAssert.assertEquals(actualWebElementsHeaderNameGotBySeleniumRelative.size(), 0, friendlyMessageElementFound);
        softAssert.assertAll();
    }

    private void checkDifferentTypesWithSoftAssert(String textAquality, String textWebElement, String textByXpath, String textSelenium, String expectedText) {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(textAquality, expectedText, friendlyMessageEquallingText);
        softAssert.assertEquals(textByXpath, expectedText, friendlyMessageEquallingText);
        softAssert.assertEquals(textWebElement, expectedText, friendlyMessageEquallingText);
        softAssert.assertEquals(textSelenium, expectedText, friendlyMessageEquallingText);
        softAssert.assertAll();
    }
}
