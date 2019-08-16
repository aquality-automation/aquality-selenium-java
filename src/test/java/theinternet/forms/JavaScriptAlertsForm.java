package theinternet.forms;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ILabel;
import org.openqa.selenium.By;

public class JavaScriptAlertsForm extends TheInternetForm {
    private final IButton btnJsAlert = getElementFactory().getButton(By.xpath("//button[@onclick='jsAlert()']"), "Js Alert");
    private final IButton btnJsConfirm = getElementFactory().getButton(By.xpath("//button[@onclick='jsConfirm()']"), "Js Confirm");
    private final IButton btnJsPrompt = getElementFactory().getButton(By.xpath("//button[@onclick='jsPrompt()']"), "Js Prompt");
    private final ILabel lblResult = getElementFactory().getLabel(By.id("result"), "Result");

    public JavaScriptAlertsForm() {
        super(By.id("content"), "JavaScript Alerts");
    }

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

    @Override
    protected String getUri() {
        return "/javascript_alerts";
    }
}
