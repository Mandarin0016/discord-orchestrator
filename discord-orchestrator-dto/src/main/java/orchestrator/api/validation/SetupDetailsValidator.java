package orchestrator.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import orchestrator.api.dto.SetupDetails;
import org.springframework.stereotype.Component;

@Component
public class SetupDetailsValidator implements ConstraintValidator<ValidSetupDetails, SetupDetails> {


    @Override
    public boolean isValid(SetupDetails value, ConstraintValidatorContext context) {
        return false;
    }
}
