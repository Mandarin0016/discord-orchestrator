package orchestrator.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
public record SetupDetails(
        @NotBlank(message = "serverName cannot be blank")
        String serverName,
        String serverDescription,
        String iconUrl,
        String splashUrl,
        String bannerUrl,
        @Valid
        List<Category> categories,
        @Valid
        List<Role> roles,
        @Valid
        List<AccessRestriction> accessRestrictions
) {
}
