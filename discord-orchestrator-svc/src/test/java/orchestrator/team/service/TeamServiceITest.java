package orchestrator.team.service;

import orchestrator.team.command.input.TeamCreateInput;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
public class TeamServiceITest {

    @Autowired
    private TeamService teamService;

    @Test
    void someTest() {

        TeamCreateInput input = TeamCreateInput.builder()
                .name("Ivan")
                .ownerId(UUID.randomUUID())
                .build();
        teamService.create(input);
    }

}
