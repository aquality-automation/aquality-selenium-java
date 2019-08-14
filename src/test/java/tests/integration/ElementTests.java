package tests.integration;

import aquality.selenium.browser.BrowserManager;
import aquality.selenium.elements.ElementState;
import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.ElementsCount;
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
import theinternet.forms.LoginForm;

import java.util.List;

public class ElementTests extends BaseTest {

    @Test
    public void testComboBox() {
        navigate(TheInternetPage.DROPDOWN);
        IComboBox comboBox = new DropDownForm().getComboBox();
        List<String> values = comboBox.getValues();
        comboBox.selectByIndex(values.size() - 1);
        Assert.assertEquals(comboBox.getSelectedText(), values.get(values.size() - 1));

        comboBox.selectByContainingText("1");
        Assert.assertEquals(comboBox.getSelectedText(), values.get(1));

        String selectedText = comboBox.getSelectedText();
        comboBox.selectByText("Option 2");
        ConditionalWait.waitFor(y -> !selectedText.equals(comboBox.getSelectedText()));
        Assert.assertEquals(comboBox.getSelectedTextByJs(), values.get(2));
    }

    @Test
    public void testSelectOptionThatContainsValue() {
        navigate(TheInternetPage.DROPDOWN);
        IComboBox comboBox = elementFactory.getComboBox(By.id("dropdown"), "Dropdown");
        comboBox.state().waitForDisplayed();
        List<String> values = comboBox.getValues();

        comboBox.selectByContainingValue("2");
        Assert.assertEquals(comboBox.getSelectedText(), values.get(2));
    }

    @Test
    public void testComboBoxGetValuesJs() {
        navigate(TheInternetPage.DROPDOWN);
        IComboBox comboBox = new DropDownForm().getComboBox();
        List<String> valuesByJs = comboBox.getJsActions().getValues();
        List<String> valuesBySelenium = comboBox.getValues();
        Assert.assertTrue(valuesByJs.containsAll(valuesBySelenium));
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
                ElementState.DISPLAYED, ElementsCount.MORE_THEN_ZERO);
        ICheckBox checkBox1 = checkBoxes.get(0);
        ICheckBox checkBox2 = checkBoxes.get(1);
        boolean stateFirst = checkBox1.isChecked();
        boolean stateSecond = checkBox2.isChecked();
        boolean state = checkBox1.isChecked();
        checkBox1.toggle();
        Assert.assertEquals(checkBox1.isChecked(), !state);

        checkBox2.uncheck();
        Assert.assertFalse(checkBox2.isChecked());

        BrowserManager.getBrowser().refresh();
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
        ExpectedCondition<Boolean> statusCodesExpectedCondition = webDriver -> BrowserManager.getBrowser().getCurrentUrl().equalsIgnoreCase(TheInternetPage.STATUS_CODES.getAddress());
        ConditionalWait.waitFor(statusCodesExpectedCondition);
        Assert.assertEquals(BrowserManager.getBrowser().getCurrentUrl(), TheInternetPage.STATUS_CODES.getAddress());

        BrowserManager.getBrowser().goBack();
        link = elementFactory.getLink(By.id("redirect"), "Link", ElementState.DISPLAYED);
        link.click();
        ConditionalWait.waitFor(statusCodesExpectedCondition);
        Assert.assertEquals(BrowserManager.getBrowser().getCurrentUrl(), TheInternetPage.STATUS_CODES.getAddress());

        BrowserManager.getBrowser().getDriver().navigate().to(href);
        ConditionalWait.waitFor(statusCodesExpectedCondition);
        Assert.assertEquals(BrowserManager.getBrowser().getCurrentUrl(), TheInternetPage.STATUS_CODES.getAddress());
    }

    @Test
    public void testTextBox() {
        navigate(TheInternetPage.LOGIN);
        LoginForm loginForm = new LoginForm();
        SoftAssert softAssert = new SoftAssert();

        ITextBox txbUsername = loginForm.getTxbUsername();
        txbUsername.focus();
        txbUsername.type("wrong");
        softAssert.assertEquals(txbUsername.getValue(), "wrong");

        ITextBox txbPass = loginForm.getTxbPassword();
        txbPass.sendKeys(Keys.NUMPAD0);
        softAssert.assertEquals(txbPass.getValue(), "0");

        txbPass.submit();
        boolean result = ConditionalWait.waitFor(webDriver -> txbPass.getValue().equalsIgnoreCase(""));
        softAssert.assertTrue(result);
        softAssert.assertAll();
    }

    @Test
    public void testSetInnerHtml() {
        navigate(TheInternetPage.LOGIN);
        LoginForm loginForm = new LoginForm();
        ITextBox txbUsername = loginForm.getTxbUsername();
        Assert.assertTrue(txbUsername.state().waitForDisplayed());

        ILabel lblLogin = loginForm.getLblLogin();
        lblLogin.setInnerHtml("<p>123123</p>");

        Assert.assertTrue(txbUsername.state().waitForNotExist(loginForm.getTimeout()));

        Assert.assertTrue(elementFactory.getLabel(By.xpath(loginForm.getXPathFormLogin().concat("/p[.='123123']")), "login with innerHTML").state().waitForDisplayed());
    }

    @Test
    public void testRightClick() {
        BrowserManager.getBrowser().getDriver().navigate().to("https://swisnl.github.io/jQuery-contextMenu/demo.html");
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
        String expectedCssValue = "\"Helvetica Neue\", Helvetica, Helvetica, Arial, sans-serif";

        Assert.assertEquals(txbUsername.getCssValue(propertyName), expectedCssValue);

        Assert.assertEquals(txbUsername.getCssValue(propertyName, HighlightState.HIGHLIGHT), expectedCssValue);
    }

    @Test(expectedExceptions = NoSuchElementException.class)
    public void testNoSuchShouldBeThrownIfNoELementsExistForSendKeys(){
        ITextBox textBox = elementFactory.getTextBox(By.xpath("//div[@class='not exist element']"), "not exist element");
        textBox.sendKeys(Keys.BACK_SPACE);
    }
}