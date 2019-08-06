package aquality.selenium.elements;

public enum HighlightState {
    HIGHLIGHT("highlight"),
    NOT_HIGHLIGHT("not_highlight");

    private final String state;

    HighlightState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return state;
    }
}
