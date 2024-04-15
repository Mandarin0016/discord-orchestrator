package orchestrator.discord.exception;

public class DiscordServerDomainException extends RuntimeException {

    public DiscordServerDomainException() {

    }

    public DiscordServerDomainException(String message) {

        super(message);
    }

    public DiscordServerDomainException(String message, Throwable cause) {

        super(message, cause);
    }
}
