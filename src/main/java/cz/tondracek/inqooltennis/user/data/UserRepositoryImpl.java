package cz.tondracek.inqooltennis.user.data;

import cz.tondracek.inqooltennis.core.exception.NotFoundException;
import cz.tondracek.inqooltennis.user.mapper.UserEntityMapper;
import cz.tondracek.inqooltennis.user.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserDao dao;
    private final UserEntityMapper mapper;

    public UserRepositoryImpl(
            UserDao userDao,
            UserEntityMapper userEntityMapper
    ) {
        this.dao = userDao;
        this.mapper = userEntityMapper;
    }

    @Override
    public User create(User user) {
        UserEntity entity = mapper.toEntity(user);
        dao.save(entity);
        return user;
    }

    @Override
    public User update(User user) {
        UserEntity entity = mapper.toEntity(user);
        dao.update(entity);
        return user;
    }

    @Override
    public User findById(UUID id) {
        return dao.findById(id)
                .map(mapper::toModel)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
    }

    @Override
    public User findByEmail(String email) {
        return dao.findByEmail(email)
                .map(mapper::toModel)
                .orElseThrow(() -> new NotFoundException("User with email " + email + " not found"));
    }

    @Override
    public List<User> findAllActive() {
        return dao.findAllActive()
                .stream()
                .map(mapper::toModel)
                .toList();
    }
}
