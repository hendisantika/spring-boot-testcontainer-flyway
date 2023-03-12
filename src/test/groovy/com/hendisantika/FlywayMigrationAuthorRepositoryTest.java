package com.hendisantika;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-testcontainer-flyway
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 3/12/23
 * Time: 08:25
 * To change this template use File | Settings | File Templates.
 */

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration test that uses {@link CleanDatabaseTestExecutionListener} to reinitialise the database after each test.
 */
@JdbcTest
@ContextConfiguration(
        initializers = FlywayMigrationAuthorRepositoryTest.class,
        classes = {
                AuthorRepository.class,
                FlywayMigrationAuthorRepositoryTest.TestConfiguration.class
        }
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// An alternative to a TestExecutionListener would be a setup method annotated with @BeforeEach that invokes Flyway. But
// then you would lose the ability to access the database in any TestExecutionListener (incl. the one that processes
// @Sql annotations) because @BeforeEach runs after the TestExecutionListeners.
@TestExecutionListeners(
        value = {CleanDatabaseTestExecutionListener.class},
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS // Retains default TestExecutionListeners.
)
@Commit
public class FlywayMigrationAuthorRepositoryTest extends AbstractPostgresJupiterTest {

    @Autowired
    AuthorRepository authorRepository;

    @Test
    void findAll_returnsAllAuthors() {
        var authors = this.authorRepository.findAll();

        assertThat(authors)
                .extracting(Author::name)
                .containsExactly("Bert Bates", "Joshua Bloch", "Kathy Sierra", "Trisha Gee");
    }

    @SpringBootApplication
    public static class TestConfiguration {
        @Bean
        public FlywayMigrationStrategy flywayMigrationStrategy() {
            return flyway -> {
                // Do nothing to disable the Flyway migration action on startup without having to disable the Flyway
                // autoconfiguration which is what spring.flyway.enabled=false would do.
            };
        }
    }
}
