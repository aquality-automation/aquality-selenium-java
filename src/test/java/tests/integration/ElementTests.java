package tests.integration;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.elements.ElementState;
import aquality.selenium.core.elements.ElementsCount;
import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.HighlightState;
import aquality.selenium.elements.interfaces.*;
import aquality.selenium.waitings.ConditionalWait;
import automationpractice.forms.DropDownForm;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.BaseTest;
import theinternet.TheInternetPage;
import theinternet.forms.FormAuthenticationForm;

import java.util.List;

public class ElementTests extends BaseTest {

    @Test
    public void testComboBox() {
        navigate(TheInternetPage.DROPDOWN);
        IComboBox comboBox = new DropDownForm().getComboBox();
        List<String> texts = comboBox.getTexts();
        int lastOptionIndex = texts.size() - 1;
        comboBox.selectByIndex(lastOptionIndex);
        Assert.assertEquals(comboBox.getSelectedText(), texts.get(lastOptionIndex));

        comboBox.selectByContainingText("1");
        Assert.assertEquals(comboBox.getSelectedText(), texts.get(1));

        String selectedText = comboBox.getSelectedText();
        comboBox.selectByText("Option 2");
        ConditionalWait.waitFor(y -> !selectedText.equals(comboBox.getSelectedText()), "Combobox should not be equal to " + selectedText);
        Assert.assertEquals(comboBox.getJsActions().getSelectedText(), texts.get(2));
    }

    @Test
    public void testSelectOptionThatContainsValue() {
        navigate(TheInternetPage.DROPDOWN);
        IComboBox comboBox = elementFactory.getComboBox(By.id("dropdown"), "Dropdown");
        comboBox.state().waitForDisplayed();
        List<String> values = comboBox.getValues();

        comboBox.selectByContainingValue("2");
        Assert.assertEquals(comboBox.getSelectedValue(), values.get(2));
    }

    @Test
    public void testComboBoxGetValuesJs() {
        navigate(TheInternetPage.DROPDOWN);
        IComboBox comboBox = new DropDownForm().getComboBox();
        List<String> textsByJs = comboBox.getJsActions().getTexts();
        List<String> textsBySelenium = comboBox.getTexts();
        Assert.assertTrue(textsByJs.containsAll(textsBySelenium));
    }

    @Test
    public void testComboBoxGetSelectedTestJs() {
        navigate(TheInternetPage.DROPDOWN);
        IComboBox comboBox = new DropDownForm().getComboBox();
        String selectedText = comboBox.getSelectedText();
        String selectedTextByJs = comboBox.getJsActions().getSelectedText();
        Assert.assertEquals(selectedTextByJs, selectedText);
    }

    @Test
    public void testComboBoxJs() {
        navigate(TheInternetPage.DROPDOWN);
        IComboBox comboBox = new DropDownForm().getComboBox();
        String text = "Option 2";
        comboBox.getJsActions().selectValueByText(text);
        Assert.assertEquals(comboBox.getSelectedText(), text);
    }

    @Test
    public void testCheckBox() {
        navigate(TheInternetPage.CHECKBOXES);
        String checkboxLocator = "//input[@type='checkbox']";
        List<ICheckBox> checkBoxes = elementFactory.findElements(By.xpath(checkboxLocator), ICheckBox.class,
                ElementsCount.MORE_THEN_ZERO, ElementState.DISPLAYED);
        ICheckBox checkBox1 = checkBoxes.get(0);
        ICheckBox checkBox2 = checkBoxes.get(1);
        boolean stateFirst = checkBox1.isChecked();
        boolean stateSecond = checkBox2.isChecked();
        boolean state = checkBox1.isChecked();
        checkBox1.toggle();
        Assert.assertEquals(checkBox1.isChecked(), !state);

        checkBox2.uncheck();
        Assert.assertFalse(checkBox2.isChecked());

        AqualityServices.getBrowser().refresh();
        checkBoxes = elementFactory.findElements(By.xpath(checkboxLocator), ElementType.CHECKBOX);
        checkBox1 = checkBoxes.get(0);
        checkBox2 = checkBoxes.get(1);
        Assert.assertEquals(checkBox1.isChecked(), stateFirst, "");
        Assert.assertEquals(checkBox2.isChecked(), stateSecond, "");
    }

    @Test
    public void testLink() {
        navigate(TheInternetPage.REDIRECTOR);
        ILink link = elementFactory.getLink(By.id("redirect"), "Link");
        String href = link.getHref();
        link.click();
        String expectedUrl = TheInternetPage.STATUS_CODES.getAddress();
        ExpectedCondition<Boolean> statusCodesExpectedCondition = webDriver -> AqualityServices.getBrowser().getCurrentUrl().equalsIgnoreCase(expectedUrl);
        ConditionalWait.waitFor(statusCodesExpectedCondition, "Current url should be equal to " + expectedUrl);
        Assert.assertEquals(AqualityServices.getBrowser().getCurrentUrl(), TheInternetPage.STATUS_CODES.getAddress());

        AqualityServices.getBrowser().goBack();
        link = elementFactory.getLink(By.id("redirect"), "Link", ElementState.DISPLAYED);
        link.click();
        ConditionalWait.waitFor(statusCodesExpectedCondition, "Current url should be equal to " + expectedUrl);
        Assert.assertEquals(AqualityServices.getBrowser().getCurrentUrl(), TheInternetPage.STATUS_CODES.getAddress());

        AqualityServices.getBrowser().getDriver().navigate().to(href);
        ConditionalWait.waitFor(statusCodesExpectedCondition,"Current url should be equal to " + expectedUrl);
        Assert.assertEquals(AqualityServices.getBrowser().getCurrentUrl(), TheInternetPage.STATUS_CODES.getAddress());
    }

    @Test
    public void testTextBox() {
        navigate(TheInternetPage.LOGIN);
        FormAuthenticationForm authenticationForm = new FormAuthenticationForm();
        SoftAssert softAssert = new SoftAssert();

        ITextBox txbUsername = authenticationForm.getTxbUsername();
        txbUsername.focus();
        txbUsername.type("wrong");
        softAssert.assertEquals(txbUsername.getValue(), "wrong");

        ITextBox txbPass = authenticationForm.getTxbPassword();
        txbPass.sendKeys(Keys.NUMPAD0);
        softAssert.assertEquals(txbPass.getValue(), "0");

        txbPass.submit();
        String expectedValue = "";
        boolean result = ConditionalWait.waitFor(() -> txbPass.getValue().equalsIgnoreCase(expectedValue), "Value of textbox should be equal " + expectedValue);
        softAssert.assertTrue(result);
        softAssert.assertAll();
    }

    @Test
    public void testSetInnerHtml() {
        navigate(TheInternetPage.LOGIN);
        FormAuthenticationForm authenticationForm = new FormAuthenticationForm();
        ITextBox txbUsername = authenticationForm.getTxbUsername();
        Assert.assertTrue(txbUsername.state().waitForDisplayed());

        ILabel lblLogin = authenticationForm.getLblLogin();
        lblLogin.setInnerHtml("<p>123123</p>");

        Assert.assertTrue(txbUsername.state().waitForNotExist(authenticationForm.getTimeout()));

        Assert.assertTrue(elementFactory.getLabel(By.xpath(authenticationForm.getXPathFormLogin().concat("/p[.='123123']")), "login with innerHTML").state().waitForDisplayed());
    }

    @Test
    public void testRightClick() {
        AqualityServices.getBrowser().getDriver().navigate().to("https://swisnl.github.io/jQuery-contextMenu/demo.html");
        ILabel label = elementFactory.getLabel(By.xpath("//span[contains(@class, 'context')]"), "Right click");
        label.getMouseActions().rightClick();
        boolean present = elementFactory.getLabel(By.xpath("//ul[contains(@class, 'context-menu-list')]"), "List", ElementState.DISPLAYED).state().waitForDisplayed();
        Assert.assertTrue(present, "");
    }

    @Test
    public void testShouldBePossibleToGetCssValue(){
        navigate(TheInternetPage.LOGIN);
        ITextBox txbUsername = elementFactory.getTextBox(By.id("username"), "username");

        String propertyName = "font-family";
        String expectedCssValue = "Helvetica";

        Assert.assertTrue(txbUsername.getCssValue(propertyName).contains(expectedCssValue));
        Assert.assertTrue(txbUsername.getCssValue(propertyName, HighlightState.HIGHLIGHT).contains(expectedCssValue));
    }

    @Test(expectedExceptions = NoSuchElementException.class)
    public void testNoSuchShouldBeThrownIfNoELementsExistForSendKeys(){
        ITextBox textBox = elementFactory.getTextBox(By.xpath("//div[@class='not exist element']"), "not exist element");
        textBox.sendKeys(Keys.BACK_SPACE);
    }
}