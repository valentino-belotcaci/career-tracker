package VaLocaProject.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import VaLocaProject.Models.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Custom finder that matches the user's request
    Optional<User> getUserById(Long id);
}