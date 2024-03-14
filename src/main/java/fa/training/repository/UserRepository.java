package fa.training.repository;

import fa.training.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByRole(String role);

    User findByUserName(String userName);

    @Query("SELECT COUNT(userId) FROM User WHERE userName LIKE :userName")
    Integer getNumberUserNameExist(String userName);

    User findByEmail(String email);

    User findByResetPasswordToken(String token);

    @Modifying
    @Transactional
    @Query("UPDATE User SET active = false WHERE userId = :id")
    int deleteByUserId(int id);
}
