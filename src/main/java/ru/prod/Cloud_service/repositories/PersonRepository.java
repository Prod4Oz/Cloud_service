package ru.prod.Cloud_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.prod.Cloud_service.Entity_models.User;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<User, Long> {


    Optional<User> findByUsername(String username);
}
