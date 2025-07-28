package michael.PunchLiteDemo.repository;

import michael.PunchLiteDemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {}

