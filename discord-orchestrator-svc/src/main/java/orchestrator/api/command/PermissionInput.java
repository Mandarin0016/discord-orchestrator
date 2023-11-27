package orchestrator.api.command;

import lombok.Builder;
import lombok.Getter;
import orchestrator.common.model.PermissionAction;

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
