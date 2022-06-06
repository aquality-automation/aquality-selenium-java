package aquality.selenium.locators;

import org.openqa.selenium.By;

public class RelativeBySupplier {

    private RelativeBySupplier() {
    }

    public static RelativeBy with(By by) {
        return new RelativeBy(by);
    }
}
