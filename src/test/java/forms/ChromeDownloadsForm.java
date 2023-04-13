package forms;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;

public class ChromeDownloadsForm extends Form {
    private static final String ADDRESS = "chrome://downloads/";
    public static final By NESTED_SHADOW_ROOT_LOCATOR = By.id("moreActionsMenu");

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
        return getFormLabel().findElementInShadowRoot(By.cssSelector("downloads-toolbar"), "Downloads toolbar", ILabel.class);
    }

    public ILabel getMainContainerLabel() {
        return getFormLabel().findElementInShadowRoot(By.id("mainContainer"), "main container", ILabel.class);
    }

    public ILabel getDownloadsToolbarLabelFromJs() {
        return lblDownloadsToolbarFromJs;
    }

    public ILabel getMainContainerLabelFromJs() {
        return lblMainContainerFromJs;
    }
}
