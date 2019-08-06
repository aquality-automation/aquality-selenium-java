package aquality.selenium.elements.interfaces;

import aquality.selenium.elements.ElementState;
import aquality.selenium.elements.ElementType;
import org.openqa.selenium.By;

public interface IParent {

    /**
     * Find an element in the parent element
     *
     * @param childLoc Child element locator
     * @param type the type of the element to be obtained

     * @return child element
     */
    default <T extends IElement> T findChildElement(By childLoc, ElementType type){
        return findChildElement(childLoc, type, ElementState.DISPLAYED);
    }

    /**
     * Find an element in the parent element
     *
     * @param childLoc Child element locator
     * @param type the type of the element to be obtained
     * @param state visibility state of target element

     * @return child element
     */
    <T extends IElement> T findChildElement(By childLoc, ElementType type, ElementState state);

    /**
     * Find an element in the parent element
     *
     * @param childLoc Child element locator
     * @param clazz class or interface of the element to be obtained
     * @return found child element
     */
    default <T extends IElement> T findChildElement(By childLoc, Class<? extends IElement> clazz){
        return findChildElement(childLoc, clazz, ElementState.DISPLAYED);
    }

    /**
     * Find an element in the parent element
     *
     * @param childLoc Child element locator
     * @param clazz class or interface of the element to be obtained
     * @param state visibility state of target element
     * @return found child element
     */
    <T extends IElement> T findChildElement(By childLoc, Class<? extends IElement> clazz, ElementState state);

    /**
     * Find an element in the parent element
     *
     * @param childLoc Child element locator
     * @param supplier required element's supplier
     * @param state visibility state of target element
     * @return found child element
     */
    <T extends IElement> T findChildElement(By childLoc, IElementSupplier<T> supplier, ElementState state);
}
