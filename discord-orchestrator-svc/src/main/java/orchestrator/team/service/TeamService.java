package orchestrator.team.service;

import com.mandarin.dto.Action;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import orchestrator.team.command.input.TeamCreateInput;
import orchestrator.team.command.output.TeamOutput;
import orchestrator.team.exception.TeamDomainException;
import orchestrator.team.model.*;
import orchestrator.team.model.mapper.TeamEntityMapper;
import orchestrator.team.property.TeamProperties;
import orchestrator.team.repository.TeamMembershipRepository;
import orchestrator.team.repository.TeamRepository;
import orchestrator.team.repository.TeamRoleRepository;
import orchestrator.user.model.User;
import orchestrator.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
public class TeamService {

    private final TeamProperties teamProperties;
    private final TeamRepository teamRepository;
    private final TeamRoleRepository teamRoleRepository;
    private final UserService userService;
    private final TeamMembershipRepository teamMembershipRepository;

    public TeamService(TeamProperties teamProperties,
                       TeamRepository teamRepository,
                       TeamRoleRepository teamRoleRepository,
                       UserService userService,
                       TeamMembershipRepository teamMembershipRepository) {

        this.teamProperties = teamProperties;
        this.teamRepository = teamRepository;
        this.teamRoleRepository = teamRoleRepository;
        this.userService = userService;
        this.teamMembershipRepository = teamMembershipRepository;
    }

    @Transactional
    public TeamOutput create(TeamCreateInput createInputCommand) {

        UUID ownerId = createInputCommand.ownerId();
        int userTeamOwnershipCount = teamRepository.countTeamByOwnerId(ownerId);
        int maxCountAllowed = teamProperties.getMaxTeamOwnershipCountAllowed();

        if (userTeamOwnershipCount >= maxCountAllowed) {
            log.warn("Team creation failed for user id=[%s]: maximum team count limit [%d] reached.".formatted(ownerId, maxCountAllowed));
            throw new TeamDomainException("Unable to create team for user id=[%s] because the maximum team count limit [%d] has been reached.".formatted(ownerId, maxCountAllowed));
        }

        String name = createInputCommand.name();
        User owner = userService.getById(ownerId, true);

        Team team = initNewTeam(owner, name);
        TeamRole defaultOwnerRole = initDefaultOwnerRole(team, owner);
        initMembership(team, owner, true, defaultOwnerRole);
        team.getTeamRoles().add(defaultOwnerRole);
        team = teamRepository.save(team);
        List<TeamMembership> teamMemberships = teamMembershipRepository.findByTeam_Id(team.getId());

        log.info("New team created with id=[%s], name=[%s], and owner=[%s].".formatted(team.getId(), name, owner.getId()));
        return TeamEntityMapper.toTeamOutput(team, teamMemberships);
    }

    private void initMembership(Team team, User member, boolean isPrimaryOwner, TeamRole... roles) {

        TeamMembership teamMembership = TeamMembership.builder()
                .team(team)
                .member(member)
                .memberRoles(Set.of(roles))
                .isPrimaryOwner(isPrimaryOwner)
                .membershipStatus(TeamMembershipStatus.ACTIVE)
                .build();

        teamMembershipRepository.save(teamMembership);
    }

    private TeamRole initDefaultOwnerRole(Team team, User teamOwner) {

        String name = teamProperties.getDefaultOwnerRole().getName();
        String color = teamProperties.getDefaultOwnerRole().getColor();
        Set<TeamRoleAbility> abilities = teamProperties.getDefaultOwnerRole().getAbilities();

        TeamRole defaultOwnerRole = TeamRole.builder()
                .team(team)
                .name(name)
                .color(color)
                .abilities(abilities)
                .roleRank(TeamRoleRank.AUTO_CREATED)
                .createdBy(teamOwner)
                .updatedBy(teamOwner)
                .build();

        return teamRoleRepository.save(defaultOwnerRole);
    }

    private Team initNewTeam(User owner, String name) {

        Team team = Team.builder()
                .name(name)
                .owner(owner)
                .isActive(true)
                .build();

        return teamRepository.save(team);
    }

    public boolean isMemberAuthorizedForAction(UUID memberId, UUID discordServerId, Action action) {

//        //TODO: Check if hibernate do properly the fetching of the associations.
//        Team team = teamRepository.findTeamByServersId(discordServerId)
//                .orElseThrow(() -> new DiscordServerDomainException("Unrecognized server with given id=[%s].".formatted(discordServerId)));
//
//        boolean isTeamMember = team.getMembers().stream().anyMatch(m -> m.getId().equals(memberId));
//        if (!isTeamMember) {
//            return false;
//        }
//
//        List<TeamRole> teamRoles = userService.getById(memberId, false)
//                .getTeamRoles()
//                .stream()
//                .filter(role -> role.getTeamId().equals(team.getId()))
//                .toList();
//
//        List<TeamRoleAbility> expectedAbilities = teamProperties.getAbilityToPackageActions()
//                .entrySet()
//                .stream().filter(e -> e.getValue().contains(action))
//                .map(Map.Entry::getKey)
//                .toList();

//        return teamRoles.stream().anyMatch(r -> r.getAbilities().stream().anyMatch(expectedAbilities::contains));
        return false;
    }

}
