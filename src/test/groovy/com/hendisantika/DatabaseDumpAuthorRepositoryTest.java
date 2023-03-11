package com.hendisantika;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-testcontainer-flyway
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 3/11/23
 * Time: 14:43
 * To change this template use File | Settings | File Templates.
 */
// Disable Flyway entirely because the test does not need it.
@JdbcTest(properties = "spring.flyway.enabled=false")
@ContextConfiguration(
        initializers = DatabaseDumpAuthorRepositoryTest.class,
        classes = {
                AuthorRepository.class
        }
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// Reinitialise the test database before each test from the previously created database dump.
@Sql(scripts = "/db.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
@Commit
public class DatabaseDumpAuthorRepositoryTest extends AbstractPostgresJupiterTest {

    @Autowired
    AuthorRepository authorRepository;


    @Test
    void findAll_returnsAllAuthors() {
        var authors = this.authorRepository.findAll();

        assertThat(authors)
                .extracting(Author::name)
                .containsExactly("Bert Bates", "Joshua Bloch", "Kathy Sierra", "Trisha Gee");
    }
}
