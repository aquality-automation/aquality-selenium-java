package manytools;

import aquality.selenium.elements.interfaces.ILabel;
import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.By;

import java.util.function.Function;

public class RequestHeadersForm extends ManyToolsForm<RequestHeadersForm> {
    private final Function<String, ILabel> getHeaderValueLabel = name -> getElementFactory().getLabel(
            By.xpath(String.format("//td[.='%s']/following-sibling::td", name)), "Header " + name);
    public RequestHeadersForm() {
        super("Request headers");
    }

    @Override
    protected String getUrlPart() {
        return "http-html-text/http-request-headers/";
    }

    @Override
    public String getValue() {
        throw new NotImplementedException("Please call the method getNullableValue with parameter instead");
    }

    public String getNullableValue(String headerName) {
        getFormLabel().state().waitForDisplayed();
        ILabel valueLabel = getHeaderValueLabel.apply(headerName);
        return valueLabel.state().isDisplayed() ? valueLabel.getText() : null;
    }
}
