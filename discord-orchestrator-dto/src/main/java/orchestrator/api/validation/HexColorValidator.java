package orchestrator.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class HexColorValidator implements ConstraintValidator<ValidHexColor, String> {

    private static final String HEX_COLOR_PATTERN = "^#([a-fA-F0-9]{6}|[a-fA-F0-9]{3})$";
    private static final Pattern pattern = Pattern.compile(HEX_COLOR_PATTERN);


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

}
