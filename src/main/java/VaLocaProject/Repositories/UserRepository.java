package VaLocaProject.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import VaLocaProject.Models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    // Override JpaRepository.findById so the method name stays findById but the return type matches
    @Query(value = "SELECT * FROM users WHERE id = :id", nativeQuery = true)
    Optional<User> findById(@Param("id") Long id);

    User findByEmail(String email);
}