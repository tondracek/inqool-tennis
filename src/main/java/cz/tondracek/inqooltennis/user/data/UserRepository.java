package cz.tondracek.inqooltennis.user.data;

import cz.tondracek.inqooltennis.core.exception.NotFoundException;
import cz.tondracek.inqooltennis.user.model.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository {

    /**
     * @param user the user to create
     * @return the created user
     */
    User create(User user);

    /**
     * @param user the user to update
     * @return the updated user
     */
    User update(User user);

    /**
     * @param id the id of the user to find
     * @return the found user or null if not found
     * @throws NotFoundException if the user with the given id does not exist
     */
    User findById(UUID id);

    /**
     * @param email the email of the user to find
     * @return the found user or null if not found
     * @throws NotFoundException if the user with the given email does not exist
     */
    User findByEmail(String email);

    /**
     * @return a list of all active users
     */
    List<User> findAllActive();
}
