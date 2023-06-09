package com.hendisantika;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-testcontainer-flyway
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 3/11/23
 * Time: 14:39
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Repository
public class AuthorRepository {

    private final JdbcTemplate jdbcTemplate;

    public AuthorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = Objects.requireNonNull(jdbcTemplate);
    }

    public List<Author> findAll() {
        return this.jdbcTemplate.query("SELECT id, name FROM authors ORDER BY name",
                (rs, rowNum) -> new Author(AuthorId.from(rs.getString("id")), rs.getString("name")));
    }

    public void add(Author author) {
        var sql = """
                INSERT INTO authors(id, name) VALUES (?::uuid, ?);
                """;

        this.jdbcTemplate.update(sql, author.id().id().toString(), author.name());
    }

    public void delete(AuthorId authorId) {
        if (authorId == null) {
            return;
        }
        this.jdbcTemplate.update("DELETE FROM authors WHERE id = ?", authorId.id());
    }
}
