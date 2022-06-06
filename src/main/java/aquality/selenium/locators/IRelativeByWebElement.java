package aquality.selenium.locators;

import org.openqa.selenium.WebElement;

public interface IRelativeByWebElement {
    RelativeBy above(WebElement element);
    RelativeBy below(WebElement element);
    RelativeBy toLeftOf(WebElement element);
    RelativeBy toRightOf(WebElement element);
    RelativeBy near(WebElement element);
    RelativeBy near(WebElement element, int atMostDistanceInPixels);
}
