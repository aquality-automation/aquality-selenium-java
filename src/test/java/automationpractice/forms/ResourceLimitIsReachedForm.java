package automationpractice.forms;

import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class ResourceLimitIsReachedForm extends Form {

    public ResourceLimitIsReachedForm() {
        super(By.xpath("//h1[.='Resource Limit Is Reached']"), "508 - Resource Limit Is Reached");
    }
}