package aquality.selenium.elements;

import org.openqa.selenium.WebElement;

import java.util.function.Predicate;

class DesiredState {

    private final Predicate<WebElement> desiredStatePredicate;
    private final String message;
    private boolean isCatchingTimeoutException;
    private boolean isThrowingNoSuchElementException;

    DesiredState(Predicate<WebElement> desiredStatePredicate, String message){
        this.desiredStatePredicate = desiredStatePredicate;
        this.message = message;
    }

    public Predicate<WebElement> getDesiredStatePredicate() {
        return desiredStatePredicate;
    }

    public String getMessage() {
        return message;
    }

    public DesiredState withCatchingTimeoutException(){
        this.isCatchingTimeoutException = true;
        return this;
    }

    public DesiredState withThrowingNoSuchElementException(){
        this.isThrowingNoSuchElementException = true;
        return this;
    }

    public boolean isCatchingInTimeoutException() {
        return isCatchingTimeoutException;
    }

    public boolean isThrowingNoSuchElementException() {
        return isThrowingNoSuchElementException;
    }
}
