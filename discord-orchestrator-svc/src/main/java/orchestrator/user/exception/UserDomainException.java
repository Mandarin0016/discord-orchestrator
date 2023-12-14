package orchestrator.user.exception;

public class UserDomainException extends RuntimeException{

    public UserDomainException() {

    }

    public UserDomainException(String message) {

        super(message);
    }

    public UserDomainException(String message, Throwable cause) {

        super(message, cause);
    }
}
