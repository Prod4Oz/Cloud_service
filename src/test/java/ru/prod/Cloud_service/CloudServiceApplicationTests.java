package ru.prod.Cloud_service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(initializers = {CloudServiceApplicationTests.Initializer.class})
class CloudServiceApplicationTests {


	private static final String DATABASE_NAME = "Cloud";
	private static final String DATABASE_USERNAME = "user";
	private static final String DATABASE_PASSWORD = "user";

	@Container
	public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.1")
			.withReuse(true)
			.withDatabaseName(DATABASE_NAME)
			.withUsername(DATABASE_USERNAME)
			.withPassword(DATABASE_PASSWORD);

	static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
			TestPropertyValues.of(
					"CONTAINER.USERNAME=" + postgreSQLContainer.getUsername(),
					"CONTAINER.PASSWORD=" + postgreSQLContainer.getPassword(),
					"CONTAINER.URL=" + postgreSQLContainer.getJdbcUrl()
			).applyTo(configurableApplicationContext.getEnvironment());
		}
	}

	@Test
	void contextDatabase() {
		Assertions.assertTrue(postgreSQLContainer.isRunning());
	}


}
