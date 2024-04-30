package orchestrator.team.model.mapper;

import lombok.experimental.UtilityClass;
import orchestrator.discord.command.output.DiscordServerOutput;
import orchestrator.discord.model.mapper.DiscordServerEntityMapper;
import orchestrator.team.command.output.TeamMemberOutput;
import orchestrator.team.command.output.TeamOutput;
import orchestrator.team.model.Team;
import orchestrator.team.model.TeamMembership;
import orchestrator.team.model.TeamRole;
import orchestrator.worker.model.Worker;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@UtilityClass
public class TeamEntityMapper {

    public static TeamOutput toTeamOutput(Team team, List<TeamMembership> teamMemberships) {

        TeamMembership ownerMembership = teamMemberships.stream()
                .filter(m -> m.getMember().getId().equals(team.getOwner().getId()))
                .findFirst()
                .get();
        TeamMemberOutput owner = toTeamMemberOutput(ownerMembership);
        List<TeamMemberOutput> members = teamMemberships.stream().map(TeamEntityMapper::toTeamMemberOutput).toList();
        List<UUID> workers = team.getWorkers().stream().map(Worker::getId).toList();
        List<DiscordServerOutput> discordServers = team.getServers().stream().map(DiscordServerEntityMapper::toDiscordServerOutput).toList();

        return TeamOutput.builder()
                .id(team.getId())
                .name(team.getName())
                .owner(owner)
                .members(members)
                .workers(workers)
                .discordServers(discordServers)
                .build();
    }

    public static TeamMemberOutput toTeamMemberOutput(TeamMembership teamMembership) {

        return TeamMemberOutput.builder()
                .memberId(teamMembership.getMember().getId())
                .firstName(teamMembership.getMember().getFirstName())
                .lastName(teamMembership.getMember().getLastName())
                .username(teamMembership.getMember().getUsername())
                .roleIds(teamMembership.getMemberRoles().stream().map(TeamRole::getId).collect(Collectors.toSet()))
                .isPrimaryOwner(teamMembership.isPrimaryOwner())
                .build();
    }
}
