package orchestrator.team.service;

import com.mandarin.dto.Action;
import lombok.extern.slf4j.Slf4j;
import orchestrator.discord.exception.DiscordServerDomainException;
import orchestrator.team.model.Team;
import orchestrator.team.model.TeamRole;
import orchestrator.team.model.TeamRoleAbility;
import orchestrator.team.property.TeamProperties;
import orchestrator.team.repository.TeamRepository;
import orchestrator.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class TeamService {

    private final TeamProperties teamProperties;
    private final TeamRepository teamRepository;
    private final UserService userService;

    public TeamService(TeamProperties teamProperties,
                       TeamRepository teamRepository,
                       UserService userService) {

        this.teamProperties = teamProperties;
        this.teamRepository = teamRepository;
        this.userService = userService;
    }

    public boolean isMemberAuthorizedForAction(UUID memberId, UUID discordServerId, Action action) {

        //TODO: Check if hibernate do properly the fetching of the associations.
        Team team = teamRepository.findTeamByServersId(discordServerId)
                .orElseThrow(() -> new DiscordServerDomainException("Unrecognized server with given id=[%s].".formatted(discordServerId)));

        boolean isTeamMember = team.getMembers().stream().anyMatch(m -> m.getId().equals(memberId));
        if (!isTeamMember) {
            return false;
        }

        List<TeamRole> teamRoles = userService.getById(memberId, false)
                .getTeamRoles()
                .stream()
                .filter(role -> role.getTeamId().equals(team.getId()))
                .toList();

        List<TeamRoleAbility> expectedAbilities = teamProperties.getAbilityToPackageActions()
                .entrySet()
                .stream().filter(e -> e.getValue().contains(action))
                .map(Map.Entry::getKey)
                .toList();

        return teamRoles.stream().anyMatch(r -> r.getAbilities().stream().anyMatch(expectedAbilities::contains));
    }

}
