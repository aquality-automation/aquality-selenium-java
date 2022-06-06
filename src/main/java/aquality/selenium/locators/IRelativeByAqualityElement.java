package aquality.selenium.locators;

import aquality.selenium.elements.interfaces.IElement;

public interface IRelativeByAqualityElement {
    RelativeBy above(IElement element);
    RelativeBy below(IElement element);
    RelativeBy toLeftOf(IElement element);
    RelativeBy toRightOf(IElement element);
    RelativeBy near(IElement element);
    RelativeBy near(IElement element, int atMostDistanceInPixels);
}
