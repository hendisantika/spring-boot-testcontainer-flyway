package com.hendisantika;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-testcontainer-flyway
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 3/12/23
 * Time: 08:28
 * To change this template use File | Settings | File Templates.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

/**
 * Integration test that resets the database after each test with a custom {@link FlywayMigrationStrategy}. Because the
 * {@link FlywayMigrationStrategy} is applied by Spring Boot's autoconfiguration, a context refresh is required to
 * trigger it. That significantly slows down test execution because Spring needs to recreate all beans after each test.
 */
@JdbcTest
@ContextConfiguration(
        initializers = FlywayMigrationStrategyAuthorRepositoryTest.class,
        classes = {
                AuthorRepository.class,
                FlywayMigrationStrategyAuthorRepositoryTest.TestConfiguration.class
        }
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// Tells Spring to perform a context refresh after each test to trigger the FlywayMigrationStrategy (see below). Slow!
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Commit
public class FlywayMigrationStrategyAuthorRepositoryTest extends AbstractPostgresJupiterTest {

    @Autowired
    AuthorRepository authorRepository;
}
