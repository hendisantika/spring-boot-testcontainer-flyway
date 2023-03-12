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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;

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
}
