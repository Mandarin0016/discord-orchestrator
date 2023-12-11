package orchestrator.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import orchestrator.api.dto.SetupDetails;
import orchestrator.api.properties.DiscordSetupProperties;
import org.springframework.stereotype.Component;

@Component
public class SetupDetailsValidator implements ConstraintValidator<ValidSetupDetails, SetupDetails> {

    private final DiscordSetupProperties discordSetupProperties;

    public SetupDetailsValidator(DiscordSetupProperties discordSetupProperties) {
        this.discordSetupProperties = discordSetupProperties;
    }

    @Override
    public boolean isValid(SetupDetails value, ConstraintValidatorContext context) {

        boolean isCategoryCountValid = value.categories().size() <= discordSetupProperties.getCategory().getMaxCount()
                && value.categories().size() >= discordSetupProperties.getCategory().getMinCount();
        boolean isRoleCountValid = value.roles().size() <= discordSetupProperties.getRole().getMaxCount()
                && value.roles().size() >= discordSetupProperties.getRole().getMinCount();

        return isCategoryCountValid && isRoleCountValid;
    }
}
