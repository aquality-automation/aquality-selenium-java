package tests.usecases;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.BaseTest;
import theinternet.forms.DynamicContentForm;

public class VisualTests extends BaseTest {
    private static final DynamicContentForm dynamicContentForm = new DynamicContentForm();

    @BeforeMethod
    @Override
    protected void beforeMethod() {
        getBrowser().goTo(dynamicContentForm.getUrl());
        getBrowser().maximize();
    }

    @Test
    public void shouldBePossibleToCheckVisualStateWhenItHasNotChanged()
    {
        Assert.assertTrue(dynamicContentForm.state().waitForDisplayed(), "Form is not opened");
        dynamicContentForm.dump().save();
        Assert.assertEquals(dynamicContentForm.dump().compare(), 0, "Form dump should remain the same");
    }

    @Test
    public void shouldBePossibleToCheckVisualStateWhenItDiffers()
    {
        final String dumpName = "the differed dump";
        Assert.assertTrue(dynamicContentForm.state().waitForDisplayed(), "Form is not opened");
        dynamicContentForm.dump().save(dumpName);
        getBrowser().refresh();
        Assert.assertTrue(dynamicContentForm.state().waitForDisplayed(), "Form is not opened");
        Assert.assertTrue(dynamicContentForm.dump().compare(dumpName) > 0, "After clicking on slider next button, the form dump should differ");
    }
}
