package theinternet.forms;

import aquality.selenium.forms.PageInfo;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

@PageInfo(id = "content", pageName = "JavaScript Alerts")
public class JavaScriptAlertsForm extends Form {
    private final IButton btnJsAlert = getElementFactory().getButton(By.xpath("//button[@onclick='jsAlert()']"), "Js Alert");
    private final IButton btnJsConfirm = getElementFactory().getButton(By.xpath("//button[@onclick='jsConfirm()']"), "Js Confirm");
    private final IButton btnJsPrompt = getElementFactory().getButton(By.xpath("//button[@onclick='jsPrompt()']"), "Js Prompt");
    private final ILabel lblResult = getElementFactory().getLabel(By.id("result"), "Result");

    public IButton getBtnJsAlert() {
        return btnJsAlert;
    }

    public IButton getBtnJsConfirm() {
        return btnJsConfirm;
    }

    public IButton getBtnJsPrompt() {
        return btnJsPrompt;
    }

    public ILabel getLblResult() {
        return lblResult;
    }
}
