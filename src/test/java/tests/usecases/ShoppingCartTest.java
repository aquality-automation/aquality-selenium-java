package tests.usecases;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.ILabel;
import automationpractice.forms.*;
import automationpractice.modals.ProceedToCheckoutModal;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.BaseTest;

import java.util.List;

import static automationpractice.Constants.URL_AUTOMATIONPRACTICE;

public class ShoppingCartTest extends BaseTest {
    private static final String USER_EMAIL_TEMPLATE = "john+%s@doe.com";
    private static final String USER_FIRST_NAME = "Dmitry";
    private static final String STATE = "California";

    @Test
    public void testShoppingCart() {
        AqualityServices.getBrowser().getDriver().navigate().to(URL_AUTOMATIONPRACTICE);
        SoftAssert softAssert = new SoftAssert();

        SliderForm sliderForm = new SliderForm();
        Assert.assertTrue(sliderForm.isDisplayed());

        sliderForm.clickBtnNext();
        sliderForm.clickBtnNext();
        ProductListForm productListForm = new ProductListForm();
        List<ILabel> productList = productListForm.getProductContainerLabels();
        softAssert.assertEquals(productList.size(), 7);

        productListForm.addToCardRandomProduct();
        ProceedToCheckoutModal proceedToCheckoutModal = new ProceedToCheckoutModal();
        proceedToCheckoutModal.getBtnProceedToCheckout().getJsActions().clickAndWait();
        ShoppingCardSummaryForm shoppingCardSummaryForm = new ShoppingCardSummaryForm();
        shoppingCardSummaryForm.getBtnPlus().getJsActions().click();
        Integer expectedQuantity = 2;
        Integer actualQuantity = shoppingCardSummaryForm.waitForQuantityAndGetValue(expectedQuantity);
        softAssert.assertEquals(expectedQuantity, actualQuantity, "Quantity is not correct");

        shoppingCardSummaryForm.clickProceedToCheckoutBtn();
        softAssert.assertTrue(new AuthenticationForm().isDisplayed());
        CartMenuForm cartMenuForm = new CartMenuForm();
        cartMenuForm.openCartMenu();
        cartMenuForm.clickCheckoutBtn();
        shoppingCardSummaryForm.clickProceedToCheckoutBtn();
        AuthenticationForm authenticationForm = new AuthenticationForm();
        authenticationForm.setEmail(getUserEmail());
        authenticationForm.clickCreateAccountBtn();
        YourPersonalInfoForm yourPersonalInfoForm = new YourPersonalInfoForm();
        yourPersonalInfoForm.selectGender(1);
        yourPersonalInfoForm.setFirstName(USER_FIRST_NAME);
        Integer expectedNumOfDays = 32;
        Integer actualNumOfDays = yourPersonalInfoForm.getNumOfDays();
        softAssert.assertEquals(expectedNumOfDays, actualNumOfDays, "Num of days from combobox is not correct");

        yourPersonalInfoForm.selectState(STATE);
        yourPersonalInfoForm.selectDay(29);
        boolean expectedStateOfNewsChb = false;
        boolean actualStateOfNewsChb = yourPersonalInfoForm.getNewsCheckBoxState();
        String errorMessage = "News checkbox state is not correct";
        softAssert.assertEquals(expectedStateOfNewsChb, actualStateOfNewsChb, errorMessage);

        yourPersonalInfoForm.setNewsChb();
        softAssert.assertTrue(yourPersonalInfoForm.getNewsCheckBoxState(), errorMessage);
        softAssert.assertTrue(yourPersonalInfoForm.isNewsCheckboxChecked(), errorMessage);
        softAssert.assertAll();
    }

    private String getUserEmail(){
        return String.format(USER_EMAIL_TEMPLATE, System.currentTimeMillis());
    }
}
