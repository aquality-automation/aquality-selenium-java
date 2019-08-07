package aquality.selenium.elements.actions;


import aquality.selenium.elements.interfaces.IElement;
import aquality.selenium.localization.LocalizationManager;
import aquality.selenium.waitings.ConditionalWait;
import aquality.selenium.logger.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MouseActions {

    private static final String LOG_DELIMITER = "::";
    private final Logger logger = Logger.getInstance();
    private IElement element;
    private String type;
    private String name;


    public MouseActions(IElement element, String type) {
        this.element = element;
        this.type = type;
        this.name = element.getName();
    }

    /**
     * Click via Action.
     */
    public void click() {
        infoLoc("loc.clicking");
        new JsActions(element, type).highlightElement();
        ConditionalWait.waitFor(driver -> performAction(new Actions(driver).click(element.getElement())));
    }

    /**
     * Right Click on the element
     */
    public void rightClick() {
        infoLoc("loc.clicking.right");
        ConditionalWait.waitFor(driver -> {
            Actions actions = new Actions(driver);
            actions.moveToElement(element.getElement());
            actions.contextClick(element.getElement()).build().perform();
            return true;
        });
    }

    /**
     * Move mouse to this element.
     */
    public void moveMouseToElement() {
        infoLoc("loc.moving");
        element.getElement().getCoordinates().inViewPort();
        ConditionalWait.waitFor(driver -> performAction(new Actions(driver).moveToElement(element.getElement())));
    }

    /**
     * Double click on the item. Waiting for the end of renderiga
     */
    public void doubleClick() {
        infoLoc("loc.clicking.double");
        ConditionalWait.waitFor(driver -> {
            ((RemoteWebDriver) driver).getMouse().mouseMove(element.getElement().getCoordinates());
            return performAction(new Actions(driver).doubleClick(element.getElement()));
        });
    }

    private Boolean performAction(Actions actions) {
        try {
            actions.build().perform();
            return true;
        } catch (WebDriverException e) {
            logger.debug(e.getMessage());
            return false;
        }
    }

    /**
     * The implementation of a method for logging of MouseActions
     *
     * @param message Message to display in the log
     * @return Formatted message (containing the name and type of item)
     */
    private String formatActionMessage(final String message) {
        return String.format("%1$s '%2$s' %3$s %4$s", type, name, LOG_DELIMITER, message);
    }

    private void infoLoc(String key) {
        logger.info(formatActionMessage(LocalizationManager.getInstance().getValue(key)));
    }
}
