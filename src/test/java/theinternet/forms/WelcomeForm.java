package theinternet.forms;

import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.elements.interfaces.ILink;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;

public class WelcomeForm extends TheInternetForm {
    private static final String EXAMPLE_LINK_XPATH_TEMPLATE = "//a[contains(@href,'%s')]";
    private final ILabel lblSubtitle = getElementFactory().getLabel(By.xpath("//h2"), "Sub title");

    public WelcomeForm() {
        super(By.xpath("//h1[contains(.,'Welcome to the-internet')]"), "Welcome to the-internet");
    }

    @Override
    protected String getUri() {
        return "";
    }

    public ILabel getSubTitleLabel() {
        return lblSubtitle;
    }

    public ILink getExampleLink(AvailableExample example) {
        String menuItemXPath = String.format(EXAMPLE_LINK_XPATH_TEMPLATE, example.name().toLowerCase());
        return getElementFactory().getLink(By.xpath(menuItemXPath), StringUtils.capitalize(example.toString()));
    }

    public Point getFormPointInViewPort(){
        return getElementFactory().getLabel(getLocator(), getName()).getJsActions().getViewPortCoordinates();
    }

    public enum AvailableExample {
        CHECKBOXES,
        DROPDOWN,
        HOVERS,
        KEY_PRESSES,
        INFINITE_SCROLL,
        ADD_REMOVE_ELEMENTS,
        CONTEXT_MENU
    }
}
