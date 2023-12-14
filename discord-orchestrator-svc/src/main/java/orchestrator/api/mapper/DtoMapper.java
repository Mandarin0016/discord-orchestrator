package orchestrator.api.mapper;

import lombok.experimental.UtilityClass;
import orchestrator.api.dto.*;
import orchestrator.common.model.ChannelType;
import orchestrator.common.model.PermissionAction;
import orchestrator.server.command.input.*;

@UtilityClass
public final class DtoMapper {

    public static DiscordServerSetupInput mapToServerSetupInput(DiscordServerSetupRequest dto){

        return DiscordServerSetupInput.builder()
                .idempotencyKey(dto.idempotencyKey())
                .discordClientId(dto.discordClientId())
                .serverName(dto.setupDetails().serverName())
                .serverDescription(dto.setupDetails().serverDescription())
                .iconUrl(dto.setupDetails().iconUrl())
                .bannerUrl(dto.setupDetails().bannerUrl())
                .categories(dto.setupDetails().categories().stream().map(DtoMapper::mapToCategoryInput).toList())
                .roles(dto.setupDetails().roles().stream().map(DtoMapper::mapToRoleInput).toList())
                .build();
    }

    private static RoleInput mapToRoleInput(Role dto) {

        return RoleInput.builder()
                .name(dto.name())
                .color(dto.color())
                .hoist(dto.hoist())
                .mentionable(dto.mentionable())
                .build();
    }

    private static DiscordCategoryInput mapToCategoryInput(Category dto) {

        return DiscordCategoryInput.builder()
                .name(dto.name())
                .description(dto.description())
                .channels(dto.channels().stream().map(DtoMapper::mapToChannelInput).toList())
                .build();
    }

    private static DiscordChannelInput mapToChannelInput(Channel dto) {

        return DiscordChannelInput.builder()
                .name(dto.name())
                .type(ChannelType.valueOf(dto.type().name()))
                .topic(dto.topic())
                .permissions(dto.permissions().stream().map(DtoMapper::mapToPermissionInput).toList())
                .build();
    }

    private static PermissionInput mapToPermissionInput(Permission dto) {

        return PermissionInput.builder()
                .roleName(dto.role())
                .actions(dto.permissionActions().stream().map(adto -> PermissionAction.valueOf(adto.name())).toList())
                .build();
    }
}
