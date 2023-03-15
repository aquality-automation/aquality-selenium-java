package automationpractice.forms;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.JavaScript;
import aquality.selenium.core.elements.ElementState;
import aquality.selenium.core.elements.ElementsCount;
import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.IElement;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

import java.util.List;
import java.util.Random;

public class ProductListForm extends Form {

    private static final String XPATH_ADD_TO_CART_BTN = "//a[contains(@class, 'add_to_cart')]";
    private static final String XPATH_PRODUCT_CONTAINER = "//ul[@id='homefeatured']//div[@class='product-container']";
    private static final String XPATH_PRODUCT = "//ul[@id='homefeatured']//li";

    public ProductListForm() {
        super(By.xpath("//a[contains(@class, 'add_to_cart')]"), "Product List");
    }

    public List<ILabel> getProductContainerLabels(){
        return getElementFactory().findElements(By.xpath(XPATH_PRODUCT_CONTAINER), ElementType.LABEL, ElementsCount.MORE_THAN_ZERO, ElementState.DISPLAYED);
    }

    private ILabel getLblFirstProduct(){
        return getElementFactory().getLabel(By.xpath(XPATH_PRODUCT.concat("[1]")), "First product");
    }

    public ILabel getLblLastProduct(){
        return getElementFactory().getLabel(By.id("homefeatured"), "home featured").findChildElement(By.xpath(".//li".concat("[last()]")), ILabel.class);
    }

    public IButton getBtnLastProductMoreFocused() {
        getLblFirstProduct().getMouseActions().moveMouseToElement();
        getLblLastProduct().getMouseActions().moveMouseToElement();
        IButton btnLastProductMore = getLblLastProduct().findChildElement(By.xpath(".//a[contains(@class, 'lnk_view')]"), ElementType.BUTTON, ElementState.EXISTS_IN_ANY_STATE);
        if(!btnLastProductMore.state().isDisplayed()) {
            getLblLastProduct().getMouseActions().moveMouseFromElement();
            getLblLastProduct().getMouseActions().moveMouseToElement();
        }
        return btnLastProductMore;
    }

    public void addToCardRandomProduct(){
        List<ILabel> productList = getProductContainerLabels();
        ILabel lblProduct = productList.get(new Random().nextInt(productList.size()));
        AqualityServices.getBrowser().executeScript(JavaScript.SCROLL_TO_ELEMENT, lblProduct.getElement());
        lblProduct.getMouseActions().moveMouseToElement();
        getBtnAddCard(lblProduct).getJsActions().click();
    }

    private IButton getBtnAddCard(IElement productItem){
        return productItem.findChildElement(By.xpath(XPATH_ADD_TO_CART_BTN), ElementType.BUTTON);
    }
}
