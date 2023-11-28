package orchestrator.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import orchestrator.api.validation.ValidSetupDetails;

import java.util.List;

@Builder
@ValidSetupDetails
public record SetupDetails(
        @NotBlank(message = "serverName cannot be blank")
        String serverName,
        String serverDescription,
        String iconUrl,
        String bannerUrl,
        @Valid
        List<Category> categories,
        @Valid
        List<Role> roles
) {
}
