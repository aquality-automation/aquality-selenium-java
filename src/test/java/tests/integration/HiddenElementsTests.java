package tests.integration;

import aquality.selenium.core.elements.ElementState;
import aquality.selenium.core.elements.ElementsCount;
import aquality.selenium.elements.interfaces.ILabel;
import automationpractice.forms.SliderForm;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.BaseTest;
import theinternet.forms.HoversForm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class HiddenElementsTests extends BaseTest {

    private final HoversForm hoversForm = new HoversForm();

    @BeforeMethod
    @Override
    protected void beforeMethod() {
        getBrowser().goTo(hoversForm.getUrl());
    }

    @DataProvider
    private Object[] getHiddenElementListProviders() {
        List<Function<ElementsCount, List<ILabel>>> providers = new ArrayList<>();
        ElementState state = ElementState.EXISTS_IN_ANY_STATE;
        providers.add(count -> hoversForm.getListElements(state, count));
        providers.add(count -> hoversForm.getListElementsByName(state, count));
        providers.add(count -> hoversForm.getListElementsByClassName(state, count));
        providers.add(count -> hoversForm.getListElementsByCss(state, count));
        providers.add(count -> hoversForm.getListElementsByDottedXpath(state, count));
        providers.add(count -> hoversForm.getChildElementsByDottedXpath(state, count));
        providers.add(count -> Collections.singletonList(hoversForm.getChildElementByNonXpath(state)));
        return providers.toArray();
    }

    @Test
    public void testHiddenElementExist() {
        Assert.assertTrue(hoversForm.getHiddenElement(HoversForm.HoverExample.THIRD, ElementState.EXISTS_IN_ANY_STATE).state().isExist());
    }

    @Test(dataProvider = "getHiddenElementListProviders")
    public void testHiddenElementsExist(Function<ElementsCount, List<ILabel>> elementsListProvider) {
        List<ILabel> listElements = elementsListProvider.apply(ElementsCount.MORE_THEN_ZERO);
        Assert.assertFalse(listElements.isEmpty());
        Assert.assertTrue(listElements.stream().allMatch(el -> el.state().isExist()));
    }

    @Test(dataProvider = "getHiddenElementListProviders")
    public void testHiddenElementsNotDisplayed(Function<ElementsCount, List<ILabel>> elementsListProvider) {
        List<ILabel> listElements = elementsListProvider.apply(ElementsCount.MORE_THEN_ZERO);
        Assert.assertFalse(listElements.isEmpty());
        Assert.assertTrue(listElements.stream().noneMatch(el -> el.state().isDisplayed()));
    }

    @Test
    public void testFindDisplayedElementsShouldReturnNoElementsIfTheyAreNotDisplayed() {
        List<ILabel> listElements = new SliderForm().getListElements(ElementState.DISPLAYED, ElementsCount.ZERO);
        Assert.assertTrue(listElements.isEmpty());
    }
}
