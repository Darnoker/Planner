package pl.ug.edu.planner.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.ug.edu.planner.user.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
}
