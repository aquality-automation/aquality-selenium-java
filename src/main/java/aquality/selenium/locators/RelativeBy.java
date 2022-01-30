package aquality.selenium.locators;

import aquality.selenium.elements.interfaces.IElement;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;

import java.util.List;

public class RelativeBy extends By {
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

    @Override
    public List<WebElement> findElements(SearchContext context) {
        return context.findElements(by);
    }
}
