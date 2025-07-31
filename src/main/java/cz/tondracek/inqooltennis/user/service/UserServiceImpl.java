package cz.tondracek.inqooltennis.user.service;

import cz.tondracek.inqooltennis.common.password.PasswordEncoder;
import cz.tondracek.inqooltennis.core.exception.BadRequestException;
import cz.tondracek.inqooltennis.core.exception.ConflictException;
import cz.tondracek.inqooltennis.core.exception.NotFoundException;
import cz.tondracek.inqooltennis.user.data.UserRepository;
import cz.tondracek.inqooltennis.user.dto.CreateUserDto;
import cz.tondracek.inqooltennis.user.dto.UpdateUserDto;
import cz.tondracek.inqooltennis.user.dto.UserDetailDto;
import cz.tondracek.inqooltennis.user.mapper.UserMapper;
import cz.tondracek.inqooltennis.user.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    private static final Pattern SIMPLE_EMAIL_PATTERN = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]{2,}$");

    private final UserRepository repository;
    private final UserMapper mapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.repository = userRepository;
        this.mapper = userMapper;
    }

    @Transactional
    @Override
    public UserDetailDto createUser(CreateUserDto createUserDto) {
        validateEmail(createUserDto.email());
        User userToCreate = mapper.toUser(createUserDto, UUID.randomUUID(), getHashedPassword(createUserDto.password()));

        checkForUserWithExistingEmail(userToCreate);

        User createdUser = repository.create(userToCreate);
        return mapper.toDetailDto(createdUser);
    }

    @Transactional
    @Override
    public UserDetailDto updateUser(UUID id, UpdateUserDto updateUserDto) {
        validateEmail(updateUserDto.email());
        User original = repository.findById(id);
        User userToUpdate = mapper.toUser(updateUserDto, getHashedPassword(updateUserDto.password()), original);

        checkForUserWithExistingEmail(userToUpdate);

        User updatedUser = repository.update(userToUpdate);
        return mapper.toDetailDto(updatedUser);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetailDto findUserById(String id) {
        User user = repository.findById(UUID.fromString(id));
        return mapper.toDetailDto(user);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetailDto findUserByEmail(String email) {
        User user = repository.findByEmail(email);
        return mapper.toDetailDto(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDetailDto> findAllActiveUsers() {
        return repository.findAllActive()
                .stream()
                .map(mapper::toDetailDto)
                .toList();
    }

    private void validateEmail(String email) {
        if (!SIMPLE_EMAIL_PATTERN.matcher(email).matches())
            throw new BadRequestException("Invalid email format: " + email);
    }

    private String getHashedPassword(String password) {
        return PasswordEncoder.hash(password);
    }

    private void checkForUserWithExistingEmail(User newUser) {
        User existingUserWithEmail;
        try {
            existingUserWithEmail = repository.findByEmail(newUser.email());
        } catch (NotFoundException e) {
            existingUserWithEmail = null;
        }
        if (existingUserWithEmail != null && !existingUserWithEmail.id().equals(newUser.id()))
            throw new ConflictException("User with email " + newUser.email() + " already exists.");
    }
}
