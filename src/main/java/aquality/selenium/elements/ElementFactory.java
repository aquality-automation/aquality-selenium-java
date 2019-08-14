package aquality.selenium.elements;

import aquality.selenium.browser.Browser;
import aquality.selenium.browser.BrowserManager;
import aquality.selenium.browser.JavaScript;
import aquality.selenium.configuration.Configuration;
import aquality.selenium.elements.interfaces.*;
import aquality.selenium.logger.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ElementFactory implements IElementFactory {
    private static final int XPATH_SUBSTRING_BEGIN_INDEX = 10;

    @Override
    public IButton getButton(final By locator, final String name, final ElementState state){
        return new Button(locator, name, state);
    }

    @Override
    public ICheckBox getCheckBox(final By locator, final String name, final ElementState state) {
        return new CheckBox(locator, name, state);
    }

    @Override
    public IComboBox getComboBox(final By locator, final String name, final ElementState state) {
        return new ComboBox(locator, name, state);
    }

    @Override
    public ILabel getLabel(final By locator, final String name, final ElementState state) {
        return new Label(locator, name, state);
    }

    @Override
    public ILink getLink(final By locator, final String name, final ElementState state) {
        return new Link(locator, name, state);
    }

    @Override
    public IRadioButton getRadioButton(final By locator, final String name, final ElementState state) {
        return new RadioButton(locator, name, state);
    }

    @Override
    public ITextBox getTextBox(final By locator, final String name, final ElementState state) {
        return new TextBox(locator, name, state);
    }

    @Override
    public <T extends IElement> T getCustomElement(IElementSupplier<T> supplier, By locator, String name, ElementState state) {
        return supplier.get(locator, name, state);
    }

    @Override
    public <T extends IElement> T findChildElement(IElement parentElement, By childLoc,
                                                   Class<? extends IElement> clazz, ElementState state) {
        return findChildElementCore(parentElement, childLoc, getDefaultElementSupplier(clazz), state);
    }

    @Override
    public <T extends IElement> T findChildElement(IElement parentElement, By childLoc,
                                                   IElementSupplier<T> supplier, ElementState state) {
        return findChildElementCore(parentElement, childLoc, supplier, state);
    }

    @Override
    public <T extends IElement> T findChildElement(IElement parentElement, By childLoc, ElementType type,
                                                   ElementState state) {
        return findChildElement(parentElement, childLoc, type.getClazz(), state);
    }

    @Override
    public <T extends IElement> List<T> findElements(By locator, IElementSupplier<T> supplier, ExpectedCount count,
                                                     ElementState state) {
        return findElementsCore(locator, supplier, state, count);
    }

    @Override
    public <T extends IElement> List<T> findElements(By locator, Class<? extends IElement> clazz, ElementState state, ExpectedCount count) {
        return findElementsCore(locator, getDefaultElementSupplier(clazz), state, count);
    }

    @Override
    public <T extends IElement> List<T> findElements(By locator, ElementType type, ElementState state, ExpectedCount count) {
        return findElements(locator, type.getClazz(), state, count);
    }

    private  <T extends IElement> List<T> findElementsCore(By locator, IElementSupplier<T> supplier,
                                                           ElementState state, ExpectedCount count) {
        List<T> elements = new ArrayList<>();
        switch (count) {
            case ZERO:
                break;
            case MORE_THEN_ZERO:
                WebDriverWait webDriverWait = new WebDriverWait(getBrowser().getDriver(),
                        Configuration.getInstance().getTimeoutConfiguration().getCondition());
                webDriverWait.until(driver -> !driver.findElements(locator).isEmpty());
                break;
            default:
                throw new IllegalArgumentException("No such expected value:".concat(count.toString()));
        }
        List<WebElement> webElements = getBrowser().getDriver().findElements(locator);
        int index = 1;
        for (WebElement webElement : webElements) {
            try {
                String xPath = locator.getClass().equals(ByXPath.class)
                        ? String.format("(%s)[%d]", locator.toString().substring(XPATH_SUBSTRING_BEGIN_INDEX), index)
                        : (String) getBrowser().executeScript(JavaScript.GET_ELEMENT_XPATH, webElement);
                T element = supplier.get(By.xpath(xPath), "element " + index, state);
                elements.add(element);
                ++index;
            } catch (IllegalArgumentException e) {
                Logger.getInstance().debug(e.getMessage());
            }
        }

        return elements;
    }

    private  <T extends IElement> T findChildElementCore(IElement parentElement, By childLoc,
                                                         IElementSupplier<T> supplier, ElementState state) {
        String childXPath;
        if(childLoc.getClass().equals(ByXPath.class)){
            By parentLoc = parentElement.getLocator();
            String parentXpath = extractXpath(parentLoc, getBrowser().getDriver());
            childXPath = childLoc.toString().substring(XPATH_SUBSTRING_BEGIN_INDEX);
            childXPath = childXPath.startsWith(".") ? "/".concat(childXPath) : childXPath;
            childXPath = String.format("%s%s", parentXpath, childXPath);
        } else {
            childXPath = extractXpath(childLoc, parentElement.getElement());
        }

        return supplier.get(By.xpath(childXPath), "Child element of ".concat(parentElement.getName()), state);
    }

    private String extractXpath(By locator, SearchContext searchContext) {
        if(locator.getClass().equals(ByXPath.class)) {
            return locator.toString().substring(XPATH_SUBSTRING_BEGIN_INDEX);
        }

        WebElement webElement = searchContext.findElements(locator).stream().findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cannot create element by non-xpath locator."
                        .concat(locator.toString())
                        .concat("Please ensure that element is currently on screen, or use xpath locators instead")));
        return (String) getBrowser().executeScript(JavaScript.GET_ELEMENT_XPATH, webElement);
    }

    private <T extends IElement> IElementSupplier<T> getDefaultElementSupplier(Class<? extends IElement> clazz) {
        Type type = convertElementClassToType(clazz);

        return (locator, name, state) -> {
            Constructor ctor;
            try {
                ctor = ((Class) type).getDeclaredConstructor(By.class, String.class, ElementState.class);
                return (T) ctor.newInstance(locator, name, state);
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                Logger.getInstance().debug(e.getMessage());
                throw new IllegalArgumentException("Something went wrong during element casting");
            }
        };
    }

    private Type convertElementClassToType(Class<? extends IElement> clazz){
        return clazz.isInterface() ? ElementType.getTypeByClass(clazz).getClazz() : clazz;
    }

    private Browser getBrowser(){
        return BrowserManager.getBrowser();
    }
}

