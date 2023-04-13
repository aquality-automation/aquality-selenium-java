package manytools;

public class BrowserLanguageForm extends ManyToolsForm<BrowserLanguageForm> {
    public BrowserLanguageForm() {
        super("Browser language");
    }

    @Override
    protected String getUrlPart() {
        return "http-html-text/browser-language/";
    }
}
