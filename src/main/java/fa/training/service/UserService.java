package fa.training.service;

import fa.training.exception.UserNotFoundException;
import fa.training.model.User;

import java.util.List;

public interface UserService {
    int createUser(User user);

    int updateUser(User user);

    List<User> getAll();

    User getUserById(int id);

    int deleteUser(int id);

    List<User> findByRole(String role);

    User findByUserName(String userName);

    Integer getNumberUserNameExist(String userName);

    int updatePassword(User user);

    void updateResetPasswordToken(String token, String email) throws UserNotFoundException;

    User getByResetPasswordToken(String token);

    void updateResetPassword(User user, String newPassword);
}
