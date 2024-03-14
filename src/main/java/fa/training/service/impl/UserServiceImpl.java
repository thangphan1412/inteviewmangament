package fa.training.service.impl;

import fa.training.exception.UserNotFoundException;
import fa.training.model.User;
import fa.training.repository.UserRepository;
import fa.training.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;


    @Override
    public int createUser(User user) {
        try {
            User userResult = userRepository.save(user);
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
        return 1;
    }

    @Override
    public int updateUser(User user) {
        try {
            Optional<User> userOptional = userRepository.findById(user.getUserId());
            if (userOptional.isPresent()) {
                // Cập nhật thông tin của candidate
                User userUpdate = userOptional.get();

                userUpdate.setFullName(user.getFullName());

                String fullName = user.getFullName();
                String[] word = fullName.split(" ");
                StringBuffer userName = new StringBuffer();
                userName.append(word[word.length - 1]);
                for (int i = 0; i < word.length - 1; i++) {
                    userName.append(word[i].toUpperCase().charAt(0));
                }

                Integer numberUserNameExist = getNumberUserNameExist(userName.toString());
                if (numberUserNameExist > 0) {
                    numberUserNameExist++;
                    userName.append(numberUserNameExist);
                }

                userUpdate.setUserName(userName.toString());
                userUpdate.setEmail(user.getEmail());
                userUpdate.setPhoneNo(user.getPhoneNo());
                userUpdate.setDob(user.getDob());
                userUpdate.setAddress(user.getAddress());
                userUpdate.setGender(user.getGender());
                userUpdate.setRole(user.getRole());
                userUpdate.setDepartment(user.getDepartment());
                userUpdate.setActive(user.getActive());
                userUpdate.setNote(user.getNote());

                // Lưu candidate cập nhật vào database
                userRepository.save(userUpdate);
                return 1;
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return -1;
    }

    @Override
    public List<User> getAll() {
        List<User> user = new ArrayList<>();
        try {
            user = userRepository.findAll();
            return user;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public int deleteUser(int id) {
        try {
            userRepository.deleteByUserId(id);
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<User> findByRole(String role) {
        return userRepository.findByRole(role);
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public Integer getNumberUserNameExist(String userName) {
        return userRepository.getNumberUserNameExist(userName + "%");
    }

    @Override
    public int updatePassword(User user) {
        try {
            Optional<User> userOptional = userRepository.findById(user.getUserId());
            if (userOptional.isPresent()) {
                // Cập nhật thông tin của candidate
                User userUpdate = userOptional.get();
                userUpdate.setPassword(user.getPassword());
                // Lưu candidate cập nhật vào database
                userRepository.save(userUpdate);
                return 1;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    @Override
    public void updateResetPasswordToken(String token, String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("");
        }
        user.setResetPasswordToken(token);
        userRepository.save(user);
    }

    @Override
    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    @Override
    public void updateResetPassword(User user, String newPassword) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = bCryptPasswordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setResetPasswordToken(null);
        userRepository.save(user);
    }
}
