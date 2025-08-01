package cz.tondracek.inqooltennis.user.mapper;

import cz.tondracek.inqooltennis.user.dto.CreateUserDto;
import cz.tondracek.inqooltennis.user.dto.UpdateUserDto;
import cz.tondracek.inqooltennis.user.dto.UserDetailDto;
import cz.tondracek.inqooltennis.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "deleted", constant = "false")
    @Mapping(target = "withDeleted", ignore = true)
    User toUser(CreateUserDto dto, UUID id, String passwordHash);

    @Mapping(target = "id", source = "original.id")
    @Mapping(target = "email", source = "update.email")
    @Mapping(target = "passwordHash", source = "passwordHash")
    @Mapping(target = "role", source = "update.role")
    @Mapping(target = "deleted", source = "original.deleted")
    @Mapping(target = "withDeleted", ignore = true)
    User toUser(UpdateUserDto update, String passwordHash, User original);

    UserDetailDto toDetailDto(User user);
}
