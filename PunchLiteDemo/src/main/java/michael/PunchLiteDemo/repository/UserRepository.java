package michael.PunchLiteDemo.repository;

import michael.PunchLiteDemo.model.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String userName);
    boolean existsByIdAndManagerId(Long userId, Long managerId);
    Optional<List<User>> findByManagerUsername(String managerUsername);
}

