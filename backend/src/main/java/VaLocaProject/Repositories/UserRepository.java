package VaLocaProject.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import VaLocaProject.Models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}