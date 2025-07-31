package cz.tondracek.inqooltennis.user.data;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDao {

    void save(UserEntity userEntity);

    void update(UserEntity userEntity);

    Optional<UserEntity> findById(UUID id);

    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findAllActive();
}
