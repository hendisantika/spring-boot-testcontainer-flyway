package com.hendisantika;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-testcontainer-flyway
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 3/11/23
 * Time: 14:38
 * To change this template use File | Settings | File Templates.
 */
public record Author(AuthorId id, String name) {
    public Author {
        if (id == null) {
            throw new NullPointerException("Required id is null");
        }
        if (name == null) {
            throw new NullPointerException("Required name is null");
        }
        if (name.isBlank()) {
            throw new IllegalArgumentException("Required name is blank");
        }
    }
}
