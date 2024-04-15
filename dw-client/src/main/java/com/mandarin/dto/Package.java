package com.mandarin.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.UUID;

/**
 * This is the main communication unit between all the discord workers and discord-orchestrator-svc.
 */
@Valid
public record Package(
        @NotNull
        UUID idempotencyKey,
        @NotNull
        UUID workerId,
        @NotBlank
        String serverId,
        Object content,
        @NotNull
        Action action,
        Status status
) implements Serializable {
}
