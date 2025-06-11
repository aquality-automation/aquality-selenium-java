package tests.integration;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.ByImage;
import aquality.selenium.elements.interfaces.ILabel;
import automationpractice.forms.ChallengingDomForm;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
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
import java.util.concurrent.atomic.AtomicReference;

import static aquality.selenium.locators.RelativeBySupplier.with;

public class LocatorTests extends BaseTest {
    private final ChallengingDomForm challengingDomForm = new ChallengingDomForm();
    private final By labelLocatorCell = By.xpath("//td");

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
        Assert.assertEquals(form.getLabelByImage().getElement().getTagName(), "img", "Correct element must be found");

        List<ILabel> childLabels = form.getChildLabelsByImage();
        List<ILabel> docLabels = form.getLabelsByImage();
        Assert.assertTrue(docLabels.size() > 1, "List of elements should be possible to find by image");
        Assert.assertEquals(docLabels.size(), childLabels.size(), "Should be possible to find child elements by image with the same count");

        ILabel imgByTag = AqualityServices.getElementFactory().getLabel(By.tagName("img"), "img by tag");
        float fullThreshold = 1;
        AtomicReference<ILabel> documentByImageContainer = new AtomicReference<>();
        boolean isFound = AqualityServices.getConditionalWait().waitFor(browser -> {
            ILabel image = AqualityServices.getElementFactory().getLabel(new ByImage(imgByTag.getElement().getScreenshotAs(OutputType.BYTES)).setThreshold(fullThreshold),
                    "img screen");
            documentByImageContainer.set(image);
            boolean isDisplayed = image.state().isDisplayed();
            if (!isDisplayed) {
                browser.navigate().refresh();
            }
            return isDisplayed;
        });
        ILabel elementByImage = documentByImageContainer.get();

        Assert.assertTrue(isFound, "Should be possible to find element by element screenshot");
        Assert.assertEquals(((ByImage) elementByImage.getLocator()).getThreshold(), fullThreshold, "Should be possible to get ByImage threshold");
        Assert.assertEquals(elementByImage.getElement().getTagName(), "img", "Correct element must be found");
    }

    @Test
    public void testAboveLocatorWithDifferentAboveParametersType() {
        ILabel cellInRow5Column5 = challengingDomForm.getCell(5, 5);
        ILabel cellInRow4Column5 = challengingDomForm.getCell(4, 5);

        ILabel actualCellRaw3Column5GotWithByXpath =
                elementFactory.getLabel(with(labelLocatorCell).above(cellInRow5Column5.getLocator()),
                        cellInRow5Column5.getName());

        ILabel actualCellRaw3Column5GotWithWebElement =
                elementFactory.getLabel(with(labelLocatorCell).above(cellInRow5Column5.getElement()),
                        cellInRow4Column5.getName());

        ILabel actualCellRaw3Column5GotWithAqualityElement =
                elementFactory.getLabel(with(labelLocatorCell).above(cellInRow5Column5),
                        cellInRow4Column5.getName());

        WebElement actualWebElementCellRaw3Column5GotBySeleniumRelative =
                getBrowser().getDriver().findElement(RelativeLocator
                        .with(labelLocatorCell)
                        .above(cellInRow5Column5.getLocator()));

        checkDifferentTypesWithSoftAssert(
                actualCellRaw3Column5GotWithAqualityElement.getText(),
                actualCellRaw3Column5GotWithWebElement.getText(),
                actualCellRaw3Column5GotWithByXpath.getText(),
                actualWebElementCellRaw3Column5GotBySeleniumRelative.getText(),
                cellInRow4Column5.getText());
    }

    @Test
    public void testBelowLocatorWithDifferentBelowParametersType() {
        ILabel cellInRow5Column5 = challengingDomForm.getCell(5, 5);
        ILabel cellInRow6Column5 = challengingDomForm.getCell(6, 5);

        ILabel actualCellRaw7Column5GotWithByXpath =
                elementFactory.getLabel(with(labelLocatorCell).below(cellInRow5Column5.getLocator()),
                        cellInRow6Column5.getName());

        ILabel actualCellRaw7Column5GotWithWebElement =
                elementFactory.getLabel(with(labelLocatorCell).below(cellInRow5Column5.getElement()),
                        cellInRow6Column5.getName());

        ILabel actualCellRaw7Column5GotWithAqualityElement =
                elementFactory.getLabel(with(labelLocatorCell).below(cellInRow5Column5),
                        cellInRow6Column5.getName());

        WebElement actualWebElementCellRaw7Column5GotBySeleniumRelative =
                getBrowser().getDriver().findElement(RelativeLocator
                        .with(labelLocatorCell)
                        .below(cellInRow5Column5.getLocator()));

        checkDifferentTypesWithSoftAssert(
                actualCellRaw7Column5GotWithAqualityElement.getText(),
                actualCellRaw7Column5GotWithWebElement.getText(),
                actualCellRaw7Column5GotWithByXpath.getText(),
                actualWebElementCellRaw7Column5GotBySeleniumRelative.getText(),
                cellInRow6Column5.getText());
    }

    @Test
    public void testToLeftOfLocatorWithDifferentToLeftOfParametersType() {
        ILabel cellInRow5Column5 = challengingDomForm.getCell(5, 5);
        ILabel cellInRow5Column4 = challengingDomForm.getCell(5, 4);

        ILabel actualCellRaw5Column3GotWithByXpath =
                elementFactory.getLabel(with(labelLocatorCell).toLeftOf(cellInRow5Column5.getLocator()),
                        cellInRow5Column4.getName());

        ILabel actualCellRaw5Column3GotWithWebElement =
                elementFactory.getLabel(with(labelLocatorCell).toLeftOf(cellInRow5Column5.getElement()),
                        cellInRow5Column4.getName());

        ILabel actualCellRaw5Column3GotWithAqualityElement =
                elementFactory.getLabel(with(labelLocatorCell).toLeftOf(cellInRow5Column5),
                        cellInRow5Column4.getName());

        WebElement actualWebElementCellRaw5Column3GotBySeleniumRelative =
                getBrowser().getDriver().findElement(RelativeLocator
                        .with(labelLocatorCell)
                        .toLeftOf(cellInRow5Column5.getLocator()));

        checkDifferentTypesWithSoftAssert(
                actualCellRaw5Column3GotWithAqualityElement.getText(),
                actualCellRaw5Column3GotWithWebElement.getText(),
                actualCellRaw5Column3GotWithByXpath.getText(),
                actualWebElementCellRaw5Column3GotBySeleniumRelative.getText(),
                cellInRow5Column4.getText());
    }

    @Test
    public void testToRightOfLocatorWithDifferentToRightOfParametersType() {
        ILabel cellInRow5Column5 = challengingDomForm.getCell(5, 5);
        ILabel cellInRow5Column6 = challengingDomForm.getCell(5, 6);

        ILabel actualCellRaw5Column7GotWithByXpath =
                elementFactory.getLabel(with(labelLocatorCell).toRightOf(cellInRow5Column5.getLocator()),
                        cellInRow5Column6.getName());

        ILabel actualCellRaw5Column7GotWithWebElement =
                elementFactory.getLabel(with(labelLocatorCell).toRightOf(cellInRow5Column5.getElement()),
                        cellInRow5Column6.getName());

        ILabel actualCellRaw5Column7GotWithAqualityElement =
                elementFactory.getLabel(with(labelLocatorCell).toRightOf(cellInRow5Column5),
                        cellInRow5Column6.getName());

        WebElement actualWebElementCellRaw5Column7GotBySeleniumRelative =
                getBrowser().getDriver().findElement(RelativeLocator
                        .with(labelLocatorCell)
                        .toRightOf(cellInRow5Column5.getLocator()));

        checkDifferentTypesWithSoftAssert(
                actualCellRaw5Column7GotWithAqualityElement.getText(),
                actualCellRaw5Column7GotWithWebElement.getText(),
                actualCellRaw5Column7GotWithByXpath.getText(),
                actualWebElementCellRaw5Column7GotBySeleniumRelative.getText(),
                cellInRow5Column6.getText());
    }

    @Test
    public void testAboveBelowLeftRightWithDifferentParametersType() {
        ILabel cellInRow3Column5 = challengingDomForm.getCell(3, 5);
        ILabel cellInRow5Column7 = challengingDomForm.getCell(5, 7);
        ILabel cellInRow5Column5 = challengingDomForm.getCell(5, 5);
        ILabel cellInRow5Column3 = challengingDomForm.getCell(5, 3);
        ILabel cellInRow6Column5 = challengingDomForm.getCell(6, 5);

        ILabel actualCellRaw5Column5GotWithAqualityElement =
                elementFactory.getLabel(with(labelLocatorCell)
                                .above(cellInRow6Column5)
                                .below(cellInRow3Column5)
                                .toRightOf(cellInRow5Column3)
                                .toLeftOf(cellInRow5Column7)
                                .above(cellInRow6Column5)
                        , cellInRow5Column5.getName());

        ILabel actualCellRaw5Column5GotWithWebElement =
                elementFactory.getLabel(with(labelLocatorCell)
                                .above(cellInRow6Column5.getElement())
                                .below(cellInRow3Column5.getElement())
                                .toRightOf(cellInRow5Column3.getElement())
                                .toLeftOf(cellInRow5Column7.getElement())
                                .above(cellInRow6Column5.getElement())
                        , cellInRow5Column5.getName());

        ILabel actualCellRaw5Column5GotWithXpath =
                elementFactory.getLabel(with(labelLocatorCell)
                                .above(cellInRow6Column5.getLocator())
                                .below(cellInRow3Column5.getLocator())
                                .toRightOf(cellInRow5Column3.getLocator())
                                .toLeftOf(cellInRow5Column7.getLocator())
                                .above(cellInRow6Column5.getLocator())
                        , cellInRow5Column5.getName());

        WebElement actualWebElementCellRaw5Column5GotBySeleniumRelative =
                getBrowser().getDriver().findElement(RelativeLocator.with(labelLocatorCell)
                        .above(cellInRow6Column5.getLocator())
                        .below(cellInRow3Column5.getLocator())
                        .toRightOf(cellInRow5Column3.getLocator())
                        .toLeftOf(cellInRow5Column7.getLocator())
                        .above(cellInRow6Column5.getLocator()));

        checkDifferentTypesWithSoftAssert(
                actualCellRaw5Column5GotWithAqualityElement.getText(),
                actualCellRaw5Column5GotWithWebElement.getText(),
                actualCellRaw5Column5GotWithXpath.getText(),
                actualWebElementCellRaw5Column5GotBySeleniumRelative.getText(),
                cellInRow5Column5.getText());
    }

    @Test
    public void testNearWithDifferentNearParameterType() {
        ILabel cellInRow1Column1 = challengingDomForm.getCell(1, 1);
        ILabel cellInRow2Column1 = challengingDomForm.getCell(2, 1);
        ILabel actualCellRaw2Column1GotWithAqualityElement =
                elementFactory.getLabel(with(labelLocatorCell).near(cellInRow1Column1),
                        cellInRow2Column1.getName());

        ILabel actualCellRaw2Column1GotWithWebElement =
                elementFactory.getLabel(with(labelLocatorCell).near(cellInRow1Column1.getElement()),
                        cellInRow2Column1.getName());

        ILabel actualCellRaw2Column1GotWithXpath =
                elementFactory.getLabel(with(labelLocatorCell).near(cellInRow1Column1.getLocator()),
                        cellInRow2Column1.getName());


        WebElement actualWebElementCellRaw2Column1GotBySeleniumRelative =
                getBrowser().getDriver().findElement(RelativeLocator.with(labelLocatorCell)
                        .near(cellInRow1Column1.getLocator()));

        checkDifferentTypesWithSoftAssert(
                actualCellRaw2Column1GotWithAqualityElement.getText(),
                actualCellRaw2Column1GotWithWebElement.getText(),
                actualCellRaw2Column1GotWithXpath.getText(),
                actualWebElementCellRaw2Column1GotBySeleniumRelative.getText(),
                cellInRow2Column1.getText());
    }

    @Test
    public void testNearWithDistanceWithDifferentParametersType() {
        ILabel cellInRow1Column1 = challengingDomForm.getCell(1, 1);
        int distanceToFindElementWithPositiveResult = 300;
        ILabel actualHeaderNameGotWithAqualityElement =
                elementFactory.getLabel(with(challengingDomForm.getLocator())
                                .near(cellInRow1Column1, distanceToFindElementWithPositiveResult),
                        challengingDomForm.getName());

        ILabel actualHeaderNameGotWithWebElement =
                elementFactory.getLabel(with(challengingDomForm.getLocator())
                                .near(cellInRow1Column1.getElement(), distanceToFindElementWithPositiveResult),
                        challengingDomForm.getName());

        ILabel actualHeaderNameGotWithXpath =
                elementFactory.getLabel(with(challengingDomForm.getLocator())
                                .near(cellInRow1Column1.getLocator(), distanceToFindElementWithPositiveResult),
                        challengingDomForm.getName());


        WebElement actualWebElementHeaderNameGotBySeleniumRelative =
                getBrowser().getDriver().findElement(RelativeLocator.with(challengingDomForm.getLocator())
                        .near(getBrowser().getDriver().findElement(cellInRow1Column1.getLocator()), distanceToFindElementWithPositiveResult));

        checkDifferentTypesWithSoftAssert(
                actualHeaderNameGotWithAqualityElement.getText(),
                actualHeaderNameGotWithWebElement.getText(),
                actualHeaderNameGotWithXpath.getText(),
                actualWebElementHeaderNameGotBySeleniumRelative.getText(),
                challengingDomForm.getHeaderText());
    }

    @Test
    public void testNearWithDistanceNegativeWithDifferentParametersType() {
        ILabel cellInRow1Column1 = challengingDomForm.getCell(1, 1);
        int distanceToFindElementWithNegativeResult = 100;
        ILabel actualHeaderNameGotWithAqualityElement =
                elementFactory.getLabel(with(challengingDomForm.getLocator())
                                .near(cellInRow1Column1, distanceToFindElementWithNegativeResult),
                        challengingDomForm.getName());

        ILabel actualHeaderNameGotWithWebElement =
                elementFactory.getLabel(with(challengingDomForm.getLocator())
                                .near(cellInRow1Column1.getElement(), distanceToFindElementWithNegativeResult),
                        challengingDomForm.getName());

        ILabel actualHeaderNameGotWithXpath =
                elementFactory.getLabel(with(challengingDomForm.getLocator())
                                .near(cellInRow1Column1.getLocator(), distanceToFindElementWithNegativeResult),
                        challengingDomForm.getName());


        List<WebElement> actualWebElementsHeaderNameGotBySeleniumRelative =
                getBrowser().getDriver().findElements(RelativeLocator.with(challengingDomForm.getLocator())
                        .near(getBrowser().getDriver().findElement(cellInRow1Column1.getLocator()), distanceToFindElementWithNegativeResult));

        SoftAssert softAssert = new SoftAssert();
        String friendlyMessageElementFound = "Element with not reachable distance is exist";
        softAssert.assertFalse(actualHeaderNameGotWithAqualityElement.state().isExist(), friendlyMessageElementFound);
        softAssert.assertFalse(actualHeaderNameGotWithWebElement.state().isExist(), friendlyMessageElementFound);
        softAssert.assertFalse(actualHeaderNameGotWithXpath.state().isExist(), friendlyMessageElementFound);
        softAssert.assertEquals(actualWebElementsHeaderNameGotBySeleniumRelative.size(), 0, friendlyMessageElementFound);
        softAssert.assertAll();
    }

    private void checkDifferentTypesWithSoftAssert(String textAquality, String textWebElement, String textByXpath, String textSelenium, String expectedText) {
        SoftAssert softAssert = new SoftAssert();
        String friendlyMessageEquallingText = "Actual cell text is not equal expected";
        softAssert.assertEquals(textAquality, expectedText, friendlyMessageEquallingText);
        softAssert.assertEquals(textByXpath, expectedText, friendlyMessageEquallingText);
        softAssert.assertEquals(textWebElement, expectedText, friendlyMessageEquallingText);
        softAssert.assertEquals(textSelenium, expectedText, friendlyMessageEquallingText);
        softAssert.assertAll();
    }
}
