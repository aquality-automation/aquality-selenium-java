package aquality.selenium.elements;

public enum Attributes {
    CLASS("class"),
    DISABLED("disabled"),
    HREF("href"),
    STYLE("style"),
    READONLY("readOnly"),
    TEXT_CONTENT("textContent"),
    VALUE("value");

    private final String attribute;

    Attributes(String attribute) {
        this.attribute = attribute;
    }

    @Override
    public String toString() {
        return attribute;
    }
}
