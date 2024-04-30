package orchestrator.team.exception;

public class TeamDomainException extends RuntimeException {

    public TeamDomainException() {

    }

    public TeamDomainException(String message) {

        super(message);
    }

    public TeamDomainException(String message, Throwable cause) {

        super(message, cause);
    }
}
