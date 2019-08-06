package aquality.selenium.elements;

public enum ElementState {
    DISPLAYED("displayed"),
    EXISTS_IN_ANY_STATE("exists");

    private final String state;

    ElementState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return state;
    }
}
