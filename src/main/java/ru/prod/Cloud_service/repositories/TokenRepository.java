package ru.prod.Cloud_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.prod.Cloud_service.Entity_models.Token;
@Repository
public interface TokenRepository extends JpaRepository<Token,String> {
}
