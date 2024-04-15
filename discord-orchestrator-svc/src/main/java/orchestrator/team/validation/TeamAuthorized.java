package orchestrator.team.validation;

import jakarta.validation.Payload;

public @interface TeamAuthorized {

    String message() default "You are not authorized to perform this action.";

    Class<?>[] group() default {};

    Class<? extends Payload>[] payload() default {};
}
