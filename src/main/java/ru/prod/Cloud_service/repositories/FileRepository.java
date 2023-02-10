package ru.prod.Cloud_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.prod.Cloud_service.Entity_models.File;
@Repository
public interface FileRepository  extends JpaRepository<File,String> {
}
