package cz.tondracek.inqooltennis.user.service;

import cz.tondracek.inqooltennis.core.exception.BadRequestException;
import cz.tondracek.inqooltennis.core.exception.ConflictException;
import cz.tondracek.inqooltennis.core.exception.NotFoundException;
import cz.tondracek.inqooltennis.user.dto.CreateUserDto;
import cz.tondracek.inqooltennis.user.dto.UpdateUserDto;
import cz.tondracek.inqooltennis.user.dto.UserDetailDto;

import java.util.List;
import java.util.UUID;

public interface UserService {

    /**
     * Creates a new user.
     *
     * @param createUserDto values for creating a new user
     * @return the created user details
     * @throws ConflictException   if a user with the same email already exists
     * @throws BadRequestException if the provided data is invalid
     */
    UserDetailDto createUser(CreateUserDto createUserDto);

    /**
     * Updates an existing user.
     *
     * @param updateUserDto values for updating the user
     * @return the updated user details
     * @throws ConflictException   if a user with the same email already exists
     * @throws BadRequestException if the provided data is invalid
     */
    UserDetailDto updateUser(UUID id, UpdateUserDto updateUserDto);

    /**
     * Finds a user by their ID.
     *
     * @param id the ID of the user to find
     * @return the user details
     * @throws NotFoundException if the user with the given ID does not exist
     */
    UserDetailDto findUserById(String id);

    /**
     * Finds a user by their email.
     *
     * @param email the email of the user to find
     * @return the user details
     * @throws NotFoundException if the user with the given email does not exist
     */
    UserDetailDto findUserByEmail(String email);

    /**
     * Finds all active users.
     *
     * @return a list of user details for all active users
     */
    List<UserDetailDto> findAllActiveUsers();
}
