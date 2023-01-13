package theinternet.forms;

import aquality.selenium.core.elements.ElementState;
import aquality.selenium.core.elements.ElementsCount;
import aquality.selenium.elements.interfaces.ILabel;
import org.openqa.selenium.By;

import java.util.List;

public class HoversForm extends TheInternetForm {
    private static final String EXAMPLE_TMP_LOC = "//div[@class='figure'][%d]";
    private static final String HIDDEN_ELEMENT_TMP_LOC = "//a[contains(@href,'users/%s')]";
    private static final String HIDDEN_ELEMENT_CLASS = "figcaption";
    private static final By DOTTED_XPATH = By.xpath(String.format(".//div[@class='%s']", HIDDEN_ELEMENT_CLASS));
    private static final By ITEM_BY_NAME = By.name("viewport");
    private static final By ITEM_BY_CSS_SELECTOR = By.cssSelector("." + HIDDEN_ELEMENT_CLASS);
    private static final By ITEM_BY_CLASS_NAME = By.className(HIDDEN_ELEMENT_CLASS);

    public HoversForm() {
        super(By.xpath("//body[.//h3[contains(.,'Hovers')]]"), "Hovers");
    }

    public ILabel getHiddenElement(HoverExample example) {
        return getHiddenElement(example, ElementState.DISPLAYED);
    }

    public ILabel getHiddenElement(HoverExample example, ElementState state) {
        return getElementFactory().getLabel(By.xpath(String.format(HIDDEN_ELEMENT_TMP_LOC, example.index)), String.format("Hidden element for %s example", example), state);
    }

    public ILabel getExample(HoverExample example) {
        return getElementFactory().getLabel(By.xpath(String.format(EXAMPLE_TMP_LOC, example.index)), String.format("%s example", example));
    }

    public List<ILabel> getListElements(ElementState state, ElementsCount count) {
        return getElementFactory().findElements(By.xpath(String.format(HIDDEN_ELEMENT_TMP_LOC, "")), "Element by xpath", ILabel.class, count, state);
    }

    public List<ILabel> getListElementsByName(ElementState state, ElementsCount count) {
        return getElementFactory().findElements(ITEM_BY_NAME, "Item by name", ILabel.class, count, state);
    }

    public List<ILabel> getListElementsByClassName(ElementState state, ElementsCount count) {
        return getElementFactory().findElements(ITEM_BY_CLASS_NAME, "Item by class name", ILabel.class, count, state);
    }

    public List<ILabel> getListElementsByCss(ElementState state, ElementsCount count) {
        return getElementFactory().findElements(ITEM_BY_CSS_SELECTOR, "Item by css", ILabel.class, count, state);
    }

    public ILabel getChildElementByNonXpath(ElementState state) {
        return getFormLabel().findChildElement(ITEM_BY_CSS_SELECTOR, "Child by non xpath", ILabel.class, state);
    }

    public List<ILabel> getListElementsByDottedXpath(ElementState state, ElementsCount count) {
        return getElementFactory().findElements(DOTTED_XPATH, "Item by dotted xpath", ILabel.class, count, state);
    }

    public List<ILabel> getChildElementsByDottedXpath(ElementState state, ElementsCount count) {
        return getFormLabel().findChildElements(DOTTED_XPATH, "Child by dotted xpath", ILabel.class, state, count);
    }

    public enum HoverExample {
        FIRST(1),
        SECOND(2),
        THIRD(3);

        int index;

        HoverExample(int i) {
            this.index = i;
        }
    }

    @Override
    protected String getUri() {
        return "/hovers";
    }
}
