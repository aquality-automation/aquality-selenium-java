package forms;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;

import java.util.List;

public class ChromeDownloadsForm extends Form {
    private static final String ADDRESS = "chrome://downloads/";
    public static final By NESTED_SHADOW_ROOT_LOCATOR = By.id("moreActionsMenu");
    public static final By DIV_ELEMENTS_LOCATOR = By.cssSelector("div");

    private final ILabel lblDownloadsToolbar = getFormLabel().findElementInShadowRoot(By.cssSelector("downloads-toolbar"), "Downloads toolbar", ILabel.class);
    private final ILabel lblMainContainer = getFormLabel().findElementInShadowRoot(By.id("mainContainer"), "Main container", ILabel.class);

    private final ILabel lblDownloadsToolbarFromJs = getFormLabel().getJsActions().findElementInShadowRoot(By.cssSelector("downloads-toolbar"), "Downloads toolbar", ILabel.class);
    private final ILabel lblMainContainerFromJs = getFormLabel().getJsActions().findElementInShadowRoot(By.id("mainContainer"), "Main container", ILabel.class);


    public ChromeDownloadsForm() {
        super(By.tagName("downloads-manager"), "Chrome downloads manager");
    }

    public static void open() {
        AqualityServices.getBrowser().goTo(ADDRESS);
    }

    public SearchContext expandShadowRoot() {
        return getFormLabel().expandShadowRoot();
    }

    public SearchContext expandShadowRootViaJs() {
        return getFormLabel().getJsActions().expandShadowRoot();
    }

    public ILabel getDownloadsToolbarLabel() {
        return lblDownloadsToolbar;
    }

    public ILabel getMainContainerLabel() {
        return lblMainContainer;
    }

    public ILabel getDownloadsToolbarLabelFromJs() {
        return lblDownloadsToolbarFromJs;
    }

    public ILabel getMainContainerLabelFromJs() {
        return lblMainContainerFromJs;
    }

    public List<ILabel> getDivElementLabels() {
        return getFormLabel().findElementsInShadowRoot(DIV_ELEMENTS_LOCATOR, "div", ILabel.class);
    }

    public List<ILabel> getDivElementLabelsFromJs() {
        return getFormLabel().getJsActions().findElementsInShadowRoot(DIV_ELEMENTS_LOCATOR, "div", ILabel.class);
    }

    public List<ILabel> getMainContainerLabels() {
        return getFormLabel().findElementsInShadowRoot(lblMainContainer.getLocator(), lblMainContainer.getName(), ILabel.class);
    }

    public List<ILabel> getMainContainerLabelsFromJs() {
        return getFormLabel().getJsActions().findElementsInShadowRoot(lblMainContainer.getLocator(), lblMainContainer.getName(), ILabel.class);
    }
}
