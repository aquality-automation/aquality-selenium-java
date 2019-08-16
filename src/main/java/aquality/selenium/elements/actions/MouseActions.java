package aquality.selenium.elements.actions;


import aquality.selenium.browser.Browser;
import aquality.selenium.browser.BrowserManager;
import aquality.selenium.elements.interfaces.IElement;
import aquality.selenium.localization.LocalizationManager;
import aquality.selenium.logger.Logger;
import aquality.selenium.utils.ElementActionRetrier;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.function.BiConsumer;

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
        ElementActionRetrier.doWithRetry(() -> performAction((actions, el) -> actions.click().build().perform()));
    }

    /**
     * Click Right (calls context menu) on the element
     */
    public void rightClick() {
        infoLoc("loc.clicking.right");
        ElementActionRetrier.doWithRetry(() -> performAction(((actions, el) -> actions.contextClick(el).build().perform())));
    }

    /**
     * Move mouse to this element.
     */
    public void moveMouseToElement() {
        infoLoc("loc.moving");
        element.getElement().getCoordinates().inViewPort();
        ElementActionRetrier.doWithRetry(() -> performAction((actions, el) -> actions.moveToElement(el).build().perform()));
    }

    /**
     * Move mouse from this element.
     */
    public void moveMouseFromElement() {
        infoLoc("loc.movingFrom");
        ElementActionRetrier.doWithRetry(() -> performAction(((actions, el) -> actions.moveToElement(el,
                -el.getSize().width / 2, -el.getSize().height / 2).build().perform())));
    }

    /**
     * Double click on the item. Waiting for the end of renderiga
     */
    public void doubleClick() {
        infoLoc("loc.clicking.double");
        ElementActionRetrier.doWithRetry(() -> (getBrowser().getDriver()).getMouse().mouseMove(element.getElement().getCoordinates()));
        ElementActionRetrier.doWithRetry(() -> performAction(((actions, el) -> actions.doubleClick(el).build().perform())));
    }

    private void performAction(BiConsumer<Actions, WebElement> consumer) {
        Actions actions = new Actions(getBrowser().getDriver());
        consumer.accept(actions.moveToElement(element.getElement()), element.getElement());
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

    private Browser getBrowser(){
        return BrowserManager.getBrowser();
    }
}
