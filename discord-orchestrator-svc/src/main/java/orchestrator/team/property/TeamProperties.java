package orchestrator.team.property;

import com.mandarin.dto.Action;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import orchestrator.team.model.TeamRoleAbility;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Data
@Validated
@ConfigurationProperties(prefix = "team")
public class TeamProperties {

    private Map<TeamRoleAbility, Set<Action>> abilityToPackageActions = new HashMap<>();
    private DefaultOwnerRole defaultOwnerRole;
    private int maxTeamOwnershipCountAllowed;

    @Data
    @Validated
    public static class DefaultOwnerRole {
        @NotBlank
        private String name;
        @NotBlank
        private String color;
        @NotEmpty
        private Set<TeamRoleAbility> abilities;
    }

}
