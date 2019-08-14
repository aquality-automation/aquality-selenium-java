package automationpractice.forms;

import aquality.selenium.forms.Form;
import aquality.selenium.forms.PageInfo;
import aquality.selenium.elements.ElementsCount;
import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.ElementState;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ILabel;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;

import java.util.List;

@PageInfo(id = "slider_row", pageName = "Slider")
public class SliderForm extends Form {

    private static final String STR_LIST_ELEMENTS = "//ul[@id='blockbestsellers']//li";

    private final IButton btnNext = getElementFactory().getButton(By.xpath("//a[contains(.,'Next')]"), "Next");
    private final By byBtnAddToCart = By.xpath(STR_LIST_ELEMENTS.concat("[last()]//a[contains(@class, 'add_to_cart')]"));

    public List<ILabel> getListElements(ElementState state, ElementsCount elementsCount){
        return getElementFactory().findElements(By.xpath(STR_LIST_ELEMENTS), ElementType.LABEL, state, elementsCount);
    }

    public Point getFormPointInViewPort(){
        return getElementFactory().getLabel(locator, name).getJsActions().getViewPortCoordinates();
    }

    public IButton getBtnAddToCart(ElementState elementState){
        return getElementFactory().getButton(byBtnAddToCart, "Add to cart", elementState);
    }

    public void clickBtnNext() {
        btnNext.clickAndWait();
    }
}
