package automationpractice.forms;

import aquality.selenium.core.elements.ElementState;
import aquality.selenium.core.elements.ElementsCount;
import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;

import java.util.List;

public class SliderForm extends Form {

    private static final String STR_LIST_ELEMENTS = "//ul[@id='blockbestsellers']//li";

    private final IButton btnNext = getElementFactory().getButton(By.xpath("//a[contains(.,'Next')]"), "Next");
    private final By byBtnAddToCart = By.xpath(STR_LIST_ELEMENTS.concat("[last()]//a[contains(@class, 'add_to_cart')]"));

    public SliderForm() {
        super(By.id("slider_row"), "Slider");
    }

    public List<ILabel> getListElements(ElementState state, ElementsCount elementsCount){
        return getElementFactory().findElements(By.xpath(STR_LIST_ELEMENTS), ElementType.LABEL, elementsCount, state);
    }

    public Point getFormPointInViewPort(){
        return getElementFactory().getLabel(getLocator(), getName()).getJsActions().getViewPortCoordinates();
    }

    public IButton getBtnAddToCart(ElementState elementState){
        return getElementFactory().getButton(byBtnAddToCart, "Add to cart", elementState);
    }

    public void clickBtnNext() {
        btnNext.clickAndWait();
    }
}
