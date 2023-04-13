package aquality.selenium.locators;

import org.openqa.selenium.By;

public interface IRelativeBy {
    RelativeBy above(By by);
    RelativeBy below(By by);
    RelativeBy toLeftOf(By by);
    RelativeBy toRightOf(By by);
    RelativeBy near(By by);
    RelativeBy near(By by, int atMostDistanceInPixels);
}
