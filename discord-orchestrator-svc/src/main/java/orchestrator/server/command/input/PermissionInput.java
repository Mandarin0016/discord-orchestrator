package orchestrator.server.command.input;

import lombok.Builder;
import lombok.Getter;
import orchestrator.common.model.server.PermissionAction;

import java.util.List;

@Getter
public class PermissionInput {
    private final String roleName;
    private final List<PermissionAction> actions;

    @Builder
    private PermissionInput(String roleName, List<PermissionAction> actions) {
        this.roleName = roleName;
        this.actions = actions;
    }
}
