package ru.prod.Cloud_service.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.prod.Cloud_service.Entity_models.User;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.prod.Cloud_service.TestProfiles.TEST_CONTAINERS;
import static ru.prod.Cloud_service.TestTags.TEST_CONTAINERS_TAG;

@DataJpaTest
@Testcontainers
@ActiveProfiles(TEST_CONTAINERS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("UserRepository: tests with PostgreSQL Testcontainers instance")
@Tag(TEST_CONTAINERS_TAG)
public class UserRepositoryContainersTest {
    public static final User user = new User("user", "user", new ArrayList<>());

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        userRepository.save(user);

    }

    @Test
    @DisplayName("find By User name")
    public void findByUsername() {
        assertEquals(user, userRepository.findByUsername("user"));
    }
}
