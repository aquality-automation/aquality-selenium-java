package aquality.selenium.locators;

import org.openqa.selenium.By;

public class RelativeBySupplier {
    public static RelativeBy with(By by) {
        return new RelativeBy(by);
    }
}