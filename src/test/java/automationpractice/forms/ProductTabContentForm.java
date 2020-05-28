package automationpractice.forms;

import aquality.selenium.core.elements.ElementState;
import aquality.selenium.core.elements.ElementsCount;
import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ByIdOrName;

import java.util.List;

public class ProductTabContentForm extends Form {

    private static final By LIST_ELEMENTS_XPATH = By.xpath("//ul[@id='blockbestsellers']//li");
    private static final By DOTTED_XPATH = By.xpath(".//ul[@id='blockbestsellers']//li[not(@style='display:none')]");
    private static final By BEST_SELLERS_BY_ID = By.id("blockbestsellers");
    private static final By INPUT_BY_NAME = By.name("controller");
    private static final By INPUT_BY_ID_OR_NAME = new ByIdOrName("controller");
    private static final By ITEM_BY_CSS_SELECTOR = By.cssSelector(".submenu-container");
    private static final By ITEM_BY_CLASS_NAME = By.className("submenu-container");

    public ProductTabContentForm() {
        super(By.className("tab-content"), "Product tab content");
    }

    public List<ILabel> getListElements(ElementState state, ElementsCount count) {
        return getElementFactory().findElements(LIST_ELEMENTS_XPATH, ElementType.LABEL, count, state);
    }

    public List<ILabel> getListElementsById(ElementState state, ElementsCount count) {
        return getElementFactory().findElements(BEST_SELLERS_BY_ID, ElementType.LABEL, count, state);
    }

    public List<ILabel> getListElementsByName(ElementState state, ElementsCount count) {
        return getElementFactory().findElements(INPUT_BY_NAME, ElementType.LABEL, count, state);
    }

    public List<ILabel> getListElementsByIdOrName(ElementState state, ElementsCount count) {
        return getElementFactory().findElements(INPUT_BY_ID_OR_NAME, ElementType.LABEL, count, state);
    }

    public List<ILabel> getListElementsByClassName(ElementState state, ElementsCount count) {
        return getElementFactory().findElements(ITEM_BY_CLASS_NAME, ElementType.LABEL, count, state);
    }

    public List<ILabel> getListElementsByCss(ElementState state, ElementsCount count) {
        return getElementFactory().findElements(ITEM_BY_CSS_SELECTOR, ElementType.LABEL, count, state);
    }

    public ILabel getChildElementByNonXPath(ElementState state) {
        return findChildElement(BEST_SELLERS_BY_ID, ElementType.LABEL, state);
    }

    public List<ILabel> getListElementsByDottedXPath(ElementState state, ElementsCount count) {
        return getElementFactory().findElements(DOTTED_XPATH, ElementType.LABEL, count, state);
    }

    public List<ILabel> getChildElementsByDottedXPath(ElementState state, ElementsCount count) {
        return findChildElements(DOTTED_XPATH, ElementType.LABEL, state, count);
    }
}
