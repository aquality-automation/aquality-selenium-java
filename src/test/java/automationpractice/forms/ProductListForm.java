package automationpractice.forms;

import aquality.selenium.browser.BrowserManager;
import aquality.selenium.browser.JavaScript;
import aquality.selenium.elements.ElementState;
import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.ExpectedCount;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.IElement;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.forms.Form;
import aquality.selenium.forms.PageInfo;
import org.openqa.selenium.By;

import java.util.List;
import java.util.Random;

@PageInfo(xpath = "//ul[@id='homefeatured']//div[@class='product-container']", pageName = "Product list")
public class ProductListForm extends Form {

    private static final String XPATH_ADD_TO_CART_BTN = "//a[contains(@class, 'add_to_cart')]";
    private static final String XPATH_PRODUCT_CONTAINER = "//ul[@id='homefeatured']//div[@class='product-container']";
    private static final String XPATH_PRODUCT = "//ul[@id='homefeatured']//li";

    public List<ILabel> getProductContainerLabels(){
        return getElementFactory().findElements(By.xpath(XPATH_PRODUCT_CONTAINER), ElementType.LABEL, ElementState.DISPLAYED, ExpectedCount.MORE_THEN_ZERO);
    }

    public ILabel getLblFirstProduct(){
        return getElementFactory().getLabel(By.xpath(XPATH_PRODUCT.concat("[1]")), "First product");
    }

    public ILabel getLblLastProduct(){
        return getElementFactory().getLabel(By.id("homefeatured"), "home featured").findChildElement(By.xpath("//li".concat("[last()]")), ILabel.class);
    }

    public IButton getBtnLastProductMore(){
        return getLblLastProduct().findChildElement(By.xpath(".//a[contains(@class, 'lnk_view')]"), ElementType.BUTTON);
    }

    public void addToCardRandomProduct(){
        List<ILabel> productList = getProductContainerLabels();
        ILabel lblProduct = productList.get(new Random().nextInt(productList.size()));
        BrowserManager.getBrowser().executeScript(JavaScript.SCROLL_TO_ELEMENT, lblProduct.getElement());
        lblProduct.getMouseActions().moveMouseToElement();
        getBtnAddCard(lblProduct).getJsActions().click();
    }

    private IButton getBtnAddCard(IElement productItem){
        return productItem.findChildElement(By.xpath(XPATH_ADD_TO_CART_BTN), ElementType.BUTTON);
    }
}
