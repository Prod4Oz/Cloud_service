package ru.prod.Cloud_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.prod.Cloud_service.Entity_models.File;
import ru.prod.Cloud_service.Entity_models.User;

import java.util.List;

@Repository
public interface FileRepository  extends JpaRepository<File,String> {

    void deleteByFilename(String filename);
    List<File> findAllByUser(User user);

    @Modifying
    @Query("update File f set f.filename = :newName where f.filename = :filename and f.user = :user")
    void editFileNameByUser(@Param("user") User user, @Param("filename") String filename, @Param("newName") String newFilename);
}
