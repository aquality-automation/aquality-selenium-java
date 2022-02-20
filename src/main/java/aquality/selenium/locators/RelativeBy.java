package aquality.selenium.locators;

import aquality.selenium.elements.interfaces.IElement;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;

import java.util.List;

public class RelativeBy extends By implements IRelativeByAqualityElement, IRelativeByWebElement, IRelativeBy {
    private By by;

    public RelativeBy(By by) {
        this.by = by;
    }

    public RelativeBy above(IElement element) {
        by = RelativeLocator.with(by).above(element.getElement());
        return new RelativeBy(by);
    }

    public RelativeBy above(WebElement element) {
        by = RelativeLocator.with(by).above(element);
        return new RelativeBy(by);
    }

    public RelativeBy above(By by) {
        this.by = RelativeLocator.with(this.by).above(by);
        return new RelativeBy(this.by);
    }

    public RelativeBy below(IElement element) {
        by = RelativeLocator.with(by).below(element.getElement());
        return new RelativeBy(by);
    }

    public RelativeBy below(WebElement element) {
        by = RelativeLocator.with(by).below(element);
        return new RelativeBy(by);
    }

    public RelativeBy below(By by) {
        this.by = RelativeLocator.with(this.by).below(by);
        return new RelativeBy(this.by);
    }

    public RelativeBy toLeftOf(IElement element) {
        by = RelativeLocator.with(by).toLeftOf(element.getElement());
        return new RelativeBy(by);
    }

    public RelativeBy toLeftOf(WebElement element) {
        by = RelativeLocator.with(by).toLeftOf(element);
        return new RelativeBy(by);
    }

    public RelativeBy toLeftOf(By by) {
        this.by = RelativeLocator.with(this.by).toLeftOf(by);
        return new RelativeBy(this.by);
    }

    public RelativeBy toRightOf(IElement element) {
        by = RelativeLocator.with(by).toRightOf(element.getElement());
        return new RelativeBy(by);
    }

    public RelativeBy toRightOf(WebElement element) {
        by = RelativeLocator.with(by).toRightOf(element);
        return new RelativeBy(by);
    }

    public RelativeBy toRightOf(By by) {
        this.by = RelativeLocator.with(this.by).toRightOf(by);
        return new RelativeBy(this.by);
    }

    public RelativeBy near(IElement element) {
        by = RelativeLocator.with(by).near(element.getElement());
        return new RelativeBy(by);
    }

    public RelativeBy near(WebElement element) {
        by = RelativeLocator.with(by).near(element);
        return new RelativeBy(by);
    }

    public RelativeBy near(By by) {
        this.by = RelativeLocator.with(this.by).near(by);
        return new RelativeBy(this.by);
    }

    public RelativeBy near(IElement element, int atMostDistanceInPixels) {
        by = RelativeLocator.with(by).near(element.getElement(), atMostDistanceInPixels);
        return new RelativeBy(by);
    }

    public RelativeBy near(WebElement element, int atMostDistanceInPixels) {
        by = RelativeLocator.with(by).near(element, atMostDistanceInPixels);
        return new RelativeBy(by);
    }

    public RelativeBy near(By by, int atMostDistanceInPixels) {
        this.by = RelativeLocator.with(this.by).near(by, atMostDistanceInPixels);
        return new RelativeBy(this.by);
    }

    @Override
    public List<WebElement> findElements(SearchContext context) {
        return context.findElements(by);
    }
}
