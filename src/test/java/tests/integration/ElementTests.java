package tests.integration;

import aquality.selenium.browser.BrowserManager;
import aquality.selenium.elements.ElementState;
import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.ExpectedCount;
import aquality.selenium.elements.HighlightState;
import aquality.selenium.elements.interfaces.*;
import aquality.selenium.waitings.ConditionalWait;
import automationpractice.forms.DropDownForm;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.BaseTest;
import theinternet.TheInternetPage;
import theinternet.forms.DynamicControlsForm;
import theinternet.forms.LoginForm;

import java.util.List;

public class ElementTests extends BaseTest {

    @Test
    public void testComboBox() {
        navigate(TheInternetPage.DROPDOWN);
        IComboBox comboBox = new DropDownForm().getComboBox();
        List<String> values = comboBox.getValuesList();
        comboBox.selectByIndex(values.size() - 1);
        Assert.assertEquals(comboBox.getSelectedText(), values.get(values.size() - 1));

        comboBox.selectOptionThatContainsText("1");
        Assert.assertEquals(comboBox.getSelectedText(), values.get(1));

        String selectedText = comboBox.getSelectedText();
        comboBox.selectByText("Option 2");
        ConditionalWait.waitForTrue(y -> !selectedText.equals(comboBox.getSelectedText()));
        Assert.assertEquals(comboBox.getSelectedTextByJs(), values.get(2));
    }

    @Test
    public void testSelectOptionThatContainsValue() {
        navigate(TheInternetPage.DROPDOWN);
        IComboBox comboBox = elementFactory.getComboBox(By.id("dropdown"), "Dropdown");
        comboBox.waitForDisplayed();
        List<String> values = comboBox.getValuesList();

        comboBox.selectOptionThatContainsValue("2");
        Assert.assertEquals(comboBox.getSelectedText(), values.get(2));
    }

    @Test
    public void testComboBoxGetValuesJs() {
        navigate(TheInternetPage.DROPDOWN);
        IComboBox comboBox = new DropDownForm().getComboBox();
        List<String> valuesByJs = comboBox.getJsActions().getValuesList();
        List<String> valuesBySelenium = comboBox.getValuesList();
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
                ElementState.DISPLAYED, ExpectedCount.MORE_THEN_ZERO);
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
        ConditionalWait.waitForTrue(statusCodesExpectedCondition);
        Assert.assertEquals(BrowserManager.getBrowser().getCurrentUrl(), TheInternetPage.STATUS_CODES.getAddress());

        BrowserManager.getBrowser().goBack();
        link = elementFactory.getLink(By.id("redirect"), "Link", ElementState.DISPLAYED);
        link.click();
        ConditionalWait.waitForTrue(statusCodesExpectedCondition);
        Assert.assertEquals(BrowserManager.getBrowser().getCurrentUrl(), TheInternetPage.STATUS_CODES.getAddress());

        BrowserManager.getBrowser().getDriver().navigate().to(href);
        ConditionalWait.waitForTrue(statusCodesExpectedCondition);
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
        boolean result = ConditionalWait.waitForTrue(webDriver -> txbPass.getValue().equalsIgnoreCase(""));
        softAssert.assertTrue(result);
        softAssert.assertAll();
    }

    @Test
    public void testTextBoxNotEnabled() {
        navigate(TheInternetPage.DYNAMIC_CONTROLS);
        Assert.assertFalse(new DynamicControlsForm().getTxbInput().isEnabled(1L));
    }

    @Test
    public void testTextBoxEnabled() {
        navigate(TheInternetPage.DYNAMIC_CONTROLS);
        DynamicControlsForm controlsForm = new DynamicControlsForm();
        controlsForm.getBtnEnable().click();
        Assert.assertTrue(controlsForm.getTxbInput().isEnabled());
    }

    @Test
    public void testSetInnerHtml() {
        navigate(TheInternetPage.LOGIN);
        LoginForm loginForm = new LoginForm();
        ITextBox txbUsername = loginForm.getTxbUsername();
        Assert.assertTrue(txbUsername.waitForDisplayed());

        ILabel lblLogin = loginForm.getLblLogin();
        lblLogin.setInnerHtml("<p>123123</p>");

        Assert.assertTrue(txbUsername.waitForNotExist(loginForm.getTimeout()));

        Assert.assertTrue(elementFactory.getLabel(By.xpath(loginForm.getXPathFormLogin().concat("/p[.='123123']")), "login with innerHTML").waitForDisplayed());
    }

    @Test
    public void testRightClick() {
        BrowserManager.getBrowser().getDriver().navigate().to("https://swisnl.github.io/jQuery-contextMenu/demo.html");
        ILabel label = elementFactory.getLabel(By.xpath("//span[contains(@class, 'context')]"), "Right click");
        label.clickRight();
        boolean present = elementFactory.getLabel(By.xpath("//ul[contains(@class, 'context-menu-list')]"), "List", ElementState.DISPLAYED).waitForDisplayed();
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

    private void navigate(TheInternetPage page) {
        BrowserManager.getBrowser().navigate().to(page.getAddress());
    }
}