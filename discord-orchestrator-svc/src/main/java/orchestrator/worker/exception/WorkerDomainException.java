package orchestrator.worker.exception;

public class WorkerDomainException extends RuntimeException {

    public WorkerDomainException() {

    }

    public WorkerDomainException(String message) {

        super(message);
    }

    public WorkerDomainException(String message, Throwable cause) {

        super(message, cause);
    }
}
