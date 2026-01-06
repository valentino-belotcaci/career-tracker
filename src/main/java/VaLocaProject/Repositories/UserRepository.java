package VaLocaProject.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import VaLocaProject.Models.User;



public interface UserRepository extends JpaRepository<User, Integer> {

}