package tests.integration.elements;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.BaseTest;
import w3schools.W3SchoolsPage;
import w3schools.forms.SelectMultipleForm;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MultiChoiceComboBoxTests extends BaseTest {

    private final SelectMultipleForm selectMultipleForm = new SelectMultipleForm();

    @BeforeMethod
    @Override
    public void beforeMethod() {
        getBrowser().goTo(W3SchoolsPage.SELECT_MULTIPLE.getAddress());
        getBrowser().setWindowSize(defaultSize.width, defaultSize.height);
    }

    @Test
    public void testDeselectByValue() {
        List<String> valuesToRemove = Stream.of("volvo", "saab").collect(Collectors.toList());

        selectMultipleForm.switchToResultFrame();
        selectMultipleForm.selectAll();
        List<String> remaining = selectMultipleForm.deselectByValue(valuesToRemove);
        selectMultipleForm.submit();

        Assert.assertEquals(selectMultipleForm.getValuesFromResult(), remaining);
    }

    @Test
    public void testDeselectByContainingValue() {
        List<String> valuesToRemove = Stream.of("saa", "ope").collect(Collectors.toList());

        selectMultipleForm.switchToResultFrame();
        selectMultipleForm.selectAll();
        List<String> remaining = selectMultipleForm.deselectByContainingValue(valuesToRemove);
        selectMultipleForm.submit();

        Assert.assertEquals(selectMultipleForm.getValuesFromResult(), remaining);
    }

    @Test
    public void testDeselectByText() {
        List<String> valuesToRemove = Stream.of("Opel").collect(Collectors.toList());

        selectMultipleForm.switchToResultFrame();
        selectMultipleForm.selectAll();
        List<String> remaining = selectMultipleForm.deselectByText(valuesToRemove);
        selectMultipleForm.submit();

        Assert.assertEquals(selectMultipleForm.getValuesFromResult(), remaining);
    }

    @Test
    public void testDeselectByContainingText() {
        List<String> valuesToRemove = Stream.of("Au", "Vol").collect(Collectors.toList());

        selectMultipleForm.switchToResultFrame();
        selectMultipleForm.selectAll();
        List<String> remaining = selectMultipleForm.deselectByContainingText(valuesToRemove);
        selectMultipleForm.submit();

        Assert.assertEquals(selectMultipleForm.getValuesFromResult(), remaining);
    }

    @Test
    public void testDeselectByIndex() {
        List<Integer> valuesToRemove = Stream.of(2, 3).collect(Collectors.toList());

        selectMultipleForm.switchToResultFrame();
        selectMultipleForm.selectAll();
        List<String> remaining = selectMultipleForm.deselectByIndex(valuesToRemove);
        selectMultipleForm.submit();

        Assert.assertEquals(selectMultipleForm.getValuesFromResult(), remaining);
    }

    @Test
    public void testDeselectAll() {
        selectMultipleForm.switchToResultFrame();
        selectMultipleForm.selectAll();
        selectMultipleForm.deselectAll();
        selectMultipleForm.submit();

        Assert.assertTrue(selectMultipleForm.getValuesFromResult().isEmpty());
    }
}
