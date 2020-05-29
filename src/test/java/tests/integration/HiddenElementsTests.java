package tests.integration;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.elements.ElementState;
import aquality.selenium.core.elements.ElementsCount;
import aquality.selenium.elements.interfaces.ILabel;
import automationpractice.forms.ProductTabContentForm;
import automationpractice.forms.SliderForm;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.BaseTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static automationpractice.Constants.URL_AUTOMATIONPRACTICE;

public class HiddenElementsTests extends BaseTest {

    private static final ProductTabContentForm productsForm = new ProductTabContentForm();

    @BeforeMethod
    @Override
    protected void beforeMethod() {
        AqualityServices.getBrowser().goTo(URL_AUTOMATIONPRACTICE);
    }


    @DataProvider
    private Object[] getHiddenElementListProviders() {
        List<Function<ElementsCount, List<ILabel>>> providers = new ArrayList<>();
        ElementState state = ElementState.EXISTS_IN_ANY_STATE;
        providers.add(count -> productsForm.getListElements(state, count));
        providers.add(count -> productsForm.getListElementsById(state, count));
        providers.add(count -> productsForm.getListElementsByName(state, count));
        providers.add(count -> productsForm.getListElementsByIdOrName(state, count));
        providers.add(count -> productsForm.getListElementsByClassName(state, count));
        providers.add(count -> productsForm.getListElementsByCss(state, count));
        providers.add(count -> productsForm.getListElementsByDottedXPath(state, count));
        providers.add(count -> productsForm.getChildElementsByDottedXPath(state, count));
        providers.add(count -> Collections.singletonList(productsForm.getChildElementByNonXPath(state)));
        return providers.toArray();
    }

    @Test
    public void testHiddenElementExist() {
        Assert.assertTrue(new SliderForm().getBtnAddToCart(ElementState.EXISTS_IN_ANY_STATE).state().isExist());
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
