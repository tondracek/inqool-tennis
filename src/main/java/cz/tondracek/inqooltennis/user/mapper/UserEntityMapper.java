package cz.tondracek.inqooltennis.user.mapper;

import cz.tondracek.inqooltennis.user.data.UserEntity;
import cz.tondracek.inqooltennis.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

    @Mapping(target = "withDeleted", ignore = true)
    User toModel(UserEntity entity);

    UserEntity toEntity(User model);
}
