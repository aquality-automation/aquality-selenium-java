package aquality.selenium.elements;

import aquality.selenium.core.elements.ElementState;
import org.openqa.selenium.By;

public abstract class CheckableElement extends Element {
    /**
     * The main constructor
     *
     * @param loc     By Locator
     * @param nameOf  Output in logs
     * @param stateOf desired ElementState
     */
    protected CheckableElement(By loc, String nameOf, ElementState stateOf) {
        super(loc, nameOf, stateOf);
    }

    public boolean isChecked() {
        logElementAction("loc.checkable.get.state");
        boolean state = getState();
        logElementAction("loc.checkable.state", state);
        return state;
    }

    protected boolean getState() {
        return doWithRetry(() -> getElement().isSelected());
    }
}
