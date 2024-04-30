package orchestrator.user.exception;

public class IllegalUserStateException extends RuntimeException {

    public IllegalUserStateException() {

    }

    public IllegalUserStateException(String message) {

        super(message);
    }

    public IllegalUserStateException(String message, Throwable cause) {

        super(message, cause);
    }
}

