package automationpractice.forms;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class AuthenticationForm extends Form {

    private final ITextBox txbEmail = getElementFactory().getTextBox(By.id("email_create"), "Email Create");
    private final IButton btnCreateAcc = getElementFactory().getButton(By.id("SubmitCreate"), "Submit Create");

    public AuthenticationForm() {
        super(By.id("email_create"), "Authentication");
    }

    public void setEmail(String email){
        txbEmail.typeSecret(email);
        txbEmail.clearAndType(email);
    }

    public void clickCreateAccountBtn(){
        btnCreateAcc.clickAndWait();
    }
}
