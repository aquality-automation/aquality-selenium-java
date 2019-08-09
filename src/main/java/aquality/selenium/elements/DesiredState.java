package aquality.selenium.elements;

import org.openqa.selenium.WebElement;

import java.util.function.Predicate;

class DesiredState {

    private final Predicate<WebElement> desiredStatePredicate;
    private final String message;
    private final boolean isCatchableInTimeoutException;
    private final boolean isThrowableNoSuchException;

    DesiredState(Predicate<WebElement> desiredStatePredicate, String message, boolean isCatchableInTimeoutException, boolean isThrowableNoSuchException){
        this.desiredStatePredicate = desiredStatePredicate;
        this.message = message;
        this.isCatchableInTimeoutException = isCatchableInTimeoutException;
        this.isThrowableNoSuchException = isThrowableNoSuchException;
    }

    DesiredState(Predicate<WebElement> desiredStatePredicate, String message){
        this.desiredStatePredicate = desiredStatePredicate;
        this.message = message;
        this.isCatchableInTimeoutException = true;
        this.isThrowableNoSuchException = true;
    }

    public Predicate<WebElement> getDesiredStatePredicate() {
        return desiredStatePredicate;
    }

    public String getMessage() {
        return message;
    }

    public boolean isCatchableInTimeoutException() {
        return isCatchableInTimeoutException;
    }

    public boolean isThrowableNoSuchException() {
        return isThrowableNoSuchException;
    }
}
