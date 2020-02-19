package aquality.selenium.elements;

public enum HighlightState {
    DEFAULT,
    HIGHLIGHT,
    NOT_HIGHLIGHT;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
