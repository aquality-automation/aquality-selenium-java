package tests.usecases;

import aquality.selenium.elements.interfaces.ILabel;
import forms.ChromeDownloadsForm;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.BaseTest;

import java.util.List;

public class ShadowRootTests extends BaseTest {
    private static final ChromeDownloadsForm form = new ChromeDownloadsForm();

    @BeforeMethod
    @Override
    public void beforeMethod() {
        ChromeDownloadsForm.open();
    }

    @Test
    public void testExpandShadowRoot() {
        Assert.assertNotNull(form.expandShadowRoot(), "Should be possible to expand shadow root and get Selenium native ShadowRoot object");
    }

    @Test
    public void testFindElementInShadowRoot() {
        Assert.assertNotNull(form.getDownloadsToolbarLabel().getElement(), "Should be possible do get the element hidden under the shadow");
        Assert.assertNotNull(form.getDownloadsToolbarLabel().findElementInShadowRoot(ChromeDownloadsForm.NESTED_SHADOW_ROOT_LOCATOR, "More actions menu", ILabel.class).getElement(),
                "Should be possible to expand the nested shadow root and get the element from it");
        Assert.assertTrue(form.getMainContainerLabel().state().isDisplayed(), "Should be possible to check that element under the shadow is displayed");
    }

    @Test
    public void testFindElementsInShadowRoot() {
        List<ILabel> elementLabels = form.getDivElementLabels();
        Assert.assertTrue(elementLabels.size() > 1, "Should be possible to find multiple elements hidden under the shadow");
        Assert.assertTrue(elementLabels.get(0).getLocator() instanceof By.ByCssSelector, "Unique locator of correct type should be generated");
        Assert.assertEquals(elementLabels.get(0).getElement().getTagName(), "div", "Should be possible to work with one of found elements");
        Assert.assertEquals(form.getMainContainerLabels().get(0).getElement().getTagName(), "div", "Should be possible to work with one of found elements found by id");
    }

    @Test
    public void testExpandShadowRootViaJs() {
        Assert.assertNotNull(form.expandShadowRootViaJs(), "Should be possible to expand shadow root and get Selenium native ShadowRoot object");
        Assert.assertNotNull(form.getDownloadsToolbarLabelFromJs().getElement(), "Should be possible do get the element hidden under the shadow");
        List<ILabel> elementLabels = form.getDivElementLabelsFromJs();
        Assert.assertTrue(elementLabels.size() > 1, "Should be possible to find multiple elements hidden under the shadow");
        Assert.assertTrue(elementLabels.get(0).getLocator() instanceof By.ByCssSelector, "Unique locator of correct type should be generated");
        Assert.assertEquals(elementLabels.get(0).getElement().getTagName(), "div", "Should be possible to work with one of found elements");
        Assert.assertEquals(form.getMainContainerLabelsFromJs().get(0).getElement().getTagName(), "div", "Should be possible to work with one of found elements found by id");
        Assert.assertNotNull(form.getDownloadsToolbarLabelFromJs().findElementInShadowRoot(ChromeDownloadsForm.NESTED_SHADOW_ROOT_LOCATOR, "More actions menu", ILabel.class).getElement(),
                "Should be possible to expand the nested shadow root and get the element from it");
        Assert.assertTrue(form.getMainContainerLabelFromJs().state().isDisplayed(), "Should be possible to check that element under the shadow is displayed");
    }
}
