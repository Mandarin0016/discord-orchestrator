package orchestrator.team.validation;

import com.mandarin.dto.Action;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import orchestrator.api.dto.worker.IncomingPackage;
import orchestrator.security.jwt.auth.AuthenticationMetadata;
import orchestrator.team.service.TeamService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TeamAuthorizationValidator implements ConstraintValidator<TeamAuthorized, IncomingPackage> {

    private final TeamService teamService;

    public TeamAuthorizationValidator(TeamService teamService) {
        this.teamService = teamService;
    }

    @Override
    public boolean isValid(IncomingPackage incomingPackage, ConstraintValidatorContext context) {

        AuthenticationMetadata principal = (AuthenticationMetadata) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UUID userId = UUID.fromString(principal.getUserId());
        UUID serverId = incomingPackage.serverId();
        Action requestAction = incomingPackage.action();

        return teamService.isMemberAuthorizedForAction(userId, serverId, requestAction);
    }
}
