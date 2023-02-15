package ru.prod.Cloud_service.repositories;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.prod.Cloud_service.Entity_models.File;
import ru.prod.Cloud_service.Entity_models.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FileRepositoryTest {

    public static final User user = new User("user", "user", new ArrayList<>());
    public static final String filename = "filename";
    public static final String filename2 = "filename_old";
    public static final String filename3 = "filename_new";

    public static final File file = new File(filename, filename.getBytes(),10L,  LocalDateTime.now(), user);
    public static final File file2 = new File(filename2, filename.getBytes(),1L,  LocalDateTime.now(), user);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FileRepository fileRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        fileRepository.deleteAll();
        userRepository.save(user);

    }


    @Test
    public void deleteByFilename() {
        fileRepository.save(file2);
        Optional<File> beforeDelete = fileRepository.findById(filename2);
        assertTrue(beforeDelete.isPresent());
        fileRepository.deleteByFilename( filename2);
        Optional<File> afterDelete = fileRepository.findById(filename2);
        assertFalse(afterDelete.isPresent());
    }



    @Test
    public void findAllByUser() {
        fileRepository.save(file);
        assertEquals(List.of(file), fileRepository.findAllByUser(user));

    }

    @Test
    public void editFileNameByUser() {
        fileRepository.save(file2);
        Optional<File> beforeEditName = fileRepository.findById(filename2);
        assertTrue(beforeEditName.isPresent());
        assertEquals(filename2, beforeEditName.get().getFilename());
        fileRepository.editFileNameByUser(user, filename2, filename3);
        Optional<File> afterEditName = fileRepository.findById(filename3);
        assertTrue(afterEditName.isPresent());
        assertEquals(filename3, afterEditName.get().getFilename());
    }

    @Test
    public void findByFilename() {
        fileRepository.save(file);
        assertEquals(file, fileRepository.findByFilename(filename));
    }
}