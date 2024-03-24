package com.mandarin.client;

import com.mandarin.dto.Package;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface DiscordWorkerBaseController {

    @PostMapping
    Package sendPackage(@Validated @NotNull @RequestBody Package pack);
}
